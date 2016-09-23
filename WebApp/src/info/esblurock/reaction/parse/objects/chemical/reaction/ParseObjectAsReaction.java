package info.esblurock.reaction.parse.objects.chemical.reaction;

import java.io.IOException;

import org.apache.commons.io.comparator.CompositeFileComparator;

import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;

import info.esblurock.reaction.data.chemical.reaction.CanonicalReactionName;
import info.esblurock.reaction.data.chemical.reaction.ParsedReactionInformation;
import info.esblurock.reaction.parse.objects.ParseObject;

public class ParseObjectAsReaction extends ParseObject {
	boolean asObject;

	public ParseObjectAsReaction(boolean asObject) {
		this.asObject = asObject;
	}
	public Filter getFilter(String input) {
		CanonicalReactionName canonical = new CanonicalReactionName();
		Filter filter = null;
		try {
			ParsedReactionInformation parsed = canonical.getCanonicalReactionName(input);
			String normedforward = null;
			String normedreverse = null;
			if(parsed.getForwardReactions().size() == 1) {
				normedforward = parsed.getForwardReactions().get(0);
			}
			if(parsed.getForwardReactions().size() == 1) {
				normedreverse = parsed.getReverseReactions().get(0);
			}
			System.out.println("ParseObjectAsReaction:(" + asObject + "): '" + normedforward + "', '" + normedreverse + ")");
			if (asObject) {
				Filter filterF = new FilterPredicate("object", FilterOperator.EQUAL, normedforward);
				Filter filterR = new FilterPredicate("object", FilterOperator.EQUAL, normedreverse);
				filter = CompositeFilterOperator.or(filterF,filterR);
			} else {
				Filter filterF = new FilterPredicate("subject", FilterOperator.EQUAL, normedforward);
				Filter filterR = new FilterPredicate("subject", FilterOperator.EQUAL, normedreverse);
				filter = CompositeFilterOperator.or(filterF,filterR);
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
