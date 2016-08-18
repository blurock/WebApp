package info.esblurock.reaction.data.contact.entities;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class ContactInfoData  extends DatabaseObject {

    @Persistent
    String parentKey = "";

    @Persistent
    String email;

    @Persistent
    String phone;

    @Persistent
    String webpage;

    public ContactInfoData() {
    	
    }
	public ContactInfoData(String email, String phone, String webpage) {
		super();
		this.email = email;
		this.phone = phone;
		this.webpage = webpage;
	}
	
	public void fill(String email, String phone, String webpage) {
		this.email = email;
		this.phone = phone;
		this.webpage = webpage;		
	}
	/*
	public ContactInfoData(String keyset, ContactInfo info) {
		SetOfAttributeValuePairs set = info.getPropertySet(keyset);
		for(AttributeValuePair pair : set) {
			if(pair.getAttributeValue().equals(ContactInfo.emailKey)) {
				this.email = pair.getPropertyValue();
			} else if(pair.getAttributeValue().equals(ContactInfo.phoneKey)) {
				this.phone = pair.getPropertyValue();
			} else if(pair.getAttributeValue().equals(ContactInfo.webpageKey)) {
				this.webpage = pair.getPropertyValue();
			}
		}
	}
	*/
	public String getParentKey() {
		return parentKey;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWebpage() {
		return webpage;
	}
	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}
	public void setParentKey(String parentkey) {
		this.parentKey = parentkey;
	}
    
}
