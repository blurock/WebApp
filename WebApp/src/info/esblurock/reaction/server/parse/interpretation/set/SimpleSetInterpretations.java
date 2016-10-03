package info.esblurock.reaction.server.parse.interpretation.set;

import java.util.ArrayList;
import java.util.HashSet;

import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.parse.objects.ParseObject;
import info.esblurock.reaction.server.parse.interpretation.Interpretation;
import info.esblurock.reaction.server.parse.interpretation.QueryParameters;

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

	public HashSet<KeywordRDF> intersection(QueryParameters input) {
		String inputS = input.getInputString();
		ArrayList<String> tokens = parseInputTokens(inputS);
		HashSet<KeywordRDF> output = null;
		boolean first = true;
		for(String token : tokens) {
			QueryParameters params = new QueryParameters(token, input.getEntityLimit());
			HashSet<KeywordRDF> rdfs = this.getSet(filter, params);
			if(first) {
				output = rdfs;
				first = false;
			} else {
				output.retainAll(rdfs);
			}
		}
		//ArrayList<KeywordRDF> lst = new ArrayList<KeywordRDF>(output);
		return output;
	}
	public HashSet<KeywordRDF> union(QueryParameters input) {
		String inputS = input.getInputString();
		ArrayList<String> tokens = parseInputTokens(inputS);
		HashSet<KeywordRDF> output = new HashSet<KeywordRDF>();
		for(String token : tokens) {
			QueryParameters params = new QueryParameters(token, input.getEntityLimit());
			HashSet<KeywordRDF> rdfs = this.getSet(filter, params);
			output.addAll(rdfs);
		}
		return output;
	}

	@Override
	public boolean interpretable(QueryParameters input) {
		return true;
	}

	@Override
	public HashSet<KeywordRDF> getResults(QueryParameters input) {
		String inputS = input.getInputString();
		HashSet<KeywordRDF> lst = new HashSet<KeywordRDF>();
		if(unionB) {
			lst.addAll(union(input));
		}
		if(intersectionB) {
			HashSet<KeywordRDF> inters = intersection(input);
			if(inters != null) {
				lst.addAll(inters);
			}
		}
		return lst;
	}
	
	
}
