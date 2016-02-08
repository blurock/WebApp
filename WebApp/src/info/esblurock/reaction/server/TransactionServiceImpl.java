package info.esblurock.reaction.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import info.esblurock.reaction.client.panel.transaction.TransactionService;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.InputTextSource;
import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.server.datastore.PMF;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TransactionServiceImpl extends RemoteServiceServlet implements
		TransactionService {

	PersistenceManager pm = PMF.get().getPersistenceManager();

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

	public List<TextSetUploadData> getAllUploadTransactions() {
		javax.jdo.Query q = pm.newQuery(TextSetUploadData.class);
		List<TextSetUploadData> results = (List<TextSetUploadData>) q.execute();
		ArrayList<TextSetUploadData> lst = new ArrayList<TextSetUploadData>();
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
				lst.add(trans);
			}
			
		}
		return lst;
		
	}
	@Override
	public List<TransactionInfo> getTransactions(String user, String keyword,
			String objecttype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeTransaction(String key) throws Exception {
		String ans = "";
		try {
			Object u = pm.getObjectById(TransactionInfo.class, key);
			if(u != null && u instanceof TransactionInfo) {
				TransactionInfo transaction = (TransactionInfo) u;
				for(String objkey: transaction.getRdfKeyWords()) {
					try {
					System.out.println("RDF: "+ objkey);
					KeywordRDF rdf = pm.getObjectById(KeywordRDF.class, objkey);
					pm.deletePersistent(rdf);
					} catch(JDOObjectNotFoundException ex) {
						System.out.println("Not found: "+ objkey);
					}
				}
				System.out.println("Done RDF");
				if(transaction.getStoredObjectKey() != null) {
					System.out.println("Delete: " + transaction.getTransactionObjectType() + "  " + transaction.getStoredObjectKey());
					Object o = pm.getObjectById(Class.forName(transaction.getTransactionObjectType()), transaction.getStoredObjectKey());
					System.out.println("Found");
					pm.deletePersistent(o);
					System.out.println("Done");
				}
				pm.deletePersistent(u);
				ans = "SUCCESS: Deleted an id: " + key;

			}
			
		} catch (Exception e) {
			ans = "ERROR: Unable to delete id: " + key;
			System.out.println("Exception: " + e);
			//throw e;
		}
		return ans;
	}

	@Override
	public Set<String> getUserSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getKeywordSet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getObjectTypeSet() {
		// TODO Auto-generated method stub
		return null;
	}

}
