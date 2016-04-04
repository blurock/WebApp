package info.esblurock.reaction.data.rdf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * The Class SetOfKeywordQueryAnswers.
 * 
 * This basically encapsulates a HashMap.
 * The key to the hash map can be either 
 * 'Object' where the RDF object points to a keyword of an object
 * or 
 * 'String' where the RDF object points to a text string
 * 
 * 
 * 
 */
public class SetOfKeywordQueryAnswers implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The query sets. */
	private HashMap<String, RDFQueryToStringSet> querySets;
	
	/**
	 * Instantiates a new sets the of keyword query answers.
	 */
	public SetOfKeywordQueryAnswers() {
		querySets = new HashMap<String, RDFQueryToStringSet>();
	}
	
	/**
	 * Merge values.
	 *
	 * @param newset the newset
	 */
	public void mergeValues(SetOfKeywordQueryAnswers newset) {
		HashMap<String, RDFQueryToStringSet> newquerysets = newset.getSet();
		for(String key : newquerysets.keySet()) {
			RDFQueryToStringSet newsubset = newquerysets.get(key);
			RDFQueryToStringSet subset = querySets.get(key);
			if(subset != null) {
				subset.mergeValues(newsubset);
			} else {
				querySets.put(key, newsubset);
			}
		}
	}
	
	/**
	 * Gets the sets the.
	 *
	 * @return the sets the
	 */
	protected HashMap<String, RDFQueryToStringSet> getSet() {
		return querySets;
	}
	
	/**
	 * Put.
	 *
	 * @param key the key
	 * @param set the set
	 */
	public void put(String key, RDFQueryToStringSet set) {
		querySets.put(key, set);
	}
	
	/**
	 * Gets the keys.
	 *
	 * @return the keys
	 */
	public Set<String> getKeys() {
		return querySets.keySet();
	}
	
	/**
	 * Gets the.
	 *
	 * @param type the type
	 * @return the RDF query to string set
	 */
	public RDFQueryToStringSet get(String type) {
		return querySets.get(type);
	}
	
	/**
	 * To string.
	 *
	 * @param prefix the prefix
	 * @return the string
	 */
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		
		
		
		String indent = prefix + " : \t";
		Set<String> keys = querySets.keySet();
		build.append(prefix + "SetOfKeywordQueryAnswers: " + keys.size() + "\n");
		
		for(String key : keys) {
			build.append(prefix + "AnswerKey: " + key + "\n");
			RDFQueryToStringSet queryset = querySets.get(key);
			build.append(queryset.toString(indent));
		}
		return build.toString();
		
	}
}

