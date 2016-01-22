package info.esblurock.react.contact;

import info.esblurock.react.data.AttributeValuePair;
import info.esblurock.react.data.SetOfPropertyAttributes;

public class ContactInfo extends SetOfPropertyAttributes {
	private static final long serialVersionUID = 1L;
	public static final String emailKey = "email";
	public static final String phoneKey = "phone";
	public static final String webpageKey = "webpage";
	
	public static final String defaultType = "default";
	
	public ContactInfo(String type, String email, String phone, String webpage) {
		addEmail(type,email);
		addPhone(type, phone);
		addHomepage(type, webpage);
	}
	public ContactInfo(String email, String phone, String webpage) {
		addEmail(defaultType,email);
		addPhone(defaultType, phone);
		addHomepage(defaultType, webpage);
	}
	
	private boolean add(String attribute, String type, String email) {
		boolean ans = true;
		AttributeValuePair pair = new AttributeValuePair(type, email);
		this.addProperty(attribute, pair);
		return ans;
	}
	public boolean addEmail(String type, String email) {
		return add(ContactInfo.emailKey,type,email);
	}

	public boolean addPhone(String type, String phone) {
		return add(ContactInfo.phoneKey,type,phone);
	}
	public boolean addHomepage(String type, String webpage) {
		return add(ContactInfo.webpageKey,type,webpage);
	}
	
}
