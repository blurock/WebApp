package info.esblurock.reaction.server.parse.query;

import info.esblurock.reaction.parse.objects.single.ParseObjectAsObject;
import info.esblurock.reaction.parse.objects.single.ParseObjectAsSubject;
import info.esblurock.reaction.server.parse.interpretation.SetOfInterpretations;
import info.esblurock.reaction.server.parse.interpretation.SingletonInterpretation;

public class ParseQueryAsSingleton implements ParseQuery {

	public SetOfInterpretations parseInput() {
		SetOfInterpretations interpret = new SetOfInterpretations();
		ParseObjectAsObject ofilter = new ParseObjectAsObject();
		SingletonInterpretation ointerpret = new SingletonInterpretation(ofilter);
		interpret.add(ointerpret);
		ParseObjectAsSubject sfilter = new ParseObjectAsSubject();
		SingletonInterpretation sinterpret = new SingletonInterpretation(sfilter);
		interpret.add(sinterpret);
		return interpret;
	}

	public String toString() {
		return "Parse query as singleton token";
	}
}
