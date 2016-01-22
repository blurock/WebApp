package info.esblurock.reaction.data.rdf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;


public class SetOfKeywordQueryAnswers implements Serializable{
	private static final long serialVersionUID = 1L;

	private HashMap<String, RDFQueryToStringSet> querySets;
	
	public SetOfKeywordQueryAnswers() {
		querySets = new HashMap<String, RDFQueryToStringSet>();
	}
	
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
	
	protected HashMap<String, RDFQueryToStringSet> getSet() {
		return querySets;
	}
	public void put(String key, RDFQueryToStringSet set) {
		querySets.put(key, set);
	}
	public Set<String> getKeys() {
		return querySets.keySet();
	}
	public RDFQueryToStringSet get(String type) {
		return querySets.get(type);
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		String indent = prefix + " : \t";
		Set<String> keys = querySets.keySet();
		for(String key : keys) {
			build.append(prefix + " " + key + "\n");
			RDFQueryToStringSet queryset = querySets.get(key);
			build.append(queryset.toString(indent));
		}
		return build.toString();
		
	}
}

