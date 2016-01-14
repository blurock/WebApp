package info.esblurock.reaction.data.rdf;

import java.util.Iterator;
import java.util.Set;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;

public class CreateSetOfKeywordQueryAnswers {
	private String stringPrefix = "String";
	private String objectPrefix = "Object";
	RDFQueryToStringSet stringset = new RDFQueryToStringSet(stringPrefix);
	RDFQueryToStringSet objectset = new RDFQueryToStringSet(objectPrefix);

	SetOfKeywordQueryAnswers answers;

	public CreateSetOfKeywordQueryAnswers(PreparedQuery pq) {
		createStandardObjectsSet();
		System.out.println("Number of answers: " + pq.countEntities());
		for(Entity entity : pq.asIterable()) {
			String subject = (String) entity.getProperty(KeywordRDF.subjectProp);
			String predicate = (String) entity.getProperty(KeywordRDF.predicateProp);
			String object = (String) entity.getProperty(KeywordRDF.objectProp);

			System.out.println("Predicate: " + predicate);

			insertRDF(subject,predicate,object);
		}
	}
	private void createStandardObjectsSet() {
		answers = new SetOfKeywordQueryAnswers();
		answers.put(stringPrefix,stringset);
		answers.put(objectPrefix,objectset);
	}
	public void insertRDF(String subject, String predicate, String object) {
		boolean notdone = true;
		Set<String> keys = answers.getKeys();
		Iterator<String> iter = keys.iterator();
		while(notdone && iter.hasNext()) {
			RDFQueryToStringSet set = answers.get(iter.next());
			notdone = !set.addRDF(subject, predicate, object);
		}
	}
	public SetOfKeywordQueryAnswers getAnswers() {
		return answers;
	}
}
