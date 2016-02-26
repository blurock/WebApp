package info.esblurock.reaction.data.delete;

import java.io.IOException;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;

import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.server.datastore.PMF;

/**
 * The Class DeleteTransactionInfo.
 * 
 * This is the class that is used to delete all associated objects from an object:
 * This includes:
 * <ul>
 * <li> The associated {@link TransactionInfo} and the associated RDF info.
 * <li> The objects contained within the object (through {@link DeleteDataStructures})
 * <li> The object itself
 * </ul>
 */
public class DeleteTransactionInfoAndObject {
	/** The persistence manager. */
	protected PersistenceManager pm = PMF.get().getPersistenceManager();

	/** deleteFromObjectKey
	 * 
	 * This is the delete routine that should be called by all objects to ensure that all associated objects are deleted.
	 * 
	 * Delete transaction info and the object from object key
	 * 
	 * The {@link TransactionInfo} is found using the object key (getTransactionFromObjectKey) and
	 * then the {@link TransactionInfo} is delete (which also deletes the object.
	 *
	 * @param key the key of the object to be deleted
	 * @return the string from the delete
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws  
	 */
	public String deleteFromTypeAndKeyword(String objectType, String keyword) throws IOException {
		TransactionInfo info = getTransactionFromTypeAndKeyword(objectType, keyword);
		String ans = this.delete(info);
		return ans;
	}
	/** deleteFromObjectKey
	 * 
	 * This is the delete routine that should be called by all objects to ensure that all associated objects are deleted.
	 * 
	 * Delete transaction info and the object from object key
	 * 
	 * The {@link TransactionInfo} is found using the object key (getTransactionFromObjectKey) and
	 * then the {@link TransactionInfo} is delete (which also deletes the object.
	 *
	 * @param key the key of the object to be deleted
	 * @return the string from the delete
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws  
	 */
	public String deleteFromObjectKey(String key) throws IOException {
		TransactionInfo info = getTransactionFromObjectKey(key);
		String ans = this.delete(info);
		return ans;
	}
	/** Delete the {@link TransactionInfo} from its key.
	 * @param fullkey the key to the {@link TransactionInfo} from the database
	 * @return the string showing the result of the delete
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String deleteTransactionInfoFromKey(String fullkey) throws IOException {
		String ans = "";
		try {
			Object transaction = pm.getObjectById(TransactionInfo.class, fullkey);
			if (transaction != null) {
				if (transaction instanceof TransactionInfo) {
					ans = delete((TransactionInfo) transaction);
				} else {
					throw new IOException("Key: " + fullkey
							+ "expected to be TransactionInfo, but was of type " 
							+ transaction.getClass().toString());
				}
			} else {
				throw new IOException("No Transaction associated with object");
			}

		} catch (Exception e) {
			ans = "ERROR: Unable to delete id: " + fullkey;
			System.out.println("Exception: " + e);
			// throw e;
		}
		
		return ans;
	}

	/** Delete the {@link TransactionInfo} and its associated objects
	 * 
	 * this deletes:
	 * <ul>
	 * <li> the list of RDF classes
	 * <li> the associated object (by calling its delete routine
	 * <li> The TransactionInfo itself
	 * </ul>
	 * 
	 * The object is delete by finding the object with the object key
	 * 
	 * <ul>
	 * <li>the getTransactionObjectType() of {@link TransactionInfo} is called to get the string name of the object
	 * <li>This is used to find the delete routine in {@link DeleteDataStructures} class.
	 * <li>The delete routine is called to delete the object
	 *</ul>
	 *
	 * @param transaction the transaction
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	private String delete(TransactionInfo transaction) throws IOException {
		StringBuilder build = new StringBuilder();
		boolean error = false;
		build.append("ERROR:  TransactionInfo: \n");
		for (String objkey : transaction.getRdfKeyWords()) {
			try {
				System.out.println("Delete RDF: " + objkey);
				KeywordRDF rdf = pm.getObjectById(KeywordRDF.class, objkey);
				pm.deletePersistent(rdf);
			} catch (JDOObjectNotFoundException ex) {
				error = true;
				build.append("RDF not found: " + objkey + "\n");
			}
		}
		System.out.println("Done Deleting RDF");

		if (transaction.getStoredObjectKey() != null) {
			String fullclassname = transaction.getTransactionObjectType();
			String key = transaction.getStoredObjectKey();
			System.out.println("Delete: " + fullclassname + "  " + key);
			DeleteDataStructures.deleteObject(fullclassname, key);
			System.out.println("Done deleting object");
		}

		pm.deletePersistent(transaction);
		String ans = "SUCCESS";
		if(error) {
			throw new IOException(build.toString());
		}

		return ans;
	}
	/**
	 *  getTransaction
	 * From the object key find the TransactionInfo  (from storedObjectKey).
	 *
	 * @param key the key of the object
	 * @return the associated transaction
	 * @throws IOException 
	 */
	private TransactionInfo getTransactionFromObjectKey(String key) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		TransactionInfo info = null;
		Filter keyfilter =
				  new FilterPredicate("transactionObjectType",FilterOperator.EQUAL,key);
		Query q = new Query("TransactionInfo").setKeysOnly();
		q.setFilter(keyfilter);
		PreparedQuery pq = datastore.prepare(q);
		boolean haselement = pq.asIterable().iterator().hasNext();
		System.out.println("getTransactionFromTypeAndKeyword" + haselement);
		for(Entity entity : pq.asIterable()) {
			
			System.out.println("Entity Properties" + entity.getProperties().keySet());
			Key transactioninfokey = entity.getKey();
			System.out.println("TransactionInfo key: " + transactioninfokey);
			info = pm.getObjectById(TransactionInfo.class,transactioninfokey);
		}
		if(info == null) {
			throw new IOException("TransactionInfo not found with object key: " + key);
		} else {
			
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
	private TransactionInfo getTransactionFromTypeAndKeyword(String objectType, String keyword) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		TransactionInfo info = null;
		System.out.println("getTransactionFromTypeAndKeyword: " + keyword + ","  + objectType);
		Filter typefilter =
				  new FilterPredicate("transactionObjectType",FilterOperator.EQUAL,objectType);
		Filter keywordfilter =
				  new FilterPredicate("keyword",FilterOperator.EQUAL,keyword);
		Filter andfilter =
				  CompositeFilterOperator.and(typefilter, keywordfilter);

		
		Query q = new Query("TransactionInfo").setKeysOnly();
		q.setFilter(andfilter);
		PreparedQuery pq = datastore.prepare(q);
		boolean haselement = pq.asIterable().iterator().hasNext();
		System.out.println("getTransactionFromTypeAndKeyword" + haselement);
		for(Entity entity : pq.asIterable()) {
			
			System.out.println("Entity Properties" + entity.getProperties().keySet());
			Key transactioninfokey = entity.getKey();
			System.out.println("TransactionInfo key: " + transactioninfokey);
			info = pm.getObjectById(TransactionInfo.class,transactioninfokey);
		}
		if(info == null) {
			throw new IOException("TransactionInfo not found with object key: " + keyword + ","  + objectType);
		} else {
			
		}
		return info;
	}


}
