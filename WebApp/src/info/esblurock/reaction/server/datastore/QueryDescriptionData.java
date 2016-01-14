package info.esblurock.reaction.server.datastore;

import info.esblurock.reaction.data.description.DescriptionData;
import info.esblurock.reaction.server.datastore.contact.ContactInfo;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class QueryDescriptionData extends DescriptionData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public QueryDescriptionData(String name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		query(name, datastore);
	}

	/**
	 * 
	 */
	public QueryDescriptionData(String name, DatastoreService datastore) {
		query(name, datastore);
	}

	/**
	 * 
	 */
	public void query(String name, DatastoreService datastore) {
		QuerySetsOfPropertyAttributes query = new QuerySetsOfPropertyAttributes(
				datastore, name, ContactInfo.contact);
		this.fillAttributes(query);
	}

}
