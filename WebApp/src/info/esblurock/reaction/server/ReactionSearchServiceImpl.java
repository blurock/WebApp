package info.esblurock.reaction.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.keyword.KeywordStandardization;
import info.esblurock.reaction.data.rdf.CreateSetOfKeywordQueryAnswers;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.rdf.RDFBySubjectSet;
import info.esblurock.reaction.data.rdf.SetOfKeywordQueryAnswers;
import info.esblurock.reaction.data.rdf.SetOfKeywordRDF;
import info.esblurock.reaction.data.rdf.graph.RDFTreeNode;
import info.esblurock.reaction.data.rdf.graph.TreeNodeFactoryWithObjectNode;
import info.esblurock.reaction.data.repository.DataPathName;
import info.esblurock.reaction.data.repository.ListOfRepositoryDataSources;
import info.esblurock.reaction.server.authorization.TaskTypes;
import info.esblurock.reaction.server.event.RegisterTransaction;
import info.esblurock.reaction.server.parse.interpretation.Interpretation;
import info.esblurock.reaction.server.parse.interpretation.QueryParameters;
import info.esblurock.reaction.server.parse.interpretation.SetOfInterpretations;
import info.esblurock.reaction.server.parse.query.ParseQuery;
import info.esblurock.reaction.server.parse.query.SetOfParseQueries;
import info.esblurock.reaction.server.parse.register.RegisteredQueries;
import info.esblurock.reaction.server.queries.QueryBase;
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
		RDFBySubjectSet hierarchy = create.getAnswers();
		SetOfKeywordQueryAnswers  answers = create.getAnswers().get(search);
		RDFBySubjectSet set = new RDFBySubjectSet();
		set.put(search, answers);
		return set;
	}
	
	public RDFBySubjectSet objectSearch(String search) {
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
	public RDFBySubjectSet singleKeyQuery(String key) throws IOException {
		ContextAndSessionUtilities util = getUtilities();
		RegisterTransaction.register(util.getUserInfo(),TaskTypes.query, key, RegisterTransaction.checkLevel1);
		RDFBySubjectSet oset = objectSearch(key);
		RDFBySubjectSet sset = basicSearch(key);
		oset.mergeValue(sset);
		return oset;
		
	}
	public RDFTreeNode searchedRegisteredQueries(String queryS)  throws IOException {
		QueryParameters query = new QueryParameters(queryS);
		SetOfParseQueries queries = RegisteredQueries.getRegistered();
		SetOfKeywordRDF total = new SetOfKeywordRDF();
		boolean overflow = false;
		for(ParseQuery pquery : queries) {
			//System.out.println("Query: " + pquery.toString());
			SetOfInterpretations interpretations = pquery.parseInput();
			for(Interpretation interpret : interpretations) {
				//System.out.println("Interpretation: " + interpret.toString());
				if(interpret.interpretable(query)) {
					//System.out.println("Interpretable: " + interpret.toString());
					HashSet<KeywordRDF> results = interpret.getResults(query);
					if(interpret.isOverflow())
						overflow = true;
					//System.out.println("Results: " + results);
					total.addAll(results);
				}
			}
		}
		TreeNodeFactoryWithObjectNode factory = new TreeNodeFactoryWithObjectNode();
		RDFTreeNode node = factory.addAllRDF(query.getInputString(), total);
		node.setOverflow(overflow);
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

	public ListOfRepositoryDataSources getRepositoryDataSources() throws IOException {
		ListOfRepositoryDataSources lst = new ListOfRepositoryDataSources();
		
		String clsname = DescriptionDataData.class.getName();
		Filter filter = null;
		ArrayList<String> propertynames = new ArrayList<String>();
		propertynames.add(ListOfRepositoryDataSources.sourceKey);
		propertynames.add(ListOfRepositoryDataSources.keyword);
		propertynames.add(ListOfRepositoryDataSources.path);
		ArrayList<ArrayList<Object>> results = QueryBase.getDatabaseEntitiesFromFilter(clsname, filter, propertynames);
		for(ArrayList<Object> objects : results) {
			String sourcekey = (String) objects.get(0);
			String keyword = (String) objects.get(1);
			ArrayList<String> path = (ArrayList<String>) objects.get(2);
			DataPathName dataname = new DataPathName(sourcekey,keyword,path);
			lst.add(dataname);
		}
		return lst;
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
		//System.out.println("getObjectFromKey:  " + clsName + "(" + key + ")");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Class<?> cls;
		Object object = null;
		pm.getFetchPlan().setGroup(FetchGroup.ALL);
		try {
			cls = Class.forName(clsName);
			//System.out.println("getObjectFromKey:  " + cls);
			object = pm.getObjectById(cls,key);
		} catch (ClassNotFoundException e) {
			throw new Exception(e.toString());
		}
		pm.retrieve(object,true);
		DatabaseObject result = (DatabaseObject)  pm.detachCopy(object);
		//System.out.println("getObjectFromKey:  Result=" 
				//+ result.getClass().getCanonicalName() 
				//+ "(" + result.getKey() + ")");
		pm.close();
		return result;
	}

	@Override
	public String registerSynonyms(HashMap<String, ArrayList<String>> standardKeywordSynonyms) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		clearKeywordStandardization(pm);
		ArrayList<KeywordStandardization> lst = new ArrayList<KeywordStandardization>();
		Set<String> standards = standardKeywordSynonyms.keySet();
		for(String synonymsset : standards) {
			ArrayList<String> set = standardKeywordSynonyms.get(synonymsset);
			for(String synonym : set) {
				KeywordStandardization standard = new KeywordStandardization(synonymsset,synonym);
				lst.add(standard);
			}
		}
		pm = PMF.get().getPersistenceManager();
		pm.makePersistentAll(lst);
		return lst.toString();
	}

	private List<KeywordStandardization> listOfKeywordStandardization(PersistenceManager pm) {
		javax.jdo.Query query = pm.newQuery(KeywordStandardization.class);
		List<KeywordStandardization> pairs = (List<KeywordStandardization>) query.execute();
		return pairs;
	}
	private void clearKeywordStandardization(PersistenceManager pm) {
		List<KeywordStandardization> lst = listOfKeywordStandardization(pm);
		pm.deletePersistentAll(lst);
		pm.close();
	}
	@Override
	public HashMap<String, ArrayList<String>> getSynonymsForStandardKeywords() throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<KeywordStandardization> pairs = listOfKeywordStandardization(pm);
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		for(KeywordStandardization standard : pairs) {
			String standardkeyword = standard.getStandardKeyword();
			if(map.containsKey(standardkeyword)) {
				ArrayList<String> synonyms = map.get(standardkeyword);
				synonyms.add(standard.getSynonym());
 			} else {
 				ArrayList<String> synonyms = new ArrayList<String>();
 				synonyms.add(standard.getSynonym());
 				map.put(standardkeyword, synonyms);
			}
		}
		return map;
		
	}
}
