package info.esblurock.reaction.server.datastore.contact;

import org.json.JSONArray;
import org.json.JSONObject;

import info.esblurock.reaction.data.description.DescriptionData;
import info.esblurock.reaction.server.datastore.StoreSetOfEntities;


public class OrganizationDescription {
	public static final String organization = "OranizationDescription";
	
	public DescriptionData description;
	public ContactInfo contact;
	public ContactLocation location;
	
	public OrganizationDescription() {
		description = null;
		contact= null;
		location = null;
	}
	public OrganizationDescription(DescriptionData desc, 
			ContactInfo con,
			ContactLocation loc) {
		description = desc;
		contact = con;
		location = loc;
	}
	public JSONObject toJSON() {
		JSONArray arr = new JSONArray();
		arr.put(0, description.toJSON());
		arr.put(1, contact.toJSON());
		arr.put(2, location.toJSON());
		
		JSONObject obj = new JSONObject();
		obj.put(organization, arr);
		return obj;
	}
	@Override
	public String toString() {
		return toJSON().toString();
	}
	
	
	public void store(String owner, String keyword) {
		StoreSetOfEntities store = new StoreSetOfEntities(owner);
		
		String key = keyword + "." + OrganizationDescription.organization;
		
		contact.addRDFEntities(key, store);
		description.addRDFEntities(key, store);
		location.addRDFEntities(key, store);
		
		store.store();
	}
}
