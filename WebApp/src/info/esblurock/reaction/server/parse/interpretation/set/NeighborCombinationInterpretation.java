package info.esblurock.reaction.server.parse.interpretation.set;

import java.util.HashSet;

import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.parse.objects.single.ParseObjectAsObject;
import info.esblurock.reaction.parse.objects.single.ParseObjectAsPredicate;
import info.esblurock.reaction.parse.objects.single.ParseObjectAsSubject;
import info.esblurock.reaction.server.parse.interpretation.Interpretation;
import info.esblurock.reaction.server.parse.interpretation.QueryParameters;

public class NeighborCombinationInterpretation extends Interpretation {

	@Override
	public boolean interpretable(QueryParameters input) {
		return true;
	}

	/**
	 * Common Object of subject tokens: S1 -> O <- S2 Common Subject of object
	 * tokens: O1 <- S -> O2 Common subject Common object
	 * 
	 */
	@Override
	public HashSet<KeywordRDF> getResults(QueryParameters input) {
		ParseObjectAsObject ofilter = new ParseObjectAsObject();
		ParseObjectAsSubject sfilter = new ParseObjectAsSubject();

		CommonSubjectInterpretation commonSubject = new CommonSubjectInterpretation(ofilter, true, false, false);
		CommonSubjectInterpretation commonObject = new CommonSubjectInterpretation(sfilter, false, false, true);

		HashSet<KeywordRDF> set = new HashSet<KeywordRDF>();

		HashSet<KeywordRDF> sset = commonSubject.getResults(input);
		HashSet<KeywordRDF> oset = commonObject.getResults(input);
		set.addAll(oset);
		set.addAll(sset);

		SimpleSetInterpretations singleO = new SimpleSetInterpretations(ofilter, true, false);
		SimpleSetInterpretations singleS = new SimpleSetInterpretations(sfilter, true, false);
		set.addAll(singleO.getResults(input));
		set.addAll(singleS.getResults(input));

		return set;
	}

}
