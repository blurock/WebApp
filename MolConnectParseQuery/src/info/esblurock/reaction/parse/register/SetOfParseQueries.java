package info.esblurock.reaction.parse.register;

import java.util.ArrayList;

import info.esblurock.reaction.parse.SetOfInterpretations;
import info.esblurock.reaction.parse.top.ParseQuery;
public class SetOfParseQueries extends ArrayList<ParseQuery>{

	public SetOfInterpretations parseInput(String input) {
		SetOfInterpretations set = new SetOfInterpretations();
		for(ParseQuery query : this) {
			if(query.interpretable(input)) {
				SetOfInterpretations subset = query.parseInput(input);
				if(subset != null) {
					set.addAll(subset);
				}
			}
		}
		return set;
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		for(ParseQuery query : this) {
			build.append(query.toString());
			build.append("\n");
		}
		return build.toString();
	}
}
