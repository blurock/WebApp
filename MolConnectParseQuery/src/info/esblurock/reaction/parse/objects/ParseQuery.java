package info.esblurock.reaction.parse.objects;

import info.esblurock.reaction.parse.SetOfInterpretations;

public interface ParseQuery {
	public SetOfInterpretations parseInput();
	public String toString();
}
