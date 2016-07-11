package info.esblurock.reaction.parse;

import com.google.appengine.api.datastore.Query.Filter;

import info.esblurock.reaction.parse.objects.SetOfParseObjects;

public abstract class Interpretation {
	String input;

	public Interpretation(String input) {
		super();
		this.input = input;
	}
	
	abstract public Filter getFilter();
	public String toString() {
		return "empty interpretation";
	}
}
