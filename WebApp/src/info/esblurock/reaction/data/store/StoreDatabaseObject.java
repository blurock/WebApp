package info.esblurock.reaction.data.store;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.DatastoreTimeoutException;

import info.esblurock.reaction.data.CreateData;
import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.PMF;

public class StoreDatabaseObject {
	private static Logger log = Logger.getLogger(CreateData.class.getName());
	final static public int maxStored = 3000;
	ArrayList<DatabaseObject> toBeStored;

	public StoreDatabaseObject() {
		toBeStored = new ArrayList<DatabaseObject>();
	}

	/**
	 * Store if the object has not been stored yet (checking if key is null).
	 *
	 * @param object
	 *            the object
	 */
	public void store(DatabaseObject object) {
		String key = object.getKey();
		if (key == null) {
			toBeStored.add(object);
			if (toBeStored.size() > maxStored) {
				flushStore();
			}
		}
	}

	public void flushStore() {
		log.info("StoreObject: flushStore(): " + toBeStored.size());
		int timeout_ms = 100;
		if (toBeStored.size() > 0) {
			while (true) {
				try {
					PersistenceManager pm = PMF.get().getPersistenceManager();
					pm.makePersistentAll(toBeStored);
					pm.close();
					break;
				} catch (DatastoreTimeoutException e) {
					try {
						Thread.currentThread().sleep(timeout_ms);
						timeout_ms *= 2;
						log.log(Level.WARNING,"flushStore(): DatastoreTimeoutException increase wait to: " + timeout_ms);
						if(timeout_ms > 10000) {
							log.log(Level.SEVERE,"flushStore(): DatastoreTimeoutException waiting does not seem to help ");
						}
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
			toBeStored = new ArrayList<DatabaseObject>();
		}
	}
	public ArrayList<DatabaseObject> getToBeStored() {
		return toBeStored;
	}

}
