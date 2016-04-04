package info.esblurock.reaction.data.rdf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class RDFBySubjectSet extends HashMap<String, SetOfKeywordQueryAnswers> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public void mergeValue(RDFBySubjectSet set) {
		for(String key : set.keySet()) {
			SetOfKeywordQueryAnswers newsubset = set.get(key);
			SetOfKeywordQueryAnswers subset = this.get(key);
			if(subset != null && newsubset != null) {
				subset.mergeValues(newsubset);
			} else if(newsubset != null) {
				this.put(key, newsubset);
			}
		}
	}

	public String toString() {
		StringBuilder build = new StringBuilder();
		String prefix = "RDFBySubjectSet:  ";
		Set<String> keys = this.keySet();
		for(String key : keys) {
			build.append("Key:" + key + "\n");
			SetOfKeywordQueryAnswers set = this.get(key);
			if(set != null)
				build.append(set.toString(prefix));
		}
		return build.toString();
		
	}
}
