package info.esblurock.reaction.parse.register;

import info.esblurock.reaction.parse.objects.chemical.reaction.ParseQueryAsReactionSimple;
import info.esblurock.reaction.parse.objects.single.ParseQueryAsSingleton;

public class RegisteredQueries {
	static SetOfParseQueries queries = null;
	
	public static SetOfParseQueries getRegistered() {
		if(queries == null) {
			standardRegistered();
		}
		return queries;
	}
	
	private static void standardRegistered() {
		queries = new SetOfParseQueries();
		ParseQueryAsSingleton singleton = new ParseQueryAsSingleton();
		queries.add(singleton);
		ParseQueryAsReactionSimple rxnsimple = new ParseQueryAsReactionSimple();
		queries.add(rxnsimple);
	}
}