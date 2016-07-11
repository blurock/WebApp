package info.esblurock.reaction.parse;

import com.google.appengine.api.datastore.Query.Filter;

import info.esblurock.reaction.parse.objects.ParseObject;

public class SingletonInterpretation extends Interpretation {
	ParseObject interpret;

	public SingletonInterpretation(String input, ParseObject interpret) {
		super(input);
		this.interpret = interpret;
	}

	@Override
	public Filter getFilter() {
		return interpret.getFilter();
	}
	public String toString() {
		return "Interpret: " + interpret.toString();
	}
	
}
