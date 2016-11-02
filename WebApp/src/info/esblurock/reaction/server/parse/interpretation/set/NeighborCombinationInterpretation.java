package info.esblurock.reaction.server.parse.interpretation.set;

import java.util.HashSet;

import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.server.parse.interpretation.Interpretation;
import info.esblurock.reaction.server.parse.interpretation.QueryParameters;
import info.esblurock.reaction.server.parse.interpretation.RDFQueryResultSet;

public class NeighborCombinationInterpretation extends Interpretation {

	@Override
	public boolean interpretable(QueryParameters input) {
		return true;
	}

	/**
	 * Common Object of subject tokens: S1 -> O <- S2 Common Subject of object
	 * tokens: O1 <- S -> O2 Common subject Common object
	 * 
	 */
	@Override
	public RDFQueryResultSet getResults(QueryParameters input) {
		CommonElementInterpretation common = new CommonElementInterpretation();
		RDFQueryResultSet set = common.getResults(input);
		return set;
	}

}
