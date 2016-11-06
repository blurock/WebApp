package info.esblurock.reaction.server.parse.interpretation;
import java.util.HashSet;
import java.util.StringTokenizer;

import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.parse.objects.ParseObject;

/** This interpretation takes one filter ({@link ParseObject}) and performs a query
 * 
 * @author edwardblurock
 *
 */
public class SingletonInterpretation extends Interpretation {
	protected ParseObject filter;

	public SingletonInterpretation(ParseObject filter) {
		this.filter = filter;
	}
	@Override
	public boolean interpretable(QueryParameters input) {
		StringTokenizer tok = new StringTokenizer(input.getInputString()," ");
		return true;
	}

	@Override
	public RDFQueryResultSet getResults(QueryParameters input) {
		System.out.println("Singleton: " + input.getInputString());
		System.out.println("Singleton: " + filter);
		RDFQueryResultSet  set = getSet(filter,input);
		System.out.println("Singleton results: " + set.toString()); 
		return set;
	}

	public String toString() {
		return "Interpret: " + filter.toString();
	}
	
}
