package info.esblurock.reaction.server.datastore;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class WriteSimpleMessage {

	public Key write(String text) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity message = new Entity("message");
		message.setProperty("text", text);
		Key keyword = datastore.put(message);
		return keyword;
	}
}
