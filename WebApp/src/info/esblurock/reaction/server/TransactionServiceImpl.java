package info.esblurock.reaction.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import info.esblurock.reaction.client.panel.transaction.TransactionService;
import info.esblurock.reaction.data.delete.DeleteTransactionInfoAndObject;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.InputTextSource;
import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.server.datastore.PMF;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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
		javax.jdo.Query q = pm.newQuery(TransactionInfo.class);
		List<TransactionInfo> results = (List<TransactionInfo>) q.execute();
		ArrayList<TransactionInfo> lst = new ArrayList<TransactionInfo>();
		for(TransactionInfo transaction: results) {
			TransactionInfo t = pm.detachCopy(transaction);
			lst.add(t);
		}
		return lst;
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.panel.transaction.TransactionService#getAllUploadTransactions()
	 */
	public List<TextSetUploadData> getAllUploadTransactions() {
		javax.jdo.Query q = pm.newQuery(TextSetUploadData.class);
		List<TextSetUploadData> results = (List<TextSetUploadData>) q.execute();
		ArrayList<TextSetUploadData> lst = new ArrayList<TextSetUploadData>();
		System.out.println("getAllUploadTransactions(): " + results.size());
		for(TextSetUploadData transaction: results) {
			if(transaction.getDescription() == null) {
				System.out.println("transaction Description is null");
			} else {
				System.out.println("transaction Description is not null\n" + transaction.getDescription().toString());
			}
			TextSetUploadData t = pm.detachCopy(transaction);
			if(t.getDescription() == null) {
				System.out.println("Description is null");
			} else {
				System.out.println("Description is not null");
			}
			ArrayList<InputTextSource> datalst = transaction.getInputTextSources();
			if(datalst == null) {
				System.out.println("InputTextSource is null");
				lst.add(t);
			} else {
				System.out.println("InputTextSource size is " + datalst.size());
				TextSetUploadData trans = new TextSetUploadData(t.getDescription());
				trans.setKey(t.getKey());
				for(InputTextSource source : datalst) {
					InputTextSource s = pm.detachCopy(source);
					trans.addInputTextSource(s);
				}
				System.out.println("getAllUploadTransactions(): adding TextSetUploadData to list");
				lst.add(trans);
			}
			
		}
		System.out.println("getAllUploadTransactions(): Done");
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
		return delete.deleteFromObjectKey(key);
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
		return delete.deleteFromTypeAndKeyword(objecttype,keyword);
	}

	@Override
	public String deleteTransactionInfoFromKey(String transactionkey) throws IOException {
		DeleteTransactionInfoAndObject delete = new DeleteTransactionInfoAndObject();
		return delete.deleteTransactionInfoFromKey(transactionkey);
		
	}
	public String removeFromRDFsFromDate(Date date) {
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
		return delete;
	}
}
