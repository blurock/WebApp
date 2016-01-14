package info.esblurock.reaction.server.datastore.contact;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import info.esblurock.reaction.server.datastore.QueryDescriptionData;


public class QueryOrganizationDescription extends OrganizationDescription {

	public QueryOrganizationDescription(String name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		contact = new QueryContactInfo(name,datastore);
		location = new QueryContactLocation(name,datastore);
		description = new QueryDescriptionData(name,datastore);
		
	}
	public QueryOrganizationDescription(String name, DatastoreService datastore) {
		contact = new QueryContactInfo(name,datastore);
		location = new QueryContactLocation(name,datastore);
		description = new QueryDescriptionData(name,datastore);
	}
}
