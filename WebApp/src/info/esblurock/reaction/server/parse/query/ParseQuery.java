package info.esblurock.reaction.server.parse.query;

import info.esblurock.reaction.server.parse.interpretation.SetOfInterpretations;

public interface ParseQuery {
	public SetOfInterpretations parseInput();
	public String toString();
}
