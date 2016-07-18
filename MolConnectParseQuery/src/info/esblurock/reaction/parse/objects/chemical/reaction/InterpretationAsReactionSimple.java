package info.esblurock.reaction.parse.objects.chemical.reaction;

import java.util.ArrayList;

import info.esblurock.react.mechanisms.chemkin.ReactionForwardReverseType;
import info.esblurock.react.mechanisms.chemkin.SetOfReactionForwardReverseTypes;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.parse.Interpretation;
import info.esblurock.reaction.parse.objects.ParseObject;
import info.esblurock.reaction.parse.objects.single.SingletonInterpretation;

public class InterpretationAsReactionSimple extends SingletonInterpretation {

	public InterpretationAsReactionSimple(ParseObjectAsReaction filter) {
		super(filter);
	}
	@Override
	public boolean interpretable(String input) {
		boolean ans = false;
		SetOfReactionForwardReverseTypes types = new SetOfReactionForwardReverseTypes();
		ReactionForwardReverseType rtype = types.findReactionType(input);
		if (rtype != null) {
			//ParseChemkinReaction parse = new ParseChemkinReaction();
			//String norm = parse.normalize(input);
			ans = true;
		}
		return ans;
	}

}
