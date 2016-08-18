package info.esblurock.reaction.data.contact.entities;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.description.DescriptionDataData;

@PersistenceCapable
public class UserDescriptionData  extends DatabaseObject {
    @Persistent
    String keyword;

    @Persistent
    String userrole;
    
    @Persistent(dependent = "true")
    DescriptionDataData description;
    
    @Persistent(dependent = "true")
    ContactInfoData contactinfo;
    
    @Persistent(dependent = "true")
    ContactLocationData location;

    public UserDescriptionData() {
    }
	public UserDescriptionData(
			String userrole,
			DescriptionDataData description,
			ContactInfoData contactinfo, 
			ContactLocationData location) {
		super();
		keyword = description.getKeyword();
		this.userrole = userrole;
		this.description = description;
		this.contactinfo = contactinfo;
		this.location = location;
	}
	public DescriptionDataData getDescription() {
		return description;
	}
	public void setDescription(DescriptionDataData description) {
		this.description = description;
	}
	public ContactInfoData getContactinfo() {
		return contactinfo;
	}
	public void setContactinfo(ContactInfoData contactinfo) {
		this.contactinfo = contactinfo;
	}
	public ContactLocationData getLocation() {
		return location;
	}
	public void setLocation(ContactLocationData location) {
		this.location = location;
	}
	public String getUserRole() {
		return userrole;
	}
	public String getKeyword() {
		return keyword;
	}
	

}
