package info.esblurock.reaction.server.datastore.contact;

import info.esblurock.reaction.server.datastore.AttributeValuePair;
import info.esblurock.reaction.server.datastore.SetsOfPropertyAttributes;

public class ContactInfo  extends SetsOfPropertyAttributes  {
	private static final long serialVersionUID = 1L;
	public static final String emailKey = "email";
	public static final String phoneKey = "phone";
	public static final String webpageKey = "webpage";
	
	public static String contact = "ContactInfo";
	
	public ContactInfo() {
		super(contact);
	}
	public ContactInfo(String attribute, String email, String phone, String web) {
		super(contact);
		addEmail(attribute, email);
		addPhone(attribute,phone);
		addHomepage(attribute, web);
	}
	public ContactInfo(String contacttype, String attribute, String email, String phone, String web) {
		super(contacttype);
		addEmail(attribute, email);
		addPhone(attribute,phone);
		addHomepage(attribute, web);
	}

	public ContactInfo(ContactInfo info) {
		this.fillAttributes(info);
	}
	private boolean add(String attribute, String type, String email) {
		boolean ans = true;
		AttributeValuePair pair = new AttributeValuePair(type, email);
		this.addProperty(attribute, pair);
		return ans;
	}
	public boolean addEmail(String type, String email) {
		return add(type,ContactInfo.emailKey,email);
	}

	public boolean addPhone(String type, String phone) {
		return add(type,ContactInfo.phoneKey,phone);
	}
	public boolean addHomepage(String type, String webpage) {
		return add(type,ContactInfo.webpageKey,webpage);
	}

	
}
