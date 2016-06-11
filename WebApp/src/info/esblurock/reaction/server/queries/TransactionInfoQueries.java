package info.esblurock.reaction.server.queries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.google.appengine.api.datastore.KeyFactory;

import info.esblurock.reaction.client.data.DatabaseObject;
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
					throw new IOException("Key: " + fullkey + "expected to be TransactionInfo, but was of type "
							+ transaction.getClass().toString());
				}
			} else {
				pm.close();
				throw new IOException("No Transaction associated with object");
			}

		} catch (Exception e) {
			pm.close();
			String ans = "ERROR: Unable to delete id: " + fullkey + "/n" + e.toString();
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
	static public ArrayList<TransactionInfo> getTransactionFromKeywordAndObjectType(String user, String key,
			String classname) throws IOException {
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
		List<DatabaseObject> set =
		QueryBase.getUserDatabaseObjectsFromSingleProperty("info.esblurock.reaction.data.transaction.TransactionInfo",user,"keyword",key);
		/*
		ArrayList<DatabaseObject> set = new ArrayList<DatabaseObject>();
		try {
			Class cls = Class.forName("info.esblurock.reaction.data.transaction.TransactionInfo");
			DatabaseObject example = (DatabaseObject) cls.newInstance();

			PersistenceManager pm = PMF.get().getPersistenceManager();
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Filter userFilter = new FilterPredicate("user", FilterOperator.EQUAL, user);
			Filter keywordFilter = new FilterPredicate("keyword", FilterOperator.EQUAL, key);
			Filter andfilter = CompositeFilterOperator.and(keywordFilter, userFilter);
			System.out.println("getTransactionFromKeywordAndUser" + example.getClass().getSimpleName() + ", " + example.getClass().getCanonicalName()); 
			Query q = new Query(example.getClass().getSimpleName()).setFilter(andfilter);
			PreparedQuery pq = datastore.prepare(q);
			Iterator<Entity> iter = pq.asIterable().iterator();

			if (iter.hasNext()) {
				while (iter.hasNext()) {
					Entity entity = iter.next();
					TransactionInfo info = (TransactionInfo) pm.getObjectById(example.getClass(), entity.getKey());
					set.add(info);
				}
			} else {
				throw new IOException("TransactionInfo not found with object key: " + key + ", User='" + user + "')");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new IOException(" ClassNotFoundException: TransactionInfo not found with object key: " + key
					+ ", User='" + user + "')");
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new IOException(" InstantiationException: TransactionInfo not found with object key: " + key
					+ ", User='" + user + "')");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new IOException(" IllegalAccessException: TransactionInfo not found with object key: " + key
					+ ", User='" + user + "')");
		}
		*/
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
		Class classC = Class.forName(classS);
		String key = transaction.getStoredObjectKey();
		DatabaseObject object = (DatabaseObject) pm.getObjectById(classC, key);
		return object;
	}

	static public void transactionExists(String user, String key, String classname) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		System.out.println("transactionExists: " + key + ",  " + classname);
		Filter userfilter = new FilterPredicate("user", FilterOperator.EQUAL, user);
		Filter keywordfilter = new FilterPredicate("keyword", FilterOperator.EQUAL, key);
		Filter classfilter = new FilterPredicate("transactionObjectType", FilterOperator.EQUAL, classname);
		Filter andfilter = CompositeFilterOperator.and(userfilter, keywordfilter, classfilter);

		Query q = new Query("TransactionInfo").setFilter(andfilter);
		PreparedQuery pq = datastore.prepare(q);
		/*
		 * for(Entity entity : pq.asIterable()) {
		 * System.out.println(entity.getProperties());
		 * if(entity.getProperty("keyword").equals(key)) { System.out.println(
		 * "Key match"); }
		 * if(entity.getProperty("transactionObjectType").equals(key)) {
		 * System.out.println("transactionObjectType match"); }
		 * 
		 * }
		 */
		Iterator<Entity> iter = pq.asIterable().iterator();
		if (iter.hasNext()) {
			String msg = "Transaction with key='" + key + " and class='" + classname + "' exists..";
			System.out.println(msg);
			throw new IOException(msg);
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
