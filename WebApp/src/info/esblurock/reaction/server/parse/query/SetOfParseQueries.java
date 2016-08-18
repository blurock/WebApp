package info.esblurock.reaction.server.parse.query;

import java.util.ArrayList;

import info.esblurock.reaction.server.parse.interpretation.SetOfInterpretations;

public class SetOfParseQueries extends ArrayList<ParseQuery> {


	public SetOfInterpretations parseInput() {
		SetOfInterpretations set = new SetOfInterpretations();
		for (ParseQuery query : this) {
			SetOfInterpretations subset = query.parseInput();
			if (subset != null) {
				set.addAll(subset);
			}
		}
		return set;
	}

	public String toString() {
		StringBuilder build = new StringBuilder();
		for (ParseQuery query : this) {
			build.append(query.toString());
			build.append("\n");
		}
		return build.toString();
	}
}
