package info.esblurock.reaction.server.queries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.FetchGroup;
import javax.jdo.FetchPlan;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.QueryResultList;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.transaction.TransactionInfo;

/**
 * A set of routines performing general queries (the object is to isolate the
 * routines which access to the database)
 * 
 * @author edwardblurock
 *
 */
public class QueryBase {

	private static final Logger log = Logger.getLogger(QueryBase.class.getName());
	public static String notfound = "NOT FOUND";
	
	private static int entityLimit = 500;

	public QueryBase() {

	}

	static public DatabaseObject getObjectById(Class cls, String key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatabaseObject obj = (DatabaseObject) pm.getObjectById(cls, key);
		return obj;
	}

	static public void deleteDatabaseObject(DatabaseObject obj) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.flush();
		pm.deletePersistent(obj);
		pm.close();
	}
	
	static public void deleteWithStringKey(Class cls, String key) throws IOException {
		try {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Object objectById = pm.getObjectById(cls, key);
			pm.deletePersistent(objectById);
		} catch (Exception ex) {
			System.out.println("Exception on deleteWithStringKey assuming can't be found: " + ex.toString());
			throw new IOException(notfound);
		}
	}

	/**
	 * Delete objects of classtype with propertyname == propertyvalue
	 * 
	 * @param classtype
	 *            The class of the items to delete.
	 * @param propertyname:
	 *            The property name to search for
	 * @param propertyvalue:
	 *            The value of the property
	 */
	static public void deleteFromIdentificationCode(Class classtype, String propertyname, String propertyvalue) {
		Filter propertyF = new FilterPredicate(propertyname, FilterOperator.EQUAL, propertyvalue);
		deleteWithFilter(classtype,propertyF);
	}
	
	static public void deleteWithFilter(Class classtype, Filter filter) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Cursor cursor = null;

		FetchOptions options = FetchOptions.Builder.withLimit(entityLimit);
		Query query = new Query(classtype.getSimpleName()).setFilter(filter);
		QueryResultList<Entity> currentBatch = datastore.prepare(query).asQueryResultList(options);
		while(currentBatch != null && currentBatch.size() > 0) {		
			ArrayList<Key> keys = new ArrayList<Key>();
			for(Entity entity: currentBatch) {
				keys.add(entity.getKey());
			}
			datastore.delete(keys);
			cursor = currentBatch.getCursor();
			options = FetchOptions.Builder.withLimit(entityLimit);
			options.startCursor(cursor);
			currentBatch = datastore.prepare(query).asQueryResultList(options);
		}
	}
	static void Batch(QueryResultList<Entity> currentBatch) {
		ArrayList<Key> keys = new ArrayList<Key>();
		for(Entity entity: currentBatch) {
			keys.add(entity.getKey());
		}
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		pm.getFetchPlan()
		.setGroup(FetchGroup.ALL)
		.setDetachmentOptions(FetchPlan.DETACH_LOAD_FIELDS);
		List<DatabaseObject> set = (List<DatabaseObject>) pm.getObjectsById(keys);
		List<DatabaseObject> detached = (List<DatabaseObject>) pm.detachCopyAll(set);
		pm.close();
	}

	/**
	 * Fetch the keys of classtype with propertyname == propertyvalue
	 * 
	 * @param classtype
	 *            The class of the keys to fetch.
	 * @param propertyname:
	 *            The property name to search for
	 * @param propertyvalue:
	 *            The value of the property
	 * 
	 * @return The list of keys of type {@link Key}
	 */
	static public ArrayList<Key> getObjectKeysFromSingleProperty(Class classtype, String propertyname,
			String propertyvalue) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Filter propertyF = new FilterPredicate(propertyname, FilterOperator.EQUAL, propertyvalue);

		Query q = new Query(classtype.getName()).setFilter(propertyF).setKeysOnly();
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterator();
		ArrayList<Key> lst = new ArrayList<Key>();
		if (iter.hasNext()) {
			while (iter.hasNext()) {
				Entity next = iter.next();
				lst.add(next.getKey());
			}
		}
		return lst;
	}

	/**
	 * Fetch objects of classtype with propertyname == propertyvalue
	 * 
	 * @param classtype
	 *            The class of the objects to fetch.
	 * @param propertyname:
	 *            The property name to search for
	 * @param propertyvalue:
	 *            The value of the property
	 * @return The list of objects matching the criteria
	 * @throws IOException
	 */
	static public List<DatabaseObject> getDatabaseObjectsFromSingleProperty(String classname, String propertyname,
			String propertyvalue) throws IOException {
		Filter keywordFilter = new FilterPredicate(propertyname, FilterOperator.EQUAL, propertyvalue);
		List<DatabaseObject> set = null;
		try {
			set = getDatabaseObjectsFromFilter(classname, keywordFilter);
		} catch (IOException ex) {
			throw new IOException(ex.toString() + " not found with : " + propertyname + "=" + propertyvalue);
		}
		return set;
	}
	static public List<DatabaseObject> getDatabaseObjectsFromProperties(String classname, 
			ArrayList<String> propertynames,
			ArrayList<String> propertyvalues) throws IOException {
		Iterator<String> iter = propertyvalues.iterator();
		ArrayList<Filter> filters = new ArrayList<Filter>();
		for(String propertyname : propertynames) {
			Filter filter = new FilterPredicate(propertyname, FilterOperator.EQUAL, iter.next());
			filters.add(filter);
		}
		Filter andfilter = CompositeFilterOperator.and(filters);

		List<DatabaseObject> set = null;
		try {
			set = getDatabaseObjectsFromFilter(classname, andfilter);
		} catch (IOException ex) {
			throw new IOException(ex.toString() + " not found with : " + propertynames + "=" + propertyvalues);
		}
		return set;
	}
	static public DatabaseObject getFirstOjbectFromProperties(String classname, ArrayList<String> propertynames,
			ArrayList<String> propertyvalues) throws IOException {
		DatabaseObject object = null;
		List<DatabaseObject> objs = getDatabaseObjectsFromProperties(classname,propertynames,propertyvalues);
		if(objs.size() > 0) {
			object = objs.get(0);
		} else {
			throw new IOException(classname + " not found with : " + propertynames + "=" + propertyvalues);
		}
		return object;
	}
	static public DatabaseObject getFirstDatabaseObjectsFromSingleProperty(String classname,
			String propertyname, String propertyvalue) throws IOException {
		List<DatabaseObject> objs = getDatabaseObjectsFromSingleProperty(classname,propertyname,propertyvalue);
		DatabaseObject object = null;
		if(objs.size() > 0) {
			object = objs.get(0);
		} else {
			throw new IOException(classname + ": Not found with : " + propertyname + "=" + propertyvalue);
		}
		return object;
	}

	/**
	 * Fetch objects of classtype with propertyname == propertyvalue for a given
	 * user
	 * 
	 * @param classtype
	 *            The class of the objects to fetch.
	 * @param propertyname:
	 *            The property name to search for
	 * @param propertyvalue:
	 *            The value of the property
	 * @return The list of objects matching the criteria
	 * @throws IOException
	 */
	static public List<DatabaseObject> getUserDatabaseObjectsFromSingleProperty(String classname, String user,
			String propertyname, String propertyvalue) throws IOException {
		Filter userFilter = new FilterPredicate("user", FilterOperator.EQUAL, user);
		Filter keywordFilter = new FilterPredicate(propertyname, FilterOperator.EQUAL, propertyvalue);
		Filter andfilter = CompositeFilterOperator.and(keywordFilter, userFilter);
		List<DatabaseObject> set = null;
		try {
			set = getDatabaseObjectsFromFilter(classname, andfilter);
		} catch (IOException ex) {
			throw new IOException(ex.toString() + " not found with : " + propertyname + "=" + propertyvalue + ", User='"
					+ user + "')");
		}
		return set;
	}

	static public DatabaseObject getFirstDatabaseObjectsFromSingleProperty(String classname, String user,
			String propertyname, String propertyvalue) throws IOException {
		List<DatabaseObject> objs = getUserDatabaseObjectsFromSingleProperty(classname,user,propertyname,propertyvalue);
		DatabaseObject object = null;
		if(objs.size() > 0) {
			object = objs.get(0);
		} else {
			throw new IOException(classname + ": Not found with : " + propertyname + "=" + propertyvalue + ", User='"
					+ user + "')");
		}
		return object;
	}
	static public ArrayList<ArrayList<Object>> getDatabaseEntitiesFromFilter(String classname, 
			Filter filter, ArrayList<String> propertynames) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ArrayList<ArrayList<Object>> total = new ArrayList<ArrayList<Object>>();
		try {
			Cursor cursor = null;
			Class cls = Class.forName(classname);
			DatabaseObject example = (DatabaseObject) cls.newInstance();
			FetchOptions options = FetchOptions.Builder.withLimit(entityLimit);
			Query query = null;
			if(filter != null) {
				query = new Query(example.getClass().getSimpleName()).setFilter(filter);
			} else {
				query = new Query(example.getClass().getSimpleName());
			}
			QueryResultList<Entity> currentBatch = datastore.prepare(query).asQueryResultList(options);
			while(currentBatch != null && currentBatch.size() > 0) {
				getEntityPropertiesBatch(cls, currentBatch, total,propertynames);
				cursor = currentBatch.getCursor();
				options = FetchOptions.Builder.withLimit(entityLimit);
				options.startCursor(cursor);
				currentBatch = datastore.prepare(query).asQueryResultList(options);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return total;
	}
	static public void getEntityPropertiesBatch(Class type, 
			QueryResultList<Entity> currentBatch, 
			ArrayList<ArrayList<Object>> results,
			ArrayList<String> propertynames) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.getFetchPlan()
		.setGroup(FetchGroup.ALL)
		.setDetachmentOptions(FetchPlan.DETACH_LOAD_FIELDS);
		
		for(Entity entity: currentBatch) {
			ArrayList<Object> entityresults = new ArrayList<Object>();
			for(String property: propertynames) {
				Object obj = entity.getProperty(property);
				entityresults.add(obj);
			}
			results.add(entityresults);
		}
		pm.close();
	}

	
	static public List<DatabaseObject> getDatabaseObjectsFromFilter(String classname, Filter filter)
			throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ArrayList<DatabaseObject> total = new ArrayList<DatabaseObject>();
		try {
			Cursor cursor = null;
			Class cls = Class.forName(classname);
			DatabaseObject example = (DatabaseObject) cls.newInstance();
			FetchOptions options = FetchOptions.Builder.withLimit(entityLimit);
			Query query = new Query(example.getClass().getSimpleName()).setFilter(filter);
			QueryResultList<Entity> currentBatch = datastore.prepare(query).asQueryResultList(options);
			while(currentBatch != null && currentBatch.size() > 0) {
				List<DatabaseObject> batch = getBatch(cls,currentBatch);
				total.addAll(batch);
				cursor = currentBatch.getCursor();
				options = FetchOptions.Builder.withLimit(entityLimit);
				options.startCursor(cursor);
				currentBatch = datastore.prepare(query).asQueryResultList(options);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return total;

	}
	static public List<DatabaseObject> getBatch(Class type, QueryResultList<Entity> currentBatch) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.getFetchPlan()
		.setGroup(FetchGroup.ALL)
		.setDetachmentOptions(FetchPlan.DETACH_LOAD_FIELDS);
		ArrayList<DatabaseObject> detacheds = new ArrayList<DatabaseObject>();
		for(Entity entity: currentBatch) {
			//keys.add(entity.getKey());
			DatabaseObject obj = (DatabaseObject) pm.getObjectById(type,entity.getKey());
			DatabaseObject detached = pm.detachCopy(obj);
			detacheds.add(detached);
		}
		pm.close();
		return detacheds;
	}
	static public ArrayList<String> generateSynonyms(String keyword) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter objectfilter =
				  new FilterPredicate("synonym",FilterOperator.EQUAL,keyword);
		Query q = new Query("KeywordStandardization").setFilter(objectfilter);
		ArrayList<String> synonyms = new ArrayList<>();
		Iterator<Entity> iter = datastore.prepare(q).asIterator();
		while(iter.hasNext()) {
			Entity entity = iter.next();
			String standard = (String) entity.getProperty("standardKeyword");
			synonyms.add(standard);
		}
		return synonyms;
	}

	
}
