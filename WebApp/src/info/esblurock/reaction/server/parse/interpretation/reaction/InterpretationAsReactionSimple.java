package info.esblurock.reaction.server.parse.interpretation.reaction;

import java.util.HashSet;

import info.esblurock.react.mechanisms.chemkin.ParseChemkinReaction;
import info.esblurock.react.mechanisms.chemkin.ReactionForwardReverseType;
import info.esblurock.react.mechanisms.chemkin.SetOfReactionForwardReverseTypes;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.parse.objects.chemical.reaction.ParseObjectAsReaction;
import info.esblurock.reaction.server.parse.interpretation.QueryParameters;
import info.esblurock.reaction.server.parse.interpretation.RDFQueryResultSet;
import info.esblurock.reaction.server.parse.interpretation.SingletonInterpretation;

public class InterpretationAsReactionSimple extends SingletonInterpretation {

	public InterpretationAsReactionSimple(ParseObjectAsReaction filter) {
		super(filter);
	}
	@Override
	public boolean interpretable(QueryParameters input) {
		boolean ans = false;
		SetOfReactionForwardReverseTypes types = new SetOfReactionForwardReverseTypes();
		ReactionForwardReverseType rtype = types.findReactionType(input.getInputString());
		if (rtype != null) {
			ans = true;
		}
		System.out.println("InterpretationAsReactionSimple: interpretable(" + input + ")=" + ans);
		return ans;
	}
	@Override
	public RDFQueryResultSet getResults(QueryParameters input) {
		ParseChemkinReaction parse = new ParseChemkinReaction();
		String norm = parse.normalize(input.getInputString());
		System.out.println("InterpretationAsReactionSimple Normed: " + norm);
		QueryParameters params = new QueryParameters(norm);
		return super.getResults(params);
	}
}
