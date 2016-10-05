package info.esblurock.reaction.server.queries;

import java.util.Iterator;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;

import info.esblurock.reaction.data.DatabaseObject;

public class DatastoreObjectLowlevelQuery implements Iterable<DatabaseObject>, Iterator<DatabaseObject> {
	private final DatastoreService datastore;
	private final Query query;
	private Cursor nextCursor;
	private Iterator<Entity> currentIterator;

	public DatastoreObjectLowlevelQuery(String classname) {
		datastore = DatastoreServiceFactory.getDatastoreService();
		query = new Query(classname);
	}

	public DatastoreObjectLowlevelQuery addFilter(String propertyName, FilterOperator operator, Object value) {
		query.addFilter(propertyName, operator, value);
		return this;
	}

	public DatastoreObjectLowlevelQuery addSort(String propertyName, SortDirection direction) {
		query.addSort(propertyName, direction);
		return this;
	}

	private void fetchResult() {
		FetchOptions options = FetchOptions.Builder.withLimit(200);
		if (nextCursor != null) {
			options.startCursor(nextCursor);
		}
		// To avoid datastore timeouts while iterating through subscriptions,
		// We make sure that we fetch entities in chunks, where each chunk is
		// a standalone query. This way we can spend as much time as we want to
		// in each iteration.
		QueryResultList<Entity> currentBatch = datastore.prepare(query).asQueryResultList(options);
		if (currentBatch != null) {
			nextCursor = currentBatch.getCursor();
		}
		currentIterator = currentBatch.iterator();
	}

	@Override
	public Iterator<DatabaseObject> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		if (currentIterator == null || !currentIterator.hasNext()) {
			fetchResult();
			onNewBatch(currentIterator);
		}
		return currentIterator.hasNext();
	}

	/**
	 * Method called between every chunked query made. Default implementation
	 * does nothing.
	 * 
	 * @param currentIterator
	 */
	protected void onNewBatch(Iterator<Entity> currentIterator) {
		// Default impl: do nothing
	}

	@Override
	public DatabaseObject next() {
		// TODO Auto-generated method stub
		return null;
	}
}
