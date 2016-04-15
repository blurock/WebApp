package info.esblurock.reaction.data;

import java.util.ArrayList;

import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.server.datastore.PMF;

/**
 * The Class CreateData.
 */
public class CreateData {

	private static Logger log = Logger.getLogger(CreateData.class.getName());
	/** The max size of store. */
	final static public int maxSizeOfStore = 4000;

	/** The store objects. */
	protected ArrayList<DatabaseObject> storeObjects;

	int numberOfStoredObjects;

	/**
	 * Instantiates a new creates the data.
	 */
	public CreateData() {
		storeObjects = new ArrayList<DatabaseObject>();
		numberOfStoredObjects = 0;
	}

	/**
	 * Adds the store.
	 *
	 * @param store
	 *            the store
	 */
	public void addStore(StoreObject store) {
		for(DatabaseObject o : store.getToBeStored()) {
			addDatabaseObject(o);
		}
	}

	
	private void addDatabaseObject(DatabaseObject o) {
		if(o.getKey() == null) {
			storeObjects.add(o);
		}
		if (storeObjects.size() > StoreObject.maxStored) {
			log.info("Number of stored objects (" + storeObjects.size() + ") exceeds max: " + StoreObject.maxStored);
			flushCreate();
		}		
	}
	/**
	 * Flush. An essential assumption is that all store objects have the same
	 * transaction object.
	 */
	public void flushCreate() {
		if (storeObjects.size() > 0) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistentAll(storeObjects);
		pm.close();
		storeObjects = new ArrayList<DatabaseObject>();
		}
	}

	/**
	 * Merge.
	 *
	 * @param create
	 *            the create
	 */
	public void merge(CreateData create) {
		for (DatabaseObject o : create.getStoreObjects()) {
			addDatabaseObject(o);
		}
	}

	/**
	 * Gets the store objects.
	 *
	 * @return the store objects
	 */
	ArrayList<DatabaseObject> getStoreObjects() {
		return storeObjects;
	}
}
