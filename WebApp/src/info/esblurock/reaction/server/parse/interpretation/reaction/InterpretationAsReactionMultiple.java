package info.esblurock.reaction.server.parse.interpretation.reaction;

import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

import info.esblurock.reaction.data.chemical.reaction.CanonicalReactionName;
import info.esblurock.reaction.data.chemical.reaction.ParsedReactionInformation;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.server.parse.interpretation.Interpretation;
import info.esblurock.reaction.server.parse.interpretation.QueryParameters;
import info.esblurock.reaction.server.parse.interpretation.SingletonInterpretation;
import info.esblurock.reaction.server.parse.interpretation.set.NeighborCombinationInterpretation;

public class InterpretationAsReactionMultiple extends Interpretation {

	@Override
	public boolean interpretable(QueryParameters input) {
		String inputS = input.getInputString();
		StringTokenizer tok = new StringTokenizer(inputS, " ");
		return tok.countTokens() > 1;
	}

	@Override
	public HashSet<KeywordRDF> getResults(QueryParameters input) {
		HashSet<KeywordRDF> set = null;
		CanonicalReactionName canonical = new CanonicalReactionName();
		try {
			ParsedReactionInformation parsed = canonical.getCanonicalReactionName(input.getInputString());
			NeighborCombinationInterpretation neighbor = new NeighborCombinationInterpretation();
			QueryParameters params = new QueryParameters(parsed.tokensAsString());
			set = neighbor.getResults(params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return set;
	}
}
