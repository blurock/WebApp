package info.esblurock.reaction.data.transaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.google.appengine.api.datastore.Query.SortDirection;

import info.esblurock.react.data.DatabaseObject;
import info.esblurock.reaction.server.datastore.PMF;

public class ActionsUsingIdentificationCode {

	static public List<DatabaseObject> getObjectFromIdentificationCode(String idcode, String classname,
			String codename) {
		List<DatabaseObject> objects = null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Filter fileCodeF = new FilterPredicate(codename, FilterOperator.EQUAL, idcode);

		Query q = new Query(classname).setFilter(fileCodeF).setKeysOnly();
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterator();
		if (iter.hasNext()) {
			ArrayList<Key> lst = new ArrayList<Key>();
			while (iter.hasNext()) {
				Entity next = iter.next();
				lst.add(next.getKey());
			}
			PersistenceManager pm = PMF.get().getPersistenceManager();
			objects = (List<DatabaseObject>) pm.getObjectsById(lst);
			pm.close();
		}
		return objects;
	}

	static public void deleteFromIdentificationCode(String idcode, String classname) {
		deleteFromIdentificationCode(idcode, classname, "sourceCode");
	}

	static public void deleteFromIdentificationCode(String idcode, String classname, String codename) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Filter fileCodeF = new FilterPredicate(codename, FilterOperator.EQUAL, idcode);

		Query q = new Query(classname).setFilter(fileCodeF).setKeysOnly();
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterator();
		if (iter.hasNext()) {
			ArrayList<Key> lst = new ArrayList<Key>();
			while (iter.hasNext()) {
				Entity next = iter.next();
				lst.add(next.getKey());
			}
			datastore.delete(lst);
		}
	}

	static public boolean deleteFileUploadLines(String fileCode, int start, int num) {
		boolean ans = true;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Filter fileCodeF = new FilterPredicate("fileCode", FilterOperator.EQUAL, fileCode);
		Filter lower = new FilterPredicate("count", FilterOperator.GREATER_THAN_OR_EQUAL, start + 1);
		Filter upper = new FilterPredicate("count", FilterOperator.LESS_THAN_OR_EQUAL, start + num);
		Filter totalfilter = CompositeFilterOperator.and(fileCodeF, lower, upper);

		Query q = new Query("FileUploadLines").setFilter(totalfilter).addSort("count", SortDirection.ASCENDING)
				.setKeysOnly();
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterator();
		if (iter.hasNext()) {
			ArrayList<Key> lst = new ArrayList<Key>();
			while (iter.hasNext()) {
				Entity next = iter.next();
				lst.add(next.getKey());
			}
			datastore.delete(lst);
		} else {
			ans = false;
		}
		return ans;
	}

	/**
	 * Gets the next linest.
	 *
	 * @param totalcount the totalcount
	 * @param maxPart the max part
	 * @param fileCode the file code
	 * @return the next linest
	 */
	static public ArrayList<String> getNextLinest(int totalcount, int maxPart, String fileCode) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ArrayList<String> lines = new ArrayList<String>();
		Filter fileCodeF = new FilterPredicate("fileCode", FilterOperator.EQUAL, fileCode);
		Filter lower = new FilterPredicate("count", FilterOperator.GREATER_THAN_OR_EQUAL, totalcount + 1);
		Filter upper = new FilterPredicate("count", FilterOperator.LESS_THAN, totalcount + maxPart + 1);
		Filter totalfilter = CompositeFilterOperator.and(fileCodeF, lower, upper);
		Query q = new Query("FileUploadLines").setFilter(totalfilter).addSort("count", SortDirection.ASCENDING);
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterator();
		while (iter.hasNext()) {
			Entity next = iter.next();
			String line = (String) next.getProperty("line");
			lines.add(line);
		}
		return lines;
	}

}
