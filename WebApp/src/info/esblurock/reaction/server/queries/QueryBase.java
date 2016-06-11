package info.esblurock.reaction.server.queries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.server.datastore.PMF;

/**
 * A set of routines performing general queries (the object is to isolate the
 * routines which access to the database)
 * 
 * @author edwardblurock
 *
 */
public class QueryBase {

	private static final Logger log = Logger.getLogger(QueryBase.class.getName());

	public QueryBase() {

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
	public void deleteFromIdentificationCode(Class classtype, String propertyname, String propertyvalue) {
		log.info("UsingIdentificationCode   deleteFromIdentificationCode: " + classtype.getName() + propertyname + ", "
				+ propertyvalue);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		javax.jdo.Query q = pm.newQuery(classtype);
		String filterS = propertyname + " == '" + propertyvalue + "'";
		log.info("deleteFromIdentificationCode: " + filterS);
		q.setFilter(filterS);
		long ans = q.deletePersistentAll();
		log.info("deleteFromIdentificationCode: " + ans);
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
	public ArrayList<Key> getObjectKeysFromSingleProperty(Class classtype, String propertyname, String propertyvalue) {
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
	 */
	public List<DatabaseObject> getDatabaseObjectsFromSingleProperty(Class classtype, String propertyname,
			String propertyvalue) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		javax.jdo.Query q = pm.newQuery(classtype);
		String filterS = propertyname + " == '" + propertyvalue + "'";
		log.info("deleteFromIdentificationCode: " + filterS);
		q.setFilter(filterS);
		List<DatabaseObject> objects = (List<DatabaseObject>) q.execute();
		return objects;
	}
	/**
	 * Fetch objects of classtype with propertyname == propertyvalue for a given user
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
	public static List<DatabaseObject> getUserDatabaseObjectsFromSingleProperty(String classname, String user, 
			String propertyname, String propertyvalue) throws IOException {
		Filter userFilter = new FilterPredicate("user", FilterOperator.EQUAL, user);
		Filter keywordFilter = new FilterPredicate(propertyname, FilterOperator.EQUAL, propertyvalue);
		Filter andfilter = CompositeFilterOperator.and(keywordFilter, userFilter);
		List<DatabaseObject> set = null;
		try {
			set = getDatabaseObjectsFromFilter(classname, andfilter);
		} catch(IOException ex) {
			throw new IOException(ex.toString()
					    + " not found with : "
						+ propertyname + "=" + propertyvalue 
						+ ", User='" + user + "')");
		}
		return set;
	}
	public static List<DatabaseObject> getDatabaseObjectsFromFilter(String classname, Filter filter) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ArrayList<DatabaseObject> set = new ArrayList<DatabaseObject>();
		
		try {
			Class cls = Class.forName(classname);
			DatabaseObject example = (DatabaseObject) cls.newInstance();
		
		Query q = new Query(example.getClass().getSimpleName()).setFilter(filter);
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterable().iterator();
		if (iter.hasNext()) {
			while (iter.hasNext()) {
				Entity entity = iter.next();
				DatabaseObject info = (DatabaseObject) pm.getObjectById(example.getClass(), entity.getKey());
				set.add(info);
			}
		} else {
			throw new IOException(example.getClass().getName() + " not found with filter");
		}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return set;
		
	}
}
