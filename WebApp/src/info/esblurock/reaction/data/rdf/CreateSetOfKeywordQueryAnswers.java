package info.esblurock.reaction.data.rdf;

import java.util.Iterator;
import java.util.Set;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;

public class CreateSetOfKeywordQueryAnswers {
	RDFBySubjectSet hierarchy;
	boolean subjecttop;
	public SetOfKeywordQueryAnswers createStandardObjectsSet() {
		String stringPrefix = "String";
		String objectPrefix = "Object";
		SetOfKeywordQueryAnswers set = new SetOfKeywordQueryAnswers();
		RDFQueryToStringSet stringset = new RDFQueryToStringSet(stringPrefix);
		RDFQueryToStringSet objectset = new RDFQueryToStringSet(objectPrefix);

		set.put(stringPrefix,stringset);
		set.put(objectPrefix,objectset);
		return set;
	}

	public CreateSetOfKeywordQueryAnswers(PreparedQuery pq, boolean subjecttop) {
		this.subjecttop = subjecttop;
		hierarchy = new RDFBySubjectSet();
		System.out.println("Number of answers: " + pq.countEntities());
		for(Entity entity : pq.asIterable()) {
			String subject = (String) entity.getProperty(KeywordRDF.subjectProp);
			String predicate = (String) entity.getProperty(KeywordRDF.predicateProp);
			String object = (String) entity.getProperty(KeywordRDF.objectProp);

			String key = subject;
			if(!subjecttop)
					key = object;
			SetOfKeywordQueryAnswers set = hierarchy.get(key);
			if(set == null) {
				set = createStandardObjectsSet();
				hierarchy.put(key, set);
			}
			if(subjecttop)
				insertRDF(set,subject,predicate,object);
			else 
				insertRDF(set,object,predicate,subject);
		}
	}
	public void insertRDF(SetOfKeywordQueryAnswers answers, String subject, String predicate, String object) {
		boolean notdone = true;
		Set<String> keys = answers.getKeys();
		Iterator<String> iter = keys.iterator();
		while(notdone && iter.hasNext()) {
			RDFQueryToStringSet set = answers.get(iter.next());
			set.setKeysAsObject(subjecttop);
			notdone = !set.addRDF(subject, predicate, object);
		}
	}
	public RDFBySubjectSet getAnswers() {
		return hierarchy;
	}
}
