package info.esblurock.reaction.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.data.rdf.CreateSetOfKeywordQueryAnswers;
import info.esblurock.reaction.data.rdf.RDFBySubjectSet;
import info.esblurock.reaction.data.rdf.RDFQueryToStringSet;
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
	public RDFBySubjectSet basicSearch(String search) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter subjectfilter =
				  new FilterPredicate("subject",FilterOperator.EQUAL,search);
		Query q = new Query("KeywordRDF").setFilter(subjectfilter);
		PreparedQuery pq = datastore.prepare(q);
		CreateSetOfKeywordQueryAnswers create = new CreateSetOfKeywordQueryAnswers(pq,true);
		RDFBySubjectSet hierarchy = create.getAnswers();
		System.out.println(hierarchy.toString());
		SetOfKeywordQueryAnswers  answers = create.getAnswers().get(search);
		
		RDFBySubjectSet set = new RDFBySubjectSet();
		set.put(search, answers);
		return set;
	}
	
	public RDFBySubjectSet objectSearch(String search) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter objectfilter =
				  new FilterPredicate("object",FilterOperator.EQUAL,search);
		Query q = new Query("KeywordRDF").setFilter(objectfilter);
		PreparedQuery pq = datastore.prepare(q);
		CreateSetOfKeywordQueryAnswers create = new CreateSetOfKeywordQueryAnswers(pq,false);
		
		return create.getAnswers();
	}
	public RDFBySubjectSet singleKeyQuery(String key) {
		RDFBySubjectSet oset = objectSearch(key);
		System.out.println("Object\n" + oset.toString());
		RDFBySubjectSet sset = basicSearch(key);
		System.out.println("Subject\n" + sset.toString());
		oset.mergeValue(sset);
		return oset;
		
	}
	public RDFBySubjectSet mergeSearch(HashSet<String> keyset) {
		RDFBySubjectSet set = new RDFBySubjectSet();
		for(String key : keyset) {
			RDFBySubjectSet subset = singleKeyQuery(key);
			System.out.println(key + "\n" + subset.toString());
			set.mergeValue(subset);
		}
		System.out.println("Final\n" + set.toString());
		return set;
	}
}
