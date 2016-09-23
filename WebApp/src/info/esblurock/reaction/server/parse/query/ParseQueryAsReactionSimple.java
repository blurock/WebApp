package info.esblurock.reaction.server.parse.query;

import info.esblurock.reaction.parse.objects.chemical.reaction.ParseObjectAsReaction;
import info.esblurock.reaction.server.parse.interpretation.SetOfInterpretations;
import info.esblurock.reaction.server.parse.interpretation.SingletonInterpretation;
import info.esblurock.reaction.server.parse.interpretation.reaction.InterpretationAsReactionSimple;

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
		ParseObjectAsReaction sfilter = new ParseObjectAsReaction(false);
		SingletonInterpretation sinterpret = new SingletonInterpretation(sfilter);
		interpret.add(sinterpret);
		
		InterpretationAsReactionSimple simpleO = new InterpretationAsReactionSimple(ofilter);
		interpret.add(simpleO);
		InterpretationAsReactionSimple simpleS = new InterpretationAsReactionSimple(sfilter);
		interpret.add(simpleS);
		return interpret;
	}

}
