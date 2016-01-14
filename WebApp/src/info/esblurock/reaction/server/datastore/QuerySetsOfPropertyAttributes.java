package info.esblurock.reaction.server.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class QuerySetsOfPropertyAttributes extends SetsOfPropertyAttributes{
	DatastoreService datastore;
	
	
	public QuerySetsOfPropertyAttributes(String name, String type) {
		super(type);
		datastore = DatastoreServiceFactory.getDatastoreService();
		fillInData(name,type);
	}
	public QuerySetsOfPropertyAttributes(DatastoreService datastore, String name, String type) {
		super(type);
		this.datastore = datastore;
		fillInData(name,type);
	}
	
	private void fillInData(String name, String type) {
		Query q = new Query(RDFDataKey.RDF);
		FilterPredicate filterSub = new FilterPredicate(RDFDataKey.subjectKey, FilterOperator.EQUAL, name);
		FilterPredicate filterPred = new FilterPredicate(RDFDataKey.predicateKey, FilterOperator.EQUAL, type);
		Filter RDFFilter = CompositeFilterOperator.and(filterSub, filterPred);

		q.setFilter(RDFFilter);
		PreparedQuery pq = datastore.prepare(q);
		
		Iterator<Entity> iter = pq.asIterator();
		while(iter.hasNext()) {
			Entity entity = iter.next();
			String object = (String) entity.getProperty(RDFDataKey.objectKey);
			
			String key = name + "." + type + "." + object;
			QuerySetOfAttributeValuePairs pairs = new QuerySetOfAttributeValuePairs(object,key);
			this.put(key, pairs);
		}
		

	}
}
