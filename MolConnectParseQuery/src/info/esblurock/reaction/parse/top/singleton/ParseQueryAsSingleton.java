package info.esblurock.reaction.parse.top.singleton;

import java.util.StringTokenizer;

import info.esblurock.reaction.parse.SetOfInterpretations;
import info.esblurock.reaction.parse.SingletonInterpretation;
import info.esblurock.reaction.parse.objects.single.ParseObjectAsObject;
import info.esblurock.reaction.parse.objects.single.ParseObjectAsSubject;
import info.esblurock.reaction.parse.top.ParseQuery;

public class ParseQueryAsSingleton implements ParseQuery {

	public SetOfInterpretations parseInput(String input) {
		SetOfInterpretations interpret = new SetOfInterpretations();
		ParseObjectAsSubject subject = new ParseObjectAsSubject(input);
		SingletonInterpretation sinterpret = new SingletonInterpretation(input, subject);
		interpret.add(sinterpret);
		ParseObjectAsObject object = new ParseObjectAsObject(input);
		SingletonInterpretation ointerpret = new SingletonInterpretation(input, object);
		interpret.add(ointerpret);
		return interpret;
	}

	@Override
	public boolean interpretable(String input) {
		StringTokenizer tok = new StringTokenizer(input," ");
		return tok.countTokens() == 1;
	}
	public String toString() {
		return "Parse query as singleton token";
	}
}
