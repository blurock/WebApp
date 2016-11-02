package info.esblurock.reaction.server.parse.interpretation;

import java.util.ArrayList;
import java.util.StringTokenizer;

import info.esblurock.reaction.parse.objects.ParseObject;
import info.esblurock.reaction.parse.objects.two.ObjectAndPredicateParseObject;
import info.esblurock.reaction.parse.objects.two.SubjectAndPredicateParseObject;
import info.esblurock.reaction.server.queries.utilities.CombinatonSets;

public class ANDInterpretation extends Interpretation {
	ParseObject parse1;
	ParseObject parse2;
	
	
	public ANDInterpretation(ParseObject parse1, ParseObject parse2) {
		super();
		this.parse1 = parse1;
		this.parse2 = parse2;
	}

	@Override
	public boolean interpretable(QueryParameters input) {
		String inputS = input.getInputString();
		StringTokenizer tok = new StringTokenizer(inputS, " ");
		return tok.countTokens() == 2;
	}

	@Override
	public RDFQueryResultSet getResults(QueryParameters input) {
		System.out.println("ANDInterpretation: '" + input + "'");
		String inputS = input.getInputString();
		CombinatonSets combinations = new CombinatonSets();
		ArrayList<QueryParameters> parameters = combinations.generateQueryParameters(inputS);
		RDFQueryResultSet totalset = new RDFQueryResultSet();
		for(QueryParameters parameter : parameters) {
			System.out.println(parameter.toString());
			RDFQueryResultSet set1 = this.getSet(parse1, parameter);
			RDFQueryResultSet set2 = this.getSet(parse2, parameter);
			totalset.addAll(set1);
			totalset.addAll(set2);
		}
		System.out.println(totalset.toString());
		return totalset;
	}

}
