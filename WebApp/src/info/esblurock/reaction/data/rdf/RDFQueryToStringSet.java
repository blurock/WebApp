package info.esblurock.reaction.data.rdf;

import info.esblurock.reaction.server.datastore.AttributeValuePair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class RDFQueryToStringSet extends HashMap<String,ArrayList<String>> implements Serializable {

	private String stringPrefix;
	private String delimitor = "#";
	
	boolean keysAsObject = true;
	private static final long serialVersionUID = 1L;
	
	RDFQueryToStringSet() {
		stringPrefix ="String";
	}
	RDFQueryToStringSet(String prefix) {
		stringPrefix = prefix;
	}

	public boolean addRDF(String subject, String predicate, String object) {
		boolean ans = false;
		if(predicate.endsWith(stringPrefix)) {
			ans = true;
			int pos = predicate.indexOf(delimitor);
			String pred = predicate.substring(0,pos);
			ArrayList<String> lst = this.get(pred);
			if(lst == null) {
				lst = new ArrayList<String>();
				this.put(pred,lst);
			}
			lst.add(object);
		}
		return ans;
	}
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		String indent = prefix + " : \t";
		Set<String> keys = this.keySet();
		for(String key : keys) {
			build.append(prefix + " : " + key + "\n");
			ArrayList<String> lst = this.get(key);
			for(String item : lst) {
				build.append(indent + item + "\n");
			}
		}
		return build.toString();
	}
	public void mergeValues(RDFQueryToStringSet newset) {
		for(String key : newset.keySet()) {
			ArrayList<String> newsubset = newset.get(key);
			ArrayList<String> subset = this.get(key);
			if(subset != null) {
				for(String item : newsubset) {
					subset.add(item);
				}
			} else {
				this.put(key, newsubset);
			}
		}
	}
	public boolean isKeysAsObject() {
		return keysAsObject;
	}
	public void setKeysAsObject(boolean keysAsObject) {
		this.keysAsObject = keysAsObject;
	}
	
}
