package info.esblurock.reaction.parse.objects.single;

import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import info.esblurock.reaction.parse.objects.ParseObject;

/** This generates a RDF filter where:  reference == input
 * 
 * @author edwardblurock
 *
 */
public class ParseObjectAsParameter extends ParseObject {
	/**
	 * This is the reference string to be tested (determined by the classes
	 * that inherit this one.
	 */
	protected String reference;


	public ParseObjectAsParameter() {
	}
	/**
	 * This sets up an FilterOperator.EQUAL filter between the 
	 * reference string and the input string.
	 * 
	 */
	public Filter getFilter(String input) {
		Filter subjectfilter =
				  new FilterPredicate(reference,FilterOperator.EQUAL,input);
		return subjectfilter;
	}
	public String toString() {
		return reference + "= input token";
	}
}
