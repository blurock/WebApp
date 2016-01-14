package info.esblurock.reaction.server.datastore.contact;

import info.esblurock.reaction.server.datastore.QuerySetsOfPropertyAttributes;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class QueryContactInfo extends ContactInfo {
	private static final long serialVersionUID = 1L;
	public QueryContactInfo(String name) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		query(name,datastore);
	}
	public QueryContactInfo(String name, DatastoreService datastore) {
		query(name,datastore);
	}
	public void query(String name, DatastoreService datastore) {
		QuerySetsOfPropertyAttributes query = new QuerySetsOfPropertyAttributes(datastore, name, ContactInfo.contact);
		this.fillAttributes(query);
	}
}
