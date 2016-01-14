package info.esblurock.reaction.data.contact.entities;

import info.esblurock.reaction.data.description.DescriptionDataData;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class UserContactDescriptionData  implements Serializable{

	private static final long serialVersionUID = 1L;
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long key;

    @Persistent
    String userrole;
    
    @Persistent(dependent = "true")
    DescriptionDataData description;
    
    @Persistent(dependent = "true")
    ContactInfoData contactinfo;
    
    @Persistent(dependent = "true")
    ContactLocationData location;

    
    
	public UserContactDescriptionData() {
	}

	public UserContactDescriptionData(String userrole,
			DescriptionDataData description, ContactInfoData contactinfo,
			ContactLocationData location) {
		super();
		this.userrole = userrole;
		this.description = description;
		this.contactinfo = contactinfo;
		this.location = location;
	}

	public String getUserrole() {
		return userrole;
	}

	public void setUserrole(String userrole) {
		this.userrole = userrole;
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

	public Long getKey() {
		return key;
	}


}
