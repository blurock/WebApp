package info.esblurock.reaction.server.parse.interpretation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;

import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.parse.objects.ParseObject;

/** An interpretation represents one query given a filter
 * This class uses the Filter generated from the {@link ParseObject} class,
 * given the {@link QueryParameters} class to generate a set of 
 * RDF's {@link RDFQueryResultSet}.
 * 
 * Classes derived from this class should implement:
 * <ul> {@link Interpretation#interpretable} : Given the input, determines whether
 * this Interpretation should be used. 
 * <li> {@link Interpretation#getResults}: The routine which gets the results from the 
 * {@link QueryParameters}
 * 
 * @author edwardblurock
 *
 */
public abstract class Interpretation {
	protected String keywordRDF = "KeywordRDF";

	abstract public boolean interpretable(QueryParameters input);
	abstract public HashSet<KeywordRDF> getResults(QueryParameters input);
	
	boolean overflow;
	int entityCount;
	int entityLimit;
	
	public Interpretation() {
		entityLimit = 100;
	}
	/** This performs one query and
	 * 
	 * @param parse: Setting up the Filter to be used in the query
	 * @param input The query parameters to be used by the query
	 * @return The set of {@link KeywordRDF} classes satisfying the query
	 */
	protected RDFQueryResultSet getSet(ParseObject parse, QueryParameters input) {
		String inputS = input.getInputString();
		entityLimit = input.getEntityLimit();
		RDFQueryResultSet set = new RDFQueryResultSet();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(keywordRDF).setFilter(parse.getFilter(inputS)).setKeysOnly();
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterator(FetchOptions.Builder.withLimit(entityLimit));
		while (iter.hasNext()) {
				Entity entity = iter.next();
				KeywordRDF info = (KeywordRDF) pm.getObjectById(KeywordRDF.class, entity.getKey());
				set.add(info);
			}
		overflow = false;
		if(set.size() == entityLimit)
			overflow = true;
		return set;		
	}	
	
	
	/** 
	 * 
	 * @param input
	 * @return
	 */
	protected ArrayList<String> parseInputTokens(String input) {
		ArrayList<String> set = new ArrayList<String>();
		StringTokenizer tok = new StringTokenizer(input," ");
		while(tok.hasMoreTokens()) {
			set.add(tok.nextToken());
		}
		return set;
	}

	
	public String toString() {
		return "empty interpretation";
	}
	public boolean isOverflow() {
		return overflow;
	}
	
}
