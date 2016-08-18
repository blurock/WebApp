package info.esblurock.reaction.server.parse.query;

import info.esblurock.reaction.server.parse.interpretation.SetOfInterpretations;
import info.esblurock.reaction.server.parse.interpretation.reaction.InterpretationAsReactionMultiple;

public class ParseQueryAsReactionMultiple  implements ParseQuery {

	@Override
	public SetOfInterpretations parseInput() {
		SetOfInterpretations interpret = new SetOfInterpretations();
		
		InterpretationAsReactionMultiple multiple = new InterpretationAsReactionMultiple();
		
		interpret.add(multiple);
		
		return interpret;
	}

}
