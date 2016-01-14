package info.esblurock.reaction.server;

import java.util.Iterator;

import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.data.rdf.CreateSetOfKeywordQueryAnswers;
import info.esblurock.reaction.data.rdf.SetOfKeywordQueryAnswers;
import info.esblurock.reaction.server.datastore.PMF;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class ReactionSearchServiceImpl  extends ServerBase implements ReactionSearchService {
	private static final long serialVersionUID = 1L;
	PersistenceManager pm = PMF.get().getPersistenceManager();
	//private String simpleQuery = "BasicSearch#";
	//private String queryType = "Query";

	@Override
	public SetOfKeywordQueryAnswers basicSearch(String search) {
		//String task = simpleQuery + search;
		//verify(task,queryType);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter subjectfilter =
				  new FilterPredicate("subject",FilterOperator.EQUAL,search);
		Query q = new Query("KeywordRDF").setFilter(subjectfilter);
		PreparedQuery pq = datastore.prepare(q);
		CreateSetOfKeywordQueryAnswers answers = new CreateSetOfKeywordQueryAnswers(pq);
		return answers.getAnswers();
	}


}
