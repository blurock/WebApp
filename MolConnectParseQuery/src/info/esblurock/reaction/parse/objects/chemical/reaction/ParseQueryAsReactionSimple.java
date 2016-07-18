package info.esblurock.reaction.parse.objects.chemical.reaction;

import info.esblurock.react.mechanisms.chemkin.ReactionForwardReverseType;
import info.esblurock.react.mechanisms.chemkin.SetOfReactionForwardReverseTypes;
import info.esblurock.reaction.parse.SetOfInterpretations;
import info.esblurock.reaction.parse.objects.ParseQuery;
import info.esblurock.reaction.parse.objects.single.SingletonInterpretation;

/** interpretable if 
 * There is a proper (set of) symbol(s) separating the product and reaction strings
 * If the spaces are taken away from the reaction, there is one token.
 * 
 * The input query is parsed and the reaction is normalized.
 * if the normalized reaction string 
 */
public class ParseQueryAsReactionSimple implements ParseQuery {


	@Override
	public SetOfInterpretations parseInput() {
		SetOfInterpretations interpret = new SetOfInterpretations();
		ParseObjectAsReaction ofilter = new ParseObjectAsReaction(true);
		SingletonInterpretation ointerpret = new SingletonInterpretation(ofilter);
		interpret.add(ointerpret);
		ParseObjectAsReaction sfilter = new ParseObjectAsReaction(true);
		SingletonInterpretation sinterpret = new SingletonInterpretation(sfilter);
		interpret.add(sinterpret);
		return interpret;
	}

}
