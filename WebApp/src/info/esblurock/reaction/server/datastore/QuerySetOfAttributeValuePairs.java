package info.esblurock.reaction.server.datastore;

import java.util.Iterator;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Entity;

public class QuerySetOfAttributeValuePairs extends SetOfAttributeValuePairs {
	private static final long serialVersionUID = 1L;

	public QuerySetOfAttributeValuePairs(String name, String key) {
		super(name);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(RDFDataKey.RDF);
		FilterPredicate filter = new FilterPredicate(RDFDataKey.subjectKey, FilterOperator.EQUAL, key);
		q.setFilter(filter);
		PreparedQuery pq = datastore.prepare(q);
		
		Iterator<Entity> iter = pq.asIterator();
		while(iter.hasNext()) {
			Entity entity = iter.next();
			String object = (String) entity.getProperty(RDFDataKey.objectKey);
			String predicate = (String) entity.getProperty(RDFDataKey.predicateKey);
			
			AttributeValuePair pair = new AttributeValuePair(predicate, object);
			this.add(pair);
		}
		
	}
}
