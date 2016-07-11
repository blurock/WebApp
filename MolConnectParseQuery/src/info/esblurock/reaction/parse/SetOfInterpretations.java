package info.esblurock.reaction.parse;

import java.util.ArrayList;

public class SetOfInterpretations extends ArrayList<Interpretation> {
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		for(Interpretation interpret : this) {
			build.append(interpret.toString());
			build.append("\n");
		}
		return build.toString();
	}
}
