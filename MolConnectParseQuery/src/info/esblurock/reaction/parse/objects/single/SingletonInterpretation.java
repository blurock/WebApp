package info.esblurock.reaction.parse.objects.single;

import java.util.ArrayList;
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
import info.esblurock.reaction.parse.Interpretation;
import info.esblurock.reaction.parse.objects.ParseObject;

public class SingletonInterpretation extends Interpretation {
	protected ParseObject filter;

	public SingletonInterpretation(ParseObject filter) {
		this.filter = filter;
	}
	@Override
	public boolean interpretable(String input) {
		StringTokenizer tok = new StringTokenizer(input," ");
		return tok.countTokens() == 1;
	}

	@Override
	public ArrayList<KeywordRDF> getResults(String input) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ArrayList<KeywordRDF> output = new ArrayList<KeywordRDF>();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(keywordRDF).setFilter(filter.getFilter(input));
		PreparedQuery pq = datastore.prepare(q);
		Iterator<Entity> iter = pq.asIterable().iterator();
		while (iter.hasNext()) {
				Entity entity = iter.next();
				KeywordRDF info = (KeywordRDF) pm.getObjectById(KeywordRDF.class, entity.getKey());
				output.add(info);
			}

		return output;
	}
	public String toString() {
		return "Interpret: " + filter.toString();
	}
	
}
