package info.esblurock.reaction.server.parse.interpretation.set;

import java.util.ArrayList;
import java.util.HashSet;

import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.parse.objects.ParseObject;
import info.esblurock.reaction.server.parse.interpretation.Interpretation;

public class SimpleSetInterpretations extends Interpretation {
	protected ParseObject filter;
	boolean intersectionB;
	boolean unionB;
	/**
	 * 
	 * @param filter The filter used to access the {@link KeywordRDF}
	 * @param intersectionB Intersect all the results
	 * @param unionB Union all the results.
	 */
	public SimpleSetInterpretations(ParseObject filter, boolean intersectionB, boolean unionB) {
		super();
		this.filter = filter;
		this.intersectionB = intersectionB;
		this.unionB = unionB;
	}

	public HashSet<KeywordRDF> intersection(String inputS) {
		ArrayList<String> tokens = parseInputTokens(inputS);
		HashSet<KeywordRDF> output = null;
		boolean first = true;
		for(String token : tokens) {
			HashSet<KeywordRDF> rdfs = this.getSet(filter, token);
			if(first) {
				output = rdfs;
				first = false;
			} else {
				output.retainAll(rdfs);
			}
		}
		ArrayList<KeywordRDF> lst = new ArrayList<KeywordRDF>(output);
		return output;
	}
	public HashSet<KeywordRDF> union(String inputS) {
		ArrayList<String> tokens = parseInputTokens(inputS);
		HashSet<KeywordRDF> output = new HashSet<KeywordRDF>();
		for(String token : tokens) {
			HashSet<KeywordRDF> rdfs = this.getSet(filter, token);
			output.addAll(rdfs);
		}
		return output;
	}

	@Override
	public boolean interpretable(String input) {
		return true;
	}

	@Override
	public HashSet<KeywordRDF> getResults(String inputS) {
		HashSet<KeywordRDF> lst = new HashSet<KeywordRDF>();
		if(unionB) {
			lst.addAll(union(inputS));
		}
		if(intersectionB) {
			lst.addAll(intersection(inputS));
		}
		return lst;
	}
	
	
}
