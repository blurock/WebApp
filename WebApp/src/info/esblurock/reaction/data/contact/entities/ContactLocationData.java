package info.esblurock.reaction.data.contact.entities;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class ContactLocationData extends DatabaseObject  {
    @Persistent
    String parentKey = "";

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
	public ContactLocationData(String addressAddress,
			String city, String country, String postcode, 
			String gpslatitute, String gpslongitude) {
		super();
		this.addressAddress = addressAddress;
		this.city = city;
		this.country = country;
		this.postcode = postcode;
		this.gpslatitute = gpslatitute;
		this.gpslongitude = gpslongitude;
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
