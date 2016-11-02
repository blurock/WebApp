package info.esblurock.reaction.server.parse.interpretation;

import java.util.HashSet;

import info.esblurock.reaction.data.rdf.KeywordRDF;

public class RDFQueryResultSet extends HashSet<KeywordRDF> {

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for(KeywordRDF rdf : this) {
			buffer.append(rdf.toString());
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
