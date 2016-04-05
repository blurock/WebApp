package info.esblurock.reaction.server;

import java.io.IOException;
import java.util.Iterator;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.user.UserAccount;
import info.esblurock.reaction.server.datastore.PMF;

public class HandleTransactions {
	static public void exception(String key, Exception ex, TransactionInfo transaction) throws IOException {
		String message = "Exception: ";
		if (transaction != null) {
			if (transaction.getKey() == null) {
				String newKey = "ERROR" + key;
				message += "Saving Unfinished Transaction: (Key='" + newKey + "') due to exception:\n " + ex.toString();
				System.out.println(message);
				transaction.setKeyword(newKey);
				PersistenceManager pm = PMF.get().getPersistenceManager();
				pm.makePersistent(transaction);
			} else {
				message += ex.toString();
			}
		} else {
			message += ex.toString();
		}
		if (ex instanceof IOException) {
			IOException ioex = (IOException) ex;
			throw ioex;
		} else {
			throw new IOException(message);
		}

	}

	static public void transactionExists(String key, String classname) throws IOException {
		boolean exists = false;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter keywordfilter = new FilterPredicate("keyword", FilterOperator.EQUAL, key);
		Filter classfilter = new FilterPredicate("transactionObjectType", FilterOperator.EQUAL, classname);
		Filter andfilter = CompositeFilterOperator.and(classfilter, keywordfilter);
		Query q = new Query("TransactionInfo").setFilter(andfilter);
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterable().iterator();
		exists = iter.hasNext();
		if (exists) {
			throw new IOException("Transaction with key='" + key + " and class='" + classname
					+ "' exists..\n delete before processing");
		}
	}
}
