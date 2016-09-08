package info.esblurock.reaction.server;

import java.io.IOException;
import java.util.HashSet;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.rdf.CreateSetOfKeywordQueryAnswers;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.rdf.RDFBySubjectSet;
import info.esblurock.reaction.data.rdf.SetOfKeywordQueryAnswers;
import info.esblurock.reaction.data.rdf.SetOfKeywordRDF;
import info.esblurock.reaction.data.rdf.graph.RDFSubTreeParentNode;
import info.esblurock.reaction.data.rdf.graph.RDFTreeNode;
import info.esblurock.reaction.data.rdf.graph.TreeNodeFactory;
import info.esblurock.reaction.server.authorization.TaskTypes;
import info.esblurock.reaction.server.event.RegisterTransaction;
import info.esblurock.reaction.server.parse.interpretation.Interpretation;
import info.esblurock.reaction.server.parse.interpretation.SetOfInterpretations;
import info.esblurock.reaction.server.parse.query.ParseQuery;
import info.esblurock.reaction.server.parse.query.SetOfParseQueries;
import info.esblurock.reaction.server.parse.register.RegisteredQueries;
import info.esblurock.reaction.server.utilities.ContextAndSessionUtilities;

import javax.jdo.FetchGroup;
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

	@Override
	public RDFBySubjectSet basicSearch(String search) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter subjectfilter =
				  new FilterPredicate("subject",FilterOperator.EQUAL,search);
		Query q = new Query("KeywordRDF").setFilter(subjectfilter);
		PreparedQuery pq = datastore.prepare(q);
		CreateSetOfKeywordQueryAnswers create = new CreateSetOfKeywordQueryAnswers(pq,true);
		pm.close();
		System.out.println("RDFBySubjectSet basicSearch: 1");
		RDFBySubjectSet hierarchy = create.getAnswers();
		System.out.println(hierarchy.toString());
		System.out.println("RDFBySubjectSet basicSearch: 2");
		SetOfKeywordQueryAnswers  answers = create.getAnswers().get(search);
		System.out.println("RDFBySubjectSet basicSearch: 3");
		
		RDFBySubjectSet set = new RDFBySubjectSet();
		System.out.println("RDFBySubjectSet basicSearch: 4");
		set.put(search, answers);
		System.out.println("RDFBySubjectSet basicSearch: 5");
		return set;
	}
	
	public RDFBySubjectSet objectSearch(String search) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter objectfilter =
				  new FilterPredicate("object",FilterOperator.EQUAL,search);
		Query q = new Query("KeywordRDF").setFilter(objectfilter);
		PreparedQuery pq = datastore.prepare(q);
		System.out.println("RDFBySubjectSet objectSearch: 1");
		CreateSetOfKeywordQueryAnswers create = new CreateSetOfKeywordQueryAnswers(pq,false);
		System.out.println("RDFBySubjectSet objectSearch: 2");
		pm.close();
		System.out.println("RDFBySubjectSet objectSearch: 3");
		return create.getAnswers();
	}
	public RDFBySubjectSet singleKeyQuery(String key) throws IOException {
		ContextAndSessionUtilities util = getUtilities();
		RegisterTransaction.register(util.getUserInfo(),TaskTypes.query, key, RegisterTransaction.checkLevel1);
		RDFBySubjectSet oset = objectSearch(key);
		RDFBySubjectSet sset = basicSearch(key);
		System.out.println("RDFBySubjectSet singleKeyQuery: 1");
		oset.mergeValue(sset);
		System.out.println("RDFBySubjectSet singleKeyQuery: 2");
		
		
		return oset;
		
	}
	public RDFTreeNode searchedRegisteredQueries(String query)  throws IOException {
		System.out.println("RegisteredQueries.getRegistered()");
		SetOfParseQueries queries = RegisteredQueries.getRegistered();
		SetOfKeywordRDF total = new SetOfKeywordRDF();
		for(ParseQuery pquery : queries) {
			System.out.println("Query: " + pquery.toString());
			SetOfInterpretations interpretations = pquery.parseInput();
			for(Interpretation interpret : interpretations) {
				System.out.println("Interpretation: " + interpret.toString());
				if(interpret.interpretable(query)) {
					System.out.println("Interpretable: " + interpret.toString());
					HashSet<KeywordRDF> results = interpret.getResults(query);
					System.out.println("Results: " + results);
					total.addAll(results);
				}
			}
		}
		System.out.println("------------------------------------------------------");
		System.out.println("Total: " + total.toString());
		TreeNodeFactory factory = new TreeNodeFactory();
		RDFTreeNode node = factory.addAllRDF(query, total);
		System.out.println("------------------------------------------------------");
		System.out.println("The RDF Tree");
		System.out.println(node.toString());
		System.out.println("------------------------------------------------------");
		return node;
	}
	
	public RDFBySubjectSet sameObjectSearch(String search) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter objectfilter =
				  new FilterPredicate("object",FilterOperator.EQUAL,search);
		Query q = new Query("KeywordRDF").setFilter(objectfilter);
		PreparedQuery pq = datastore.prepare(q);
		CreateSetOfKeywordQueryAnswers create = new CreateSetOfKeywordQueryAnswers(pq,false);
		pm.close();
		return create.getAnswers();
	}

	
	
	public RDFBySubjectSet mergeSearch(HashSet<String> keyset)  throws IOException {
		RDFBySubjectSet set = new RDFBySubjectSet();
		for(String key : keyset) {
			RDFBySubjectSet subset = singleKeyQuery(key);
			set.mergeValue(subset);
		}
		return set;
	}

	@Override
	public DatabaseObject getObjectFromKey(String clsName, String key) throws Exception {
		System.out.println("getObjectFromKey:  " + clsName + "(" + key + ")");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Class<?> cls;
		Object object = null;
		pm.getFetchPlan().setGroup(FetchGroup.ALL);
		try {
			cls = Class.forName(clsName);
			object = pm.getObjectById(cls,key);
		} catch (ClassNotFoundException e) {
			throw new Exception(e.toString());
		}
		pm.retrieve(object,true);
		DatabaseObject result = (DatabaseObject)  pm.detachCopy(object);
		System.out.println("getObjectFromKey:  Result=" 
				+ result.getClass().getCanonicalName() 
				+ "(" + result.getKey() + ")");
		pm.close();
		return result;
	}
}
