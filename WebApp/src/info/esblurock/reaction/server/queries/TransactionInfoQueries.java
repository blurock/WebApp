package info.esblurock.reaction.server.queries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.apphosting.utils.config.AppYaml.Handler.LoginType;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import info.esblurock.reaction.data.delete.DeleteTransactionInfoAndObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.server.datastore.PMF;

public class TransactionInfoQueries {
	private static final Logger log = Logger.getLogger(TransactionInfoQueries.class.getName());

	
	public static TransactionInfo getTransactionFromKeyString(String keyS) throws IOException {
		Key key = KeyFactory.createKey(TransactionInfo.class.getSimpleName(), keyS);
		return getTransactionFromKey(key);
	}
	
	public static TransactionInfo getTransactionFromKey(Key fullkey) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		TransactionInfo info = null;
		try {
			Object transaction = pm.getObjectById(TransactionInfo.class, fullkey);
			if (transaction != null) {
				if (transaction instanceof TransactionInfo) {
					info = (TransactionInfo) transaction;
				} else {
					throw new IOException("Key: " + fullkey
							+ "expected to be TransactionInfo, but was of type " 
							+ transaction.getClass().toString());
				}
			} else {
				pm.close();
				throw new IOException("No Transaction associated with object");
			}

		} catch (Exception e) {
			pm.close();
			String ans = "ERROR: Unable to delete id: " + fullkey+ "/n" + e.toString();
			throw new IOException(ans);
		} finally {
			pm.close();
		}
		return info;
	}
	/**
	 *  getTransaction
	 * From the object key find the TransactionInfo  (from storedObjectKey).
	 *
	 * @param key the key of the object
	 * @return the associated transaction
	 * @throws IOException 
	 */
	static public TransactionInfo
		getFirstTransactionFromKeywordAndObjectType(String key, String classname) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Filter keywordfilter = new FilterPredicate("keyword", FilterOperator.EQUAL, key);
		Filter classfilter = new FilterPredicate("transactionObjectType", FilterOperator.EQUAL, classname);
		Filter andfilter = CompositeFilterOperator.and(classfilter, keywordfilter);
		
		Query q = new Query("TransactionInfo").setFilter(andfilter);
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterable().iterator();
		TransactionInfo info = null;
		if (iter.hasNext()) {
			Entity entity = iter.next();
			info = (TransactionInfo) pm.getObjectById(TransactionInfo.class, entity.getKey());
			if(iter.hasNext()) {
				log.log(Level.WARNING,"Transaction with key='" + key + " and class='" + classname
						+ "' has more than one match");
			}
		} else {
			throw new IOException("TransactionInfo not found with object key: " + key + ","  + classname);
		}
		return info;
	}
	
	static public void transactionExists(String key, String classname) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Filter keywordfilter = new FilterPredicate("keyword", FilterOperator.EQUAL, key);
		Filter classfilter = new FilterPredicate("transactionObjectType", FilterOperator.EQUAL, classname);
		Filter andfilter = CompositeFilterOperator.and(classfilter, keywordfilter);
		
		Query q = new Query("TransactionInfo").setFilter(andfilter);
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterable().iterator();
		if (iter.hasNext()) {
			throw new IOException("Transaction with key='" + key + " and class='" + classname
					+ "' exists..\n delete before processing");
		}
	}
	static public TransactionInfo getFirstTransactionFromObjectKey(String key) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String queryS = "storedObjectKey == keyparameter";
		javax.jdo.Query query = pm.newQuery(TransactionInfo.class);
		query.setFilter(queryS);
		query.declareParameters("key");
		TransactionInfo info = null;
		try {
			List<TransactionInfo> lst = (List<TransactionInfo>) query.execute(key);
			if(!lst.isEmpty()) {
				if(lst.size() > 1) {
					String message = "More than one TransactionInfo with a storedObjectKey='" + key + "'";
					log.log(Level.WARNING,message);
				}
				info = lst.get(0);
			}
		} finally {
			query.closeAll();
		}
		return info;
	}
/*	
		
		Query q = new Query("TransactionInfo");
		Filter keyfilter =
				  new FilterPredicate("storedObjectKey",FilterOperator.EQUAL,key);
		q.setFilter(keyfilter);
		try {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			PreparedQuery pq = datastore.prepare(q);
			boolean haselement = pq.asIterable().iterator().hasNext();
			log.info("getTransactionFromTypeAndKeyword" + haselement);
			ArrayList<TransactionInfo> set = new ArrayList<TransactionInfo>();
			for(Entity entity : pq.asIterable()) {				
				Key transactioninfokey = entity.getKey();
				TransactionInfo info = pm.getObjectById(TransactionInfo.class,transactioninfokey);
				set.add(info)
			}
			
		}  finally {
			  pm.close();
		}
		PreparedQuery pq = datastore.prepare(q);
		boolean haselement = pq.asIterable().iterator().hasNext();
		log.info("getTransactionFromTypeAndKeyword" + haselement);
		System.out.println("getTransactionFromTypeAndKeyword" + haselement);
		
		
		if(info == null) {
			throw new IOException("TransactionInfo not found with object key: " + key);
		} else {
			
		}
		
	}
	*/
}
