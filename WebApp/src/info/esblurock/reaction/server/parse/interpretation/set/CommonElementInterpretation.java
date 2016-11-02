package info.esblurock.reaction.server.parse.interpretation.set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.parse.objects.ParseObject;
import info.esblurock.reaction.parse.objects.single.ParseObjectAsObject;
import info.esblurock.reaction.parse.objects.single.ParseObjectAsSubject;
import info.esblurock.reaction.server.parse.interpretation.Interpretation;
import info.esblurock.reaction.server.parse.interpretation.QueryParameters;
import info.esblurock.reaction.server.parse.interpretation.RDFQueryResultSet;

/**
 * 
 * 
 * @author edwardblurock
 *
 */

public class CommonElementInterpretation extends Interpretation {
	protected ParseObject subjectFilter;
	protected ParseObject objectFilter;
	/*
	protected boolean subjectAsKey;
	protected boolean predicateAsKey;
	protected boolean objectAsKey;
	*/

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
	public CommonElementInterpretation() {
		super();
		this.subjectFilter = new ParseObjectAsSubject();
		this.objectFilter = new ParseObjectAsObject();
	}
	@Override
	public boolean interpretable(QueryParameters input) {
		String inputS = input.getInputString();
		StringTokenizer tok = new StringTokenizer(inputS," ");
		return tok.countTokens() > 1;
	}

/**
 * for each token in the input, a {@link RDFKeywordSetMap} is created.
 * Then the intersection routine of {@link IntersectionKeywordMapSets} is used to 
 * find the intersection, a list of {@link KeywordRDF}.
 * 
 */
	@Override
	public RDFQueryResultSet getResults(QueryParameters input) {
		RDFQueryResultSet totalobjectset = new RDFQueryResultSet();
		RDFQueryResultSet totalsubjectset = new RDFQueryResultSet();
		String inputS = input.getInputString();
		ArrayList<String> set = parseInputTokens(inputS);
		//System.out.println("'" + inputS + "'  ... Tokens: " + set);
		for(String key : set) {
			QueryParameters params = new QueryParameters(key, input.getEntityLimit());
			RDFQueryResultSet subjectset = getSet(subjectFilter, params);
			RDFQueryResultSet objectset = getSet(objectFilter, params);
			totalobjectset.addAll(objectset);
			totalsubjectset.addAll(subjectset);
		}
		//System.out.println("--------------------------------------------------------------");
		//System.out.println("total subjectset\n" + totalobjectset.toString());
		RDFKeywordSetMap commonobjectforsubject = setUpMap(totalobjectset,true,false);
		Set<String> objects = commonobjectforsubject.keySet();
		//System.out.println("objects:  "+ objects);
		
		//System.out.println("--------------------------------------------------------------");
		//System.out.println("total objectset\n" + totalsubjectset.toString());
		RDFKeywordSetMap commonsubjectforobject = setUpMap(totalsubjectset,false,true);
		Set<String> subjects = commonsubjectforobject.keySet();
		//System.out.println("subjects:  "+ subjects);
		//System.out.println("--------------------------------------------------------------");

		subjects.retainAll(objects);
		//System.out.println("intersection:  "+ subjects);
		RDFQueryResultSet output = new RDFQueryResultSet();
		for(String common: subjects) {
			RDFQueryResultSet commonsubjectset = commonsubjectforobject.get(common);
			RDFQueryResultSet commonobjectset = commonobjectforsubject.get(common);
			output.addAll(commonobjectset);
			output.addAll(commonsubjectset);
		}
		return output;
	}
	/** User the keyword input (used in the filter) to get a list of RDF's ( {@link KeywordRDF}
	 * 
	 * @param input a single input key 
	 * @return the results (list of RDF's) using the key in the filter
	 */
	RDFKeywordSetMap  setUpMap(RDFQueryResultSet rdfs,boolean subjectAsKey, boolean objectAsKey) {
		RDFKeywordSetMap keymap = new RDFKeywordSetMap(subjectAsKey, false ,objectAsKey);
		for(KeywordRDF rdf : rdfs) {
			keymap.add(rdf);
		}
		return keymap;		
	}
	@Override
	public String toString() {
		return "Common subject or object: " + objectFilter.toString() + ", " + subjectFilter.toString();
	}
	
}
