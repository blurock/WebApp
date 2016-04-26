package info.esblurock.reaction.server.queries;

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
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import info.esblurock.react.data.DatabaseObject;
import info.esblurock.reaction.server.datastore.PMF;

public class QueryBase {

	private static final Logger log = Logger.getLogger(QueryBase.class.getName());

	public QueryBase() {
		
	}

	public void deleteFromIdentificationCode(Class classtype, String codename, String idcode) {
		log.info("UsingIdentificationCode   deleteFromIdentificationCode: " + classtype.getName()+ codename + ", " + idcode);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		javax.jdo.Query q = pm.newQuery(classtype);
		String filterS = codename + " == '" + idcode + "'";
		log.info("deleteFromIdentificationCode: " + filterS);
		q.setFilter(filterS);
		long ans = q.deletePersistentAll();
		log.info("deleteFromIdentificationCode: " + ans);
	}

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
	public List<DatabaseObject> getDatabaseObjectsFromSingleProperty(Class classtype, String codename, String idcode) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		javax.jdo.Query q = pm.newQuery(classtype);
		String filterS = codename + " == '" + idcode + "'";
		log.info("deleteFromIdentificationCode: " + filterS);
		q.setFilter(filterS);
		List<DatabaseObject> objects = (List<DatabaseObject>) q.execute();
		return objects;
	}
}
