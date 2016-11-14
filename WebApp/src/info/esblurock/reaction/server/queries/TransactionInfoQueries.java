package info.esblurock.reaction.server.queries;

import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.FetchGroup;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Key;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.upload.types.CreateBufferedReaderForSourceFile;

public class TransactionInfoQueries {
	private static final Logger log = Logger.getLogger(TransactionInfoQueries.class.getName());

	/**
	 * 
	 * @param keyS
	 *            The String key of the {@link TransactionInfo} (from
	 *            {@link DatabaseObject})
	 * @return The valid transaction
	 * @throws IOException
	 */
	public static TransactionInfo getTransactionFromKeyString(String keyS) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		TransactionInfo info = null;
		try {
			info = pm.getObjectById(TransactionInfo.class, keyS);
			if (info == null) {
				pm.close();
				throw new IOException("No Transaction associated with object");
			}
		} catch (Exception e) {
			pm.close();
			String ans = "ERROR: Unable to fetch TransactionInfo with id: " + keyS + "/n" + e.toString();
			throw new IOException(ans);
		} finally {
			pm.close();
		}
		return info;
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
					throw new IOException("Key: " + fullkey + "expected to be TransactionInfo, but was of type "
							+ transaction.getClass().toString());
				}
			} else {
				pm.close();
				throw new IOException("No Transaction associated with object");
			}

		} catch (Exception e) {
			pm.close();
			String ans = "ERROR: Unable to fetch id: " + fullkey + "/n" + e.toString();
			throw new IOException(ans);
		} finally {
			pm.close();
		}
		return info;
	}

	/**
	 * getTransaction From the object key find the TransactionInfo (from
	 * storedObjectKey).
	 *
	 * @param key
	 *            the key of the object
	 * @return the associated transaction
	 * @throws IOException
	 */
	static public TransactionInfo getFirstTransactionFromKeywordUserSourceCodeAndObjectType(String user, String key,
			String sourceCode, String classname) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Filter sourceCodeFilter = new FilterPredicate("sourceCode", FilterOperator.EQUAL, sourceCode);
		Filter userFilter = new FilterPredicate("user", FilterOperator.EQUAL, user);
		Filter keywordFilter = new FilterPredicate("keyword", FilterOperator.EQUAL, key);
		Filter classFilter = new FilterPredicate("transactionObjectType", FilterOperator.EQUAL, classname);
		Filter andfilter = CompositeFilterOperator.and(classFilter, keywordFilter, userFilter, sourceCodeFilter);

		Query q = new Query("TransactionInfo").setFilter(andfilter);
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterable().iterator();
		TransactionInfo info = null;
		if (iter.hasNext()) {
			Entity entity = iter.next();
			info = (TransactionInfo) pm.getObjectById(TransactionInfo.class, entity.getKey());
			if (iter.hasNext()) {
				log.log(Level.WARNING,
						"Transaction with key='" + key + " and class='" + classname + "' has more than one match");
			}
		} else {
			throw new IOException("TransactionInfo not found with object key: " + key + "," + classname);
		}
		return info;
	}

	/**
	 * getTransaction From the object key find the TransactionInfo (from
	 * storedObjectKey).
	 *
	 * @param key
	 *            the key of the object
	 * @return the associated transaction
	 * @throws IOException
	 */
	static public UploadFileTransaction getFirstUploadFileTransactionFromKeywordUserSourceCodeAndObjectType(String user,
			String filename) throws IOException {
		/*
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
*/
		Filter sourceCodeFilter = new FilterPredicate("filename", FilterOperator.EQUAL, filename);
		Filter userFilter = new FilterPredicate("user", FilterOperator.EQUAL, user);
		/*
		Filter typeFilter = new FilterPredicate("sourceType", FilterOperator.EQUAL,
				CreateBufferedReaderForSourceFile.uploadFileAsSource);
				*/
		Filter andfilter = CompositeFilterOperator.and(userFilter, sourceCodeFilter);
		List<DatabaseObject> objs = QueryBase.getDatabaseObjectsFromFilter(UploadFileTransaction.class.getName(), andfilter);
		UploadFileTransaction info = null;
		if (objs.size() > 0) {
			int sourceID = 0;
			for (DatabaseObject obj : objs) {
				UploadFileTransaction nextinfo = (UploadFileTransaction) obj;
				int source = Integer.valueOf(nextinfo.getFileCode());
				if (source > sourceID) {
					info = nextinfo;
					sourceID = source;
				}
			}
		} else {
			throw new IOException("UploadFileTransaction not found with object key: " + user + "," + filename);
		}
		return info;
	}

	/**
	 * getTransaction From the object key find the TransactionInfo (from
	 * storedObjectKey).
	 *
	 * @param key
	 *            the key of the object
	 * @return the associated transaction
	 * @throws IOException
	 */
	static public ArrayList<TransactionInfo> getTransactionFromKeywordAndObjectType(String user, String key,
			String classname, boolean singleton) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Filter userFilter = new FilterPredicate("user", FilterOperator.EQUAL, user);
		Filter keywordFilter = new FilterPredicate("keyword", FilterOperator.EQUAL, key);
		Filter classFilter = new FilterPredicate("transactionObjectType", FilterOperator.EQUAL, classname);
		Filter andfilter = CompositeFilterOperator.and(classFilter, keywordFilter, userFilter);

		Query q = new Query("TransactionInfo").setFilter(andfilter);
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterable().iterator();
		ArrayList<TransactionInfo> set = new ArrayList<TransactionInfo>();
		if (iter.hasNext()) {
			while (iter.hasNext()) {
				Entity entity = iter.next();
				TransactionInfo info = (TransactionInfo) pm.getObjectById(TransactionInfo.class, entity.getKey());
				set.add(info);
			}
		} else {
			throw new IOException("TransactionInfo not found with object key: " + key + "," + classname);
		}
		if (singleton) {
			TransactionInfo answer = null;
			for (TransactionInfo info : set) {
				if (answer == null) {
					answer = info;
				} else {
					if (answer.getCreationDate().before(info.getCreationDate())) {
						answer = info;
					}
				}
			}
			set = new ArrayList<TransactionInfo>();
			set.add(answer);
		}
		return set;
	}

	/**
	 * getTransaction From the object key find the TransactionInfo (from
	 * storedObjectKey).
	 *
	 * @param key
	 *            the key of the object
	 * @return the associated transaction
	 * @throws IOException
	 */
	static public List<DatabaseObject> getTransactionFromKeywordAndUser(String user, String key) throws IOException {
		List<DatabaseObject> dataset = QueryBase.getUserDatabaseObjectsFromSingleProperty(
				"info.esblurock.reaction.data.transaction.TransactionInfo", user, "keyword", key);
		HashMap<String, TransactionInfo> map = new HashMap<String, TransactionInfo>();
		for (DatabaseObject obj : dataset) {
			TransactionInfo info = (TransactionInfo) obj;
			TransactionInfo i = map.get(info.getTransactionObjectType());
			if (i == null) {
				map.put(info.getTransactionObjectType(), info);
			} else {
				if (i.getCreationDate().before(info.getCreationDate())) {
					map.remove(info.getTransactionObjectType());
					map.put(info.getTransactionObjectType(), info);
				}
			}
		}
		ArrayList<DatabaseObject> set = new ArrayList<DatabaseObject>();
		for (String classname : map.keySet()) {
			TransactionInfo info = (TransactionInfo) map.get(classname);
			set.add(info);
		}
		return set;
	}

	/**
	 * getTransaction From the object key find the TransactionInfo (from
	 * storedObjectKey).
	 *
	 * @param key
	 *            the key of the object
	 * @return the associated transaction
	 * @throws IOException
	 */
	static public TransactionInfo getFirstTransactionFromKeywordAndObjectType(String key, String classname)
			throws IOException {

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
			if (iter.hasNext()) {
				log.log(Level.WARNING,
						"Transaction with key='" + key + " and class='" + classname + "' has more than one match");
			}
		} else {
			throw new IOException("TransactionInfo not found with object key: " + key + "," + classname);
		}
		return info;
	}

	/**
	 * From the (input) {@link TransactionInfo}, retrieve the (input)
	 * {@link DatabaseObject}
	 * 
	 * The object key, storedObjectKey, and the class name
	 * (transactionObjectType) within {@link TransactionInfo} is used to
	 * retrieve the {@link DatabaseObject}
	 * 
	 * @param transaction
	 *            to determine which database object to retrieve.
	 * @return The corresponding input {@link DatabaseObject}
	 * @throws ClassNotFoundException
	 */
	static public DatabaseObject getClassObjectFromTransactionInfo(TransactionInfo transaction)
			throws ClassNotFoundException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String classS = transaction.getTransactionObjectType();
		// Class classC = Class.forName(classS);
		String key = transaction.getStoredObjectKey();
		pm.getFetchPlan().setGroup(FetchGroup.ALL);
		pm.getFetchPlan().setMaxFetchDepth(-1);
		DatabaseObject object = (DatabaseObject) pm.getObjectById(Class.forName(classS), key);
		pm.close();
		return object;
	}

	static public void transactionExists(String user, String key, String classname) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		System.out.println("transactionExists: " + key + ",  " + classname + "," + user);
		Filter userfilter = new FilterPredicate("user", FilterOperator.EQUAL, user);
		Filter keywordfilter = new FilterPredicate("keyword", FilterOperator.EQUAL, key);
		Filter classfilter = new FilterPredicate("transactionObjectType", FilterOperator.EQUAL, classname);
		Filter andfilter = CompositeFilterOperator.and(userfilter, keywordfilter, classfilter);

		Query q = new Query("TransactionInfo").setFilter(andfilter);
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterable().iterator();
		if (iter.hasNext()) {
			String msg = "Transaction with key='" + key + " and class='" + classname + "' exists..";
			throw new IOException(msg);
		} else {
			System.out.println("No transactions found");
		}
	}

	static public TransactionInfo getFirstTransactionFromObjectKey(String key) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String queryS = "storedObjectKey == '" + key + "'";
		javax.jdo.Query query = pm.newQuery(TransactionInfo.class);
		query.setFilter(queryS);
		TransactionInfo info = null;
		try {
			List<TransactionInfo> lst = (List<TransactionInfo>) query.execute();
			if (!lst.isEmpty()) {
				if (lst.size() > 1) {
					String message = "More than one TransactionInfo with a storedObjectKey='" + key + "'";
					log.log(Level.WARNING, message);
				}
				info = lst.get(0);
			}
		} finally {
			query.closeAll();
		}
		return info;
	}
}
