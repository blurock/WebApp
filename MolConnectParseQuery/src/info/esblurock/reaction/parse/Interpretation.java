package info.esblurock.reaction.parse;

import java.util.ArrayList;


import info.esblurock.reaction.data.rdf.KeywordRDF;

public abstract class Interpretation {
	protected String keywordRDF = "KeywordRDF";

	abstract public boolean interpretable(String input);
	abstract public ArrayList<KeywordRDF> getResults(String input);
	public String toString() {
		return "empty interpretation";
	}
}
