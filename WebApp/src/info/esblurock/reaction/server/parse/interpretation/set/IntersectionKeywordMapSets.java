package info.esblurock.reaction.server.parse.interpretation.set;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.server.parse.interpretation.RDFQueryResultSet;

/** The intersection of keyword maps
 * 
 * For each unique keyword that appears in the list of {@link IntersectionKeywordMapSets}
 * The intersection of the set of the corresponding RDF's ({@link KeywordRDF}) is accumulated
 * 
 * The routine is intersction.
 * 
 * @author edwardblurock
 *
 */
public class IntersectionKeywordMapSets {
	
	public IntersectionKeywordMapSets() {
		super();
	}
	/**
	 * For each keyword, the intersections of all the subsets (RDF's with that keyword)
	 * are made. 
	 * @return
	 */
	
	public RDFQueryResultSet intersection(ArrayList<RDFKeywordSetMap> keywordMaps) {
		RDFQueryResultSet intersection = new RDFQueryResultSet();
		HashSet<String> keyset = assembleKeys(keywordMaps);
		for(String key: keyset) {
			RDFQueryResultSet rdfset = intersectionForKey(key,keywordMaps);
			intersection.addAll(rdfset);
		}
		return intersection;
	}
/**
 * This assembles all the unique keywords from the list of keyword maps ( {@link RDFKeywordSetMap} )
 * @param keywordMaps
 * @return
 */
	private HashSet<String> assembleKeys(ArrayList<RDFKeywordSetMap> keywordMaps) {
		HashSet<String> totalset = new HashSet<String>();
		for(RDFKeywordSetMap map : keywordMaps) {
			Set<String> keys = map.keySet();
			totalset.addAll(keys);
		}
		return totalset;
	}
	
	/**
	 * The intersection of all sets of RDF's ( {@link KeywordRDF} ) corresponding
	 * to the given keyword.
	 * 
	 * @param key: the key to be used for the set of maps
	 * @param keywordMaps The set of maps
	 * @return
	 */

	private RDFQueryResultSet intersectionForKey(String key, ArrayList<RDFKeywordSetMap> keywordMaps) {
		Iterator<RDFKeywordSetMap> iter = keywordMaps.iterator();
		RDFQueryResultSet intersetion = null;
		if(iter.hasNext()) {
			RDFKeywordSetMap rdfmap = iter.next();
			intersetion = rdfmap.get(key);
			while(intersetion != null && iter.hasNext()) {
				rdfmap = iter.next();
				RDFQueryResultSet keyset = rdfmap.get(key);
				intersetion.retainAll(keyset);
			}
		}
		return intersetion;
	}
		
}
