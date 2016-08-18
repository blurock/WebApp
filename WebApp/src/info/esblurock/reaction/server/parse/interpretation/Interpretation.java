package info.esblurock.reaction.server.parse.interpretation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.parse.objects.ParseObject;
import info.esblurock.reaction.server.parse.interpretation.set.RDFKeywordSetMap;

public abstract class Interpretation {
	protected String keywordRDF = "KeywordRDF";

	abstract public boolean interpretable(String input);
	abstract public HashSet<KeywordRDF> getResults(String input);
	
	protected HashSet<KeywordRDF>  getSet(ParseObject parse, String inputS) {
		HashSet<KeywordRDF> set = new HashSet<KeywordRDF>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(keywordRDF).setFilter(parse.getFilter(inputS));
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterable().iterator();
		while (iter.hasNext()) {
				Entity entity = iter.next();
				KeywordRDF info = (KeywordRDF) pm.getObjectById(KeywordRDF.class, entity.getKey());
				set.add(info);
			}
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
}
