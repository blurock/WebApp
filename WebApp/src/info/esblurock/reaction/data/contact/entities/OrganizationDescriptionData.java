package info.esblurock.reaction.data.contact.entities;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.description.DescriptionDataData;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class OrganizationDescriptionData extends DatabaseObject {
    @Persistent
    String organizationtype;
    
    @Persistent(dependent = "true")
    DescriptionDataData description;
    
    @Persistent(dependent = "true")
    ContactInfoData contactinfo;
    
    @Persistent(dependent = "true")
    ContactLocationData location;

    public OrganizationDescriptionData() {
    }
	public OrganizationDescriptionData(
			String organizationtype,
			DescriptionDataData description,
			ContactInfoData contactinfo, 
			ContactLocationData location) {
		super();
		this.organizationtype = organizationtype;
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
	public String getOrganizationtype() {
		return organizationtype;
	}
	
}
