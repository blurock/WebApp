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
	
	public void put(String key, RDFQueryToStringSet set) {
		querySets.put(key, set);
	}
	public Set<String> getKeys() {
		return querySets.keySet();
	}
	public RDFQueryToStringSet get(String type) {
		return querySets.get(type);
	}
}

