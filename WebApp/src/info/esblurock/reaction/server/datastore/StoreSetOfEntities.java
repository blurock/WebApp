package info.esblurock.reaction.server.datastore;

import java.util.ArrayList;
import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;


public class StoreSetOfEntities {
	private ArrayList<DataPropertyValue> entities;
	private ArrayList<Key> setOfKeys;
	private DatastoreService datastore;
	private String owner;

	public StoreSetOfEntities() {
		owner = null;
		initialize();
	}

	public StoreSetOfEntities(String owner) {
		this.owner = owner;
		initialize();
	}

	private void initialize() {
		datastore = DatastoreServiceFactory.getDatastoreService();
		entities = new ArrayList<DataPropertyValue>();
		setOfKeys = new ArrayList<Key>();
	}

	public void addEntity(DataPropertyValue entity) {
		entities.add(entity);
	}

	public Key store() {
		Date date = new Date();
		ArrayList<Key> keys = new ArrayList<Key>();
		for (DataPropertyValue entity : entities) {
			entity.addProperty("owner", owner);
			entity.addProperty("time", date);
			Key key = datastore.put(entity.getEntity());
			keys.add(key);
		}
		Entity trans = new Entity("transaction");
		trans.setUnindexedProperty("keys", keys);
		trans.setProperty("owner", owner);
		trans.setProperty("time", date);
		return datastore.put(trans);
	}
}
