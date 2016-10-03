package info.esblurock.reaction.server.parse.interpretation;
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

public class SingletonInterpretation extends Interpretation {
	protected ParseObject filter;

	public SingletonInterpretation(ParseObject filter) {
		this.filter = filter;
	}
	@Override
	public boolean interpretable(QueryParameters input) {
		StringTokenizer tok = new StringTokenizer(input.getInputString()," ");
		return tok.countTokens() == 1;
	}

	@Override
	public HashSet<KeywordRDF> getResults(QueryParameters input) {
		HashSet<KeywordRDF>  set = getSet(filter,input);
		return set;
	}

	public String toString() {
		return "Interpret: " + filter.toString();
	}
	
}
