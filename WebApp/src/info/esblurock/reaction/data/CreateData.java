package info.esblurock.reaction.data;

import java.util.ArrayList;

import info.esblurock.reaction.client.data.DatabaseObject;

/**
 * The Class CreateData.
 */
public class CreateData {
	
	/** The max size of store. */
	final static public int maxSizeOfStore = 200;

	/** The store objects. */
	protected ArrayList<StoreObject> storeObjects;

	/**
	 * Instantiates a new creates the data.
	 */
	public CreateData() {
		storeObjects = new ArrayList<StoreObject>();
	}

	/**
	 * Adds the store.
	 *
	 * @param store the store
	 */
	public void addStore(StoreObject store) {
		storeObjects.add(store);
		if (storeObjects.size() > maxSizeOfStore) {
			flushCreate();
		}
	}

	
	/**
	 * Flush.
	 * An essential assumption is that all store objects have the same transaction object.
	 */
	public void flushCreate() {
		StoreObject start = null;
		for(StoreObject store : storeObjects) {
			if(start == null) {
				start = store;
			} else {
				for(DatabaseObject o: store.getToBeStored()) {
					start.store(o);
				}
			}
		}
		System.out.println("CreateData flush: ");
		start.flushStore();
		storeObjects = new ArrayList<StoreObject>();
	}

	/**
	 * Merge.
	 *
	 * @param create the create
	 */
	public void merge(CreateData create) {
		for (StoreObject store : create.getStoreObjects()) {
			storeObjects.add(store);
		}
	}

	/**
	 * Gets the store objects.
	 *
	 * @return the store objects
	 */
	ArrayList<StoreObject> getStoreObjects() {
		return storeObjects;
	}
}
