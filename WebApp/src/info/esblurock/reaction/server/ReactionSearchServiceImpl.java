package info.esblurock.reaction.server;

import java.io.IOException;
import java.util.HashSet;

import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.rdf.CreateSetOfKeywordQueryAnswers;
import info.esblurock.reaction.data.rdf.RDFBySubjectSet;
import info.esblurock.reaction.data.rdf.SetOfKeywordQueryAnswers;
import info.esblurock.reaction.server.authorization.TaskTypes;
import info.esblurock.reaction.server.event.RegisterTransaction;
import info.esblurock.reaction.server.utilities.ContextAndSessionUtilities;

import javax.jdo.FetchGroup;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
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
		RDFBySubjectSet hierarchy = create.getAnswers();
		System.out.println(hierarchy.toString());
		SetOfKeywordQueryAnswers  answers = create.getAnswers().get(search);
		
		RDFBySubjectSet set = new RDFBySubjectSet();
		set.put(search, answers);
		pm.close();
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
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Class<?> cls;
		DatabaseObject object = null;
		pm.getFetchPlan().setGroup(FetchGroup.ALL);
		try {
			cls = Class.forName(clsName);
			object = (DatabaseObject) pm.getObjectById(cls,key);
		} catch (ClassNotFoundException e) {
			throw new Exception(e.toString());
		}
		pm.retrieve(object,true);
		DatabaseObject ans = pm.detachCopy(object);
		pm.close();
		return ans;
	}
}
