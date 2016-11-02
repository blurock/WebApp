package info.esblurock.reaction.server.parse.interpretation.set;

import java.util.HashMap;

import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.server.parse.interpretation.RDFQueryResultSet;

/**
 * This structure organizes the added {@link KeywordRDF} into a {@link HashMap} where the 
 * token (determined by subjectAsKey, predicateAsKey and objectAsKey) is used as the 
 * key to the map.
 * 
 * @author edwardblurock
 *
 */
public class RDFKeywordSetMap extends HashMap<String,RDFQueryResultSet> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean subjectAsKey;
	boolean predicateAsKey;
	boolean objectAsKey;
	
	/**
	 * The constructor assumes that only one boolean is set to true
	 * 
	 * @param subjectAsKey
	 * @param predicateAsKey
	 * @param objectAsKey
	 */
	public RDFKeywordSetMap(boolean subjectAsKey, boolean predicateAsKey, boolean objectAsKey) {
		super();
		this.subjectAsKey = subjectAsKey;
		this.predicateAsKey = predicateAsKey;
		this.objectAsKey = objectAsKey;
	}
	/** 
	 * Add {@link KeywordRDF} to the map. Using subjectAsKey, predicateAsKey and objectAsKey 
	 * a HashMap is formed organizing the {@link KeywordRDF}.
	 * 
	 * @param rdf An RDF to add
	 */
	public void add(KeywordRDF rdf)  {
		String key = null;
		if(subjectAsKey) {
			key = rdf.getSubject();
		} else if(predicateAsKey) {
			key = rdf.getPredicate();
		} else {
			key = rdf.getObject();
		}
		RDFQueryResultSet tokenset = this.get(key);
		if(tokenset == null) {
			tokenset = new RDFQueryResultSet();
			this.put(key, tokenset);
		}
		tokenset.add(rdf);
	}
}
