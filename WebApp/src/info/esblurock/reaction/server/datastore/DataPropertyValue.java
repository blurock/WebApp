package info.esblurock.reaction.server.datastore;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;

public class DataPropertyValue {
	Entity entity;
	public DataPropertyValue(String type) {
		entity = new Entity(type);
	}

	public void addProperty(String key, String value) {
		entity.setProperty(key, value);
	}
	public void addProperty(String key, Date date) {
		entity.setProperty(key, date);
	}
	public Entity getEntity() {
		return entity;
	}
}