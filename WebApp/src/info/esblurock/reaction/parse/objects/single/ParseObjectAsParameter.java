package info.esblurock.reaction.parse.objects.single;

import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import info.esblurock.reaction.parse.objects.ParseObject;

public class ParseObjectAsParameter extends ParseObject {
	protected String reference;


	public ParseObjectAsParameter() {
	}
	public Filter getFilter(String input) {
		Filter subjectfilter =
				  new FilterPredicate(reference,FilterOperator.EQUAL,input);
		return subjectfilter;
	}
	public String toString() {
		return reference + "= input token";
	}
}