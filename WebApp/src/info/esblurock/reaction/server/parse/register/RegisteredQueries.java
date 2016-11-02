package info.esblurock.reaction.server.parse.register;

import info.esblurock.reaction.server.parse.query.ParseQueryAsReactionMultiple;
import info.esblurock.reaction.server.parse.query.ParseQueryAsReactionSimple;
import info.esblurock.reaction.server.parse.query.ParseQueryAsSingleton;
import info.esblurock.reaction.server.parse.query.ParseQueryAsTwoKeywords;
import info.esblurock.reaction.server.parse.query.SetOfParseQueries;

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
		
		ParseQueryAsReactionMultiple rxnmultiple = new ParseQueryAsReactionMultiple();
		queries.add(rxnmultiple);
		
		ParseQueryAsTwoKeywords twokeywords = new ParseQueryAsTwoKeywords();
		queries.add(twokeywords);
	}
}
