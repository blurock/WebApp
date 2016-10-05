package info.esblurock.reaction.data.transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.google.appengine.api.datastore.Text;
import java.util.StringTokenizer;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.upload.FileUploadLines;
import info.esblurock.reaction.server.queries.QueryBase;

public class ActionsUsingIdentificationCode {
	private static final Logger log = Logger.getLogger(ActionsUsingIdentificationCode.class.getName());

	/*
	 * The maximum number of retries (in getNextLines routine) if full set
	 * cannot be retrieved
	 */
	static int maxNumberOfRetries = 100;

	/*
	 * The pause in milliseconds (in getNextLines routine) if full set cannot be
	 * retrieved and the next try
	 */
	static int milliSecondPause = 200;

	/**
	 * Fetches objects of classname with propertyname == propertyvalue
	 * 
	 * @param classtype
	 *            The class of the items to fetch.
	 * @param propertyname:
	 *            The property name to search for
	 * @param propertyvalue:
	 *            The value of the property
	 * @return
	 * @throws IOException 
	 */
	static public List<DatabaseObject> getObjectFromIdentificationCode(Class classtype, String propertyname,
			String propertyvalue) throws IOException {
		List<DatabaseObject> objects = QueryBase.getDatabaseObjectsFromSingleProperty(classtype.getName(), propertyname,
				propertyvalue);
		return objects;
	}

	static public void deleteFromIdentificationCode(Class classtype, String idcode) {
		deleteFromIdentificationCode(classtype, "sourceCode", idcode);
	}

	/**
	 * Deletes objects of classname with propertyname == propertyvalue
	 * 
	 * @param classtype
	 *            The class of the items to be deleted.
	 * @param propertyname:
	 *            The property name to search for
	 * @param propertyvalue:
	 *            The value of the property
	 */
	static public void deleteFromIdentificationCode(Class classtype, String propertyname, String propertyvalue) {
		QueryBase query = new QueryBase();
		query.deleteFromIdentificationCode(classtype, propertyname, propertyvalue);
	}

	/**
	 * Delete a set of line of set determined by fileCode
	 * 
	 * @param fileCode
	 *            The set of lines to delete (within {@link FileUploadLines}))
	 * @param start:
	 *            Lines with count (within {@link FileUploadLines}) starting
	 *            with start+1 are deleted.
	 * @param num:
	 *            Delete at num lines within the set
	 * @return true if successful
	 * 
	 *         Searches for num lines from set with fileCode (within
	 *         {@link FileUploadLines}) beginning at (start+1) (count field
	 *         within {@link FileUploadLines}). The keys are retrieved and then
	 *         the set is deleted.
	 */
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
	 * The lines to be retrieved have counts (in {@link FileUploadLines}):
	 * (totalcount+1) <= count < (totalcount + maxPart + 1)
	 *
	 * @param totalcount
	 *            The index for beginning of the line numbers to retrieve. The
	 *            first line number to retrieve is totalcount+1.
	 * @param maxPart
	 *            The maximum number of lines to retrieve. The number of lines
	 *            left can be less.
	 * @param fileCode
	 *            This is the fileCode within the {@link FileUploadLines}
	 *            specifying which set of lines should be retrieved.
	 * @return the next list of lines in the proper order.
	 * 
	 *         This routine is the main routine which calls getNextLinesPart to
	 *         actually fetch the lines. It can happen that not all the lines
	 *         have been written to the database (async problem). This routine
	 *         repeatedly (after a delay of milliSecondPause milliseconds) calls
	 *         getNextLinesPart until a full set of lines are retrieved (if
	 *         lines are missing, getNextLinesPart throws an exception). If the
	 *         number of tries exceeds maxNumberOfRetries, then the return is
	 *         the list of no elements.
	 * 
	 */
	static public ArrayList<String> getNextLines(int totalcount, int maxPart, String fileCode) {
		int retrycount = 0;
		ArrayList<String> set = null;
		boolean notcomplete = true;
		while (notcomplete) {
			try {
				set = getNextLinesPart(totalcount, fileCode);
				notcomplete = false;
			} catch (IOException ex) {
				try {
					retrycount++;
					if (retrycount < maxNumberOfRetries) {
						String msg = "Incomplete read, waiting " + milliSecondPause + " milliseconds and trying again";
						log.info(msg);
						Thread.sleep(milliSecondPause);
					} else {
						log.log(Level.SEVERE, "Reading lines interrupted too many retries");
					}

				} catch (InterruptedException e) {
					log.log(Level.SEVERE, "Reading lines interrupted");
					notcomplete = false;
				}
			}
		}
		return set;
	}

	/**
	 * Retrieve the next set of lines starting with totalcount+1
	 * 
	 * @param totalcount
	 *            The index for beginning of the line numbers to retrieve. The
	 *            first line number to retrieve is totalcount+1.
	 * @param maxPart
	 *            The maximum number of lines to retrieve. The number of lines
	 *            left can be less.
	 * @param fileCode
	 *            This is the fileCode within the {@link FileUploadLines}
	 *            specifying which set of lines should be retrieved.
	 * @return the next list of lines in the proper order.
	 * 
	 * @throws {@link
	 *             IOException} means that not all in the set have been
	 *             retrieved. A restart is needed to try again.
	static private ArrayList<String> getNextLinesPart(int totalcount, int maxPart, String fileCode) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ArrayList<String> lines = new ArrayList<String>();
		Filter fileCodeF = new FilterPredicate("fileCode", FilterOperator.EQUAL, fileCode);
		Filter lower = new FilterPredicate("count", FilterOperator.GREATER_THAN_OR_EQUAL, totalcount + 1);
		Filter upper = new FilterPredicate("count", FilterOperator.LESS_THAN, totalcount + maxPart + 1);
		Filter totalfilter = CompositeFilterOperator.and(fileCodeF, lower, upper);
		Query q = new Query("FileUploadLines").setFilter(totalfilter).addSort("count", SortDirection.ASCENDING);
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterator();
		int count = 0;
		while (iter.hasNext()) {
			Entity next = iter.next();
			Long num = (Long) next.getProperty("count");
			int current = totalcount + 1 + count;
			if (current == num.intValue()) {
				String line = (String) next.getProperty("line");
				lines.add(line);
				count++;
			} else {
				log.info("Mismatch: " + num.intValue() + "expected: " + current);
				throw new IOException("missing line");
			}
		}
		return lines;
	}
	 */
	static private ArrayList<String> getNextLinesPart(int totalcount, String fileCode) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		ArrayList<String> lines = new ArrayList<String>();
		Filter fileCodeF = new FilterPredicate("fileCode", FilterOperator.EQUAL, fileCode);
		Filter lower = new FilterPredicate("beginLineCount", FilterOperator.EQUAL, totalcount);
		Filter totalfilter = CompositeFilterOperator.and(fileCodeF, lower);
		Query q = new Query("FileUploadTextBlock").setFilter(totalfilter);
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterator();
		int count = 0;
		while (iter.hasNext()) {
			Entity entity = iter.next();
			Text block = (Text) entity.getProperty("textBlock");
			StringTokenizer tok = new StringTokenizer(block.getValue(), "\n");
			while(tok.hasMoreTokens()) {
				String line = tok.nextToken();
				lines.add(line);
			}
		}
		return lines;
	}

}
