package info.esblurock.react.data;

import java.util.HashMap;
import java.util.HashSet;

public class SetOfPropertyAttributes extends HashMap<String, HashSet<AttributeValuePair>> {
	private static final long serialVersionUID = 1L;

	public boolean addProperty(String type, String property, String attribute) {
		AttributeValuePair pair = new AttributeValuePair(property, attribute);
		return addProperty(type,pair);
	}
	public boolean addProperty(String type, AttributeValuePair pair) {
		boolean ans = false;
		HashSet<AttributeValuePair> lst;
		if(this.containsKey(type)) {
			lst = this.get(type);
		} else {
			lst = new HashSet<AttributeValuePair>();
			this.put(type, lst);
		}
		ans = lst.add(pair);
		return ans;
	}
	
	public boolean removeProperty(String attribute) {
		boolean ans = true;
		this.remove(attribute);
		return ans;
	}
}
