package info.esblurock.reaction.parse.objects.chemical.reaction;

import java.io.IOException;

import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import info.esblurock.reaction.parse.objects.ParseObject;
import info.esblurock.reaction.parse.objects.chemical.CanonicalReactionName;

public class ParseObjectAsReaction extends ParseObject {
	boolean asObject;

	public ParseObjectAsReaction(boolean asObject) {
		this.asObject = asObject;
	}
	public Filter getFilter(String input) {
		CanonicalReactionName canonical = new CanonicalReactionName();
		Filter filter = null;
		try {
			String normed = canonical.getCanonicalReactionName(input);
			if (asObject) {
				filter = new FilterPredicate("object", FilterOperator.EQUAL, normed);
			} else {
				filter = new FilterPredicate("subject", FilterOperator.EQUAL, normed);
			}
		} catch (IOException e) {
		}
		return filter;
	}

	public String toString() {
		String pred = "";
			if (asObject) {
				pred = "object = reaction";
			} else {
				pred = "subject = reaction";
			}
		return pred;
	}

}
