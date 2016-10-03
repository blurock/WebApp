package info.esblurock.reaction.server.parse.interpretation.set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.parse.objects.ParseObject;
import info.esblurock.reaction.server.parse.interpretation.Interpretation;
import info.esblurock.reaction.server.parse.interpretation.QueryParameters;

/**
 * 
 * 
 * @author edwardblurock
 *
 */

public class CommonSubjectInterpretation extends Interpretation {
	protected ParseObject filter;
	protected boolean subjectAsKey;
	protected boolean predicateAsKey;
	protected boolean objectAsKey;

	/** CommonSubjectInterpretation
	 * @param filter The filter to use to get the {@link KeywordRDF} objects
	 * @param subjectAsKey The subject is the key to orient with
	 * @param predicateAsKey The predicate is the key to orient with
	 * @param objectAsKey The object is the key to orient with
	 * 
	 * Only one of subjectAsKey, predicateAsKey and objectAsKey should be true.
	 * These determine which key should match. 
	 * The filter is used to get a set of {@link KeywordRDF} objects. then those with share a 
	 * common token (determined by subjectAsKey, predicateAsKey and objectAsKey) are considered
	 * a match.
	 * 
	 */
	public CommonSubjectInterpretation(ParseObject filter, boolean subjectAsKey, boolean predicateAsKey,
			boolean objectAsKey) {
		super();
		this.filter = filter;
		this.subjectAsKey = subjectAsKey;
		this.predicateAsKey = predicateAsKey;
		this.objectAsKey = objectAsKey;
	}
	public CommonSubjectInterpretation(ParseObject filter) {
		this.filter = filter;
		this.subjectAsKey = true;
		this.predicateAsKey = false;
		this.objectAsKey = false;
	}
	@Override
	public boolean interpretable(QueryParameters input) {
		String inputS = input.getInputString();
		StringTokenizer tok = new StringTokenizer(inputS," ");
		return tok.countTokens() == 1;
	}

/**
 * for each token in the input, a {@link RDFKeywordSetMap} is created.
 * Then the intersection routine of {@link IntersectionKeywordMapSets} is used to 
 * find the intersection, a list of {@link KeywordRDF}.
 * 
 */
	@Override
	public HashSet<KeywordRDF> getResults(QueryParameters input) {
		String inputS = input.getInputString();
		ArrayList<String> set = parseInputTokens(inputS);
		ArrayList<RDFKeywordSetMap> fullset = new ArrayList<RDFKeywordSetMap>();
		for(String key : set) {
			QueryParameters params = new QueryParameters(key, input.getEntityLimit());
			RDFKeywordSetMap rdfmap =  getSetForToken(params,subjectAsKey,predicateAsKey,objectAsKey);
			fullset.add(rdfmap);
		}
		IntersectionKeywordMapSets intersection = new IntersectionKeywordMapSets();
		HashSet<KeywordRDF>  output = intersection.intersection(fullset);
		return output;
	}
	/** User the keyword input (used in the filter) to get a list of RDF's ( {@link KeywordRDF}
	 * 
	 * @param input a single input key 
	 * @return the results (list of RDF's) using the key in the filter
	 */
	RDFKeywordSetMap  getSetForToken(QueryParameters input,boolean subjectAsKey, boolean predicateAsKey,
			boolean objectAsKey) {
		HashSet<KeywordRDF> rdfs = this.getSet(filter,input);
		RDFKeywordSetMap keymap = new RDFKeywordSetMap(subjectAsKey, predicateAsKey,objectAsKey);
		for(KeywordRDF rdf : rdfs) {
			keymap.add(rdf);
		}
		return keymap;		
	}
	@Override
	public String toString() {
		return "Interpret: " + filter.toString();
	}
	
}
