package info.esblurock.reaction.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.jdo.FetchGroup;
import javax.jdo.PersistenceManager;

import info.esblurock.reaction.client.panel.transaction.TransactionService;
import info.esblurock.reaction.data.delete.DeleteTransactionInfoAndObject;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.server.datastore.PMF;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// TODO: Auto-generated Javadoc
/**
 * The Class TransactionServiceImpl.
 */
public class TransactionServiceImpl extends RemoteServiceServlet implements
		TransactionService {

	/** The pm. */
	PersistenceManager pm = PMF.get().getPersistenceManager();

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.panel.transaction.TransactionService#getAllTransactions()
	 */
	@Override
	public List<TransactionInfo> getAllTransactions() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		javax.jdo.Query q = pm.newQuery(TransactionInfo.class);
		List<TransactionInfo> results = (List<TransactionInfo>) q.execute();
		ArrayList<TransactionInfo> lst = new ArrayList<TransactionInfo>();
		for(TransactionInfo transaction: results) {
			TransactionInfo t = pm.detachCopy(transaction);
			lst.add(t);
		}
		pm.close();
		return lst;
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.panel.transaction.TransactionService#getAllUploadTransactions()
	 */
	public List<TextSetUploadData> getAllUploadTransactions() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.getFetchPlan().setGroup(FetchGroup.ALL);
		javax.jdo.Query q = pm.newQuery(TextSetUploadData.class);
		List<TextSetUploadData> results = (List<TextSetUploadData>) q.execute();
		ArrayList<TextSetUploadData> lst = new ArrayList<TextSetUploadData>();
		for(TextSetUploadData data : results) {
			if(data != null) {
				pm.retrieve(data);
				TextSetUploadData datacopy = pm.detachCopy(data);
				lst.add(datacopy);
			} else {
				System.out.println("getAllUploadTransactions() an element is null");
			}
		}
		pm.close();
		return lst;
	}
	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.panel.transaction.TransactionService#getTransactions(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<TransactionInfo> getTransactions(String user, String keyword,
			String objecttype) {
		return null;
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.panel.transaction.TransactionService#removeTransaction(java.lang.String)
	 */
	@Override
	public String removeTransactionOfObject(String key) throws Exception {
		DeleteTransactionInfoAndObject delete = new DeleteTransactionInfoAndObject();
		String ans = delete.deleteFromObjectKey(key);
		pm.close();
		return ans;
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.panel.transaction.TransactionService#getUserSet()
	 */
	@Override
	public Set<String> getUserSet() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.panel.transaction.TransactionService#getKeywordSet()
	 */
	@Override
	public Set<String> getKeywordSet() {
		return null;
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.panel.transaction.TransactionService#getObjectTypeSet()
	 */
	@Override
	public Set<String> getObjectTypeSet() {
		return null;
	}

	@Override
	public String removeTransactionWithTypeAndKeyword(String objecttype, String keyword) throws Exception {
		DeleteTransactionInfoAndObject delete = new DeleteTransactionInfoAndObject();
		String ans = delete.deleteFromTypeAndKeyword(objecttype,keyword);
		return ans;
	}

	@Override
	public String deleteTransactionInfoFromKey(String transactionkey) throws IOException {
		DeleteTransactionInfoAndObject delete = new DeleteTransactionInfoAndObject();
		String ans = delete.deleteTransactionInfoFromKey(transactionkey);
		pm.close();
		return ans;
		
	}
	public String removeFromRDFsFromDate(Date date) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter subjectfilter =
				  new FilterPredicate("creationDate",FilterOperator.GREATER_THAN_OR_EQUAL,date);
		Query q = new Query("KeywordRDF").setFilter(subjectfilter).setKeysOnly();
		PreparedQuery pq = datastore.prepare(q);
		boolean haselement = pq.asIterable().iterator().hasNext();
		System.out.println("removeFromRDFsFromDate" + haselement);
		String delete = "Delete KeywordRDF  from " + date.toString();
		if(haselement) {
		int count = 0;
		for(Entity entity : pq.asIterable()) {
			System.out.println("Entity Properties" + entity.getProperties().keySet());
			Key rdfkey = entity.getKey();
			System.out.println("TransactionInfo key: " + rdfkey);
			KeywordRDF rdf = pm.getObjectById(KeywordRDF.class,rdfkey);
			pm.deletePersistent(rdf);
			count++;
		}
		Integer countI = new Integer(count);
		delete = delete + ":   Deleted " + countI.toString() + "records";
		} else {
			delete = delete + ": no records found";
		}
		pm.close();
		return delete;
	}
}
