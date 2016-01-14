package info.esblurock.reaction.server.datastore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class SetsOfPropertyAttributes extends HashMap<String, SetOfAttributeValuePairs> {
	private static final long serialVersionUID = 1L;
	private static final String setsOfAttributeValueSetsKey = "SetsOfAttributeValueSets";
	private String nameOfSets;
	
	public SetsOfPropertyAttributes() {
		nameOfSets = SetsOfPropertyAttributes.setsOfAttributeValueSetsKey;
	}
	public SetsOfPropertyAttributes(String setsname) {
		nameOfSets = setsname;
	}
	public SetsOfPropertyAttributes(SetsOfPropertyAttributes sets) {
		fillAttributes(sets);
	}

	public void fillAttributes(SetsOfPropertyAttributes sets) {
		this.nameOfSets = sets.nameOfSets;
		Set<String> keys = sets.keySet();
		for(String key : keys) {
			SetOfAttributeValuePairs set = sets.get(key);
			this.put(key, set);
		}
	}
	/*
	public String getPropertyFromSet(String setkey, String property) {
		
	}
	*/
	public SetOfAttributeValuePairs getPropertySet(String key) {
		return this.get(key);
	}
	public boolean addProperty(String type, String property, String attribute) {
		AttributeValuePair pair = new AttributeValuePair(property, attribute);
		return addProperty(type,pair);
	}
	public boolean addProperty(String type, AttributeValuePair pair) {
		boolean ans = false;
		SetOfAttributeValuePairs lst;
		if(this.containsKey(type)) {
			lst = this.get(type);
		} else {
			lst = new SetOfAttributeValuePairs(type);
			this.put(type, lst);
			
		}
		ans = lst.add(pair);
		return ans;
	}
	
	public String getNameOfSets() {
		return nameOfSets;
	}
	public boolean removeProperty(String attribute) {
		boolean ans = true;
		this.remove(attribute);
		return ans;
	}
	SetOfAttributeValuePairs getAttributeSet(String attributeset) {
		return this.get(attributeset);
	}
	String getAttributeValue(String attributeset, String attribute) {
		String value = null;
		SetOfAttributeValuePairs set = getAttributeSet(attributeset);
		if(set != null) {
			boolean notdone = true;
			Iterator<AttributeValuePair> iter = set.iterator();
			while(notdone && iter.hasNext()) {
				AttributeValuePair pair = iter.next();
				if(pair.equals(attribute)) {
					value = pair.getPropertyValue();
					notdone = false;
				}
			}
		}
		return value;
	}

	@Override
	public String toString() {
		JSONObject json = this.toJSON();
		return json.toString();		
	}
	
	public JSONObject toJSON() {
		JSONArray arr = new JSONArray();	
		int count = 0;
		Set<String> keys = this.keySet();
		for(String key : keys) {
			JSONObject obj = this.toJSON(key);
			arr.put(count++, obj);
		}
		JSONObject obj = new JSONObject();
		obj.put(nameOfSets, arr);
		return obj;
	}
	public JSONObject toJSON(String attributeset) {
		SetOfAttributeValuePairs set = getAttributeSet(attributeset);
		JSONObject obj = set.toJSON();
		return obj;
	}
	public void addRDFEntities(String topkey, StoreSetOfEntities entities) {
		Set<String> keys = this.keySet();
		for(String key:keys) {
			String datasetkey = topkey + "." + nameOfSets;
			SetOfAttributeValuePairs pairs = this.get(key);
			String setkey = pairs.addRDFEntities(datasetkey, entities);
			RDFDataKey rdfdata = new RDFDataKey(topkey, nameOfSets, key);
			entities.addEntity(rdfdata);
		}
	}
}
