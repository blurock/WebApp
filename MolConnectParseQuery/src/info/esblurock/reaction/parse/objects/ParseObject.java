package info.esblurock.reaction.parse.objects;

import com.google.appengine.api.datastore.Query.Filter;

public class ParseObject {
	protected String input;
	
	
	public ParseObject(String input) {
		super();
		this.input = input;
	}
	
	public Filter getFilter() {
		return null;
	}
	public String toString() {
		return "empty parse";
	}
}
