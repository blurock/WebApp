package info.esblurock.reaction.data.contact.entities;

import info.esblurock.reaction.client.data.DatabaseObject;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class ContactLocationData extends DatabaseObject  {
    @Persistent
    String parentKey = "";

    @Persistent
    String addressName;
	
    @Persistent
    String addressAddress;
	
    @Persistent
    String city;
	
    @Persistent
    String country;
	
    @Persistent
    String postcode;
	
    @Persistent
    String gpslatitute;
	
    @Persistent
    String gpslongitude;

    public ContactLocationData() {
    	
    }
	public ContactLocationData(String addressName, String addressAddress,
			String city, String country, String postcode, String gpslatitute,
			String gpslongitude) {
		super();
		this.addressName = addressName;
		this.addressAddress = addressAddress;
		this.city = city;
		this.country = country;
		this.postcode = postcode;
		this.gpslatitute = gpslatitute;
		this.gpslongitude = gpslongitude;
	}
	/*
	public ContactLocationData(String keyset, ContactLocation location) {
		SetOfAttributeValuePairs set = location.getPropertySet(keyset);
		for(AttributeValuePair pair : set) {
			if(pair.getAttributeValue().equals(ContactLocation.addressNameKey)) {
				this.addressName = pair.getPropertyValue();
			} else if(pair.getAttributeValue().equals(ContactLocation.addressAddressKey)) {
				this.addressAddress = pair.getPropertyValue();
			} else if(pair.getAttributeValue().equals(ContactLocation.cityKey)) {
				this.city = pair.getPropertyValue();
			} else if(pair.getAttributeValue().equals(ContactLocation.countryKey)) {
				this.country = pair.getPropertyValue();
			} else if(pair.getAttributeValue().equals(ContactLocation.postCodeKey)) {
				this.postcode = pair.getPropertyValue();
			} else if(pair.getAttributeValue().equals(ContactLocation.gpsLatitute)) {
				this.gpslatitute = pair.getPropertyValue();
			} else if(pair.getAttributeValue().equals(ContactLocation.gpsLongitude)) {
				this.gpslongitude = pair.getPropertyValue();
			}
		}
	}
	*/
	public String getAddressName() {
		return addressName;
	}
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public String getAddressAddress() {
		return addressAddress;
	}
	public void setAddressAddress(String addressAddress) {
		this.addressAddress = addressAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getGpslatitute() {
		return gpslatitute;
	}
	public void setGpslatitute(String gpslatitute) {
		this.gpslatitute = gpslatitute;
	}
	public String getGpslongitude() {
		return gpslongitude;
	}
	public void setGpslongitude(String gpslongitude) {
		this.gpslongitude = gpslongitude;
	}
	public String getParentKey() {
		return parentKey;
	}
	public void setParentKey(String parentkey) {
		this.parentKey = parentkey;
	}
	

}
