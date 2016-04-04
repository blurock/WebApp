package info.esblurock.reaction.server.chemkin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortPredicate;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Key;

import info.esblurock.react.mechanisms.chemkin.ChemkinString;
import info.esblurock.reaction.data.upload.FileUploadLines;
import info.esblurock.reaction.data.upload.UploadFilePartTransaction;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.server.datastore.PMF;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;

public class ChemkinStringFromStoredFile extends ChemkinString {
	private static final long serialVersionUID = 1L;
	PersistenceManager pm = PMF.get().getPersistenceManager();
	DatastoreService datastore;
	ArrayList<String> lines;
	ArrayList<String> parts;
	Iterator<String> partiterator;
	private int count = 0;
	private int totalcount = 0;

	public ChemkinStringFromStoredFile(String key, String user, String commentString) {
		super(key, commentString);
		count = 0;
		datastore = DatastoreServiceFactory.getDatastoreService();
		UploadFileTransaction transaction = pm.getObjectById(UploadFileTransaction.class, key);
		if (transaction != null) {
			parts = transaction.getSetOfLinesKeys();
			partiterator = parts.iterator();
			setUpNextPart();
		} else {
			System.out.println("UploadFileTransaction not found with key: " + key);
		}
	}

	private boolean setUpNextPart() {
		boolean nextpart = true;
		lines = new ArrayList<String>();
		count = 0;
		if (partiterator.hasNext()) {
			String key = partiterator.next();
			UploadFilePartTransaction filepart = pm.getObjectById(UploadFilePartTransaction.class, key);

			Filter fileCodeF = new FilterPredicate("fileCode", FilterOperator.EQUAL, filepart.getFilecode());
			Filter lower = new FilterPredicate("count", FilterOperator.GREATER_THAN_OR_EQUAL, totalcount+1);
			Filter upper = new FilterPredicate("count", FilterOperator.LESS_THAN,
					totalcount + filepart.getSetOfLinesKeys().size());
			Filter totalfilter = CompositeFilterOperator.and(fileCodeF, lower, upper);
			Query q = new Query("FileUploadLines").setFilter(totalfilter).addSort("count", SortDirection.ASCENDING);
			PreparedQuery pq = datastore.prepare(q);
			Iterator<Entity> iter = pq.asIterator();
			while (iter.hasNext()) {
				Entity next = iter.next();
				String line = (String) next.getProperty("line");
				lines.add(line);
			}
		}
		if (lines.size() == 0)
			nextpart = false;
		return nextpart;
	}

	public String getCurrent() {
		String line = null;
		if (count < lines.size()) {
			line = lines.get(count);
		} else {
			if (setUpNextPart()) {
				line = lines.get(count);
			}
		}
		return line;
	}

	public String nextToken() {
		String line = null;
		count++;
		totalcount++;
		if (count < lines.size()) {
			line = lines.get(count);
		} else {
			if (setUpNextPart()) {
				line = lines.get(count);
			}
		}
		return line;
	}

}
