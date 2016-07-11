package info.esblurock.reaction.parse.top;

import info.esblurock.reaction.parse.SetOfInterpretations;

public interface ParseQuery {
	public boolean interpretable(String input);
	public SetOfInterpretations parseInput(String input);
	public String toString();
}
