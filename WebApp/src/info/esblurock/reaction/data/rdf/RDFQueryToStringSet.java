package info.esblurock.reaction.data.rdf;

import info.esblurock.reaction.server.datastore.AttributeValuePair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class RDFQueryToStringSet extends HashMap<String,ArrayList<String>> implements Serializable {

	private String stringPrefix;
	private String delimitor = "#";
	/**
	 * 
	 */
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
}
