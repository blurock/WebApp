package info.esblurock.reaction.data.rdf;

import java.io.Serializable;
import java.util.ArrayList;

public class SetOfKeywordRDF extends ArrayList<KeywordRDF> implements Serializable{
	private static final long serialVersionUID = 1363420428188231616L;

	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("# results: " + this.size() + "\n");
		for(KeywordRDF rdf : this) {
			build.append(rdf.toString());
			build.append("\n");
		}
		return build.toString();
	}
}
