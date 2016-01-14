package info.esblurock.reaction.server.datastore.contact;

import info.esblurock.reaction.server.datastore.SetsOfPropertyAttributes;

public class ContactLocation extends SetsOfPropertyAttributes{
	private static final long serialVersionUID = 1L;
	public static String addressNameKey = "name";
	public static String addressAddressKey = "address";
	public static String cityKey = "city";
	public static String countryKey = "country";
	public static String postCodeKey = "postcode";
	public static String gpsLatitute = "latitude";
	public static String gpsLongitude = "longitude";

	public static final String location = "ContactLocation";
	private String contactSetName;
	
	public ContactLocation() {
		super(location);
		contactSetName = location;
	}
	public ContactLocation(String contactsetname) {
		super(contactsetname);
		contactSetName = contactsetname;
	}
	
	public ContactLocation(
			String attribute, 
			String name, String address, 
			String city, String country, String postcode,
			String gpsLatitude, String gpsLongitude) {
		super(location);
		contactSetName = location;
		addAddress(attribute, 
				name, address,
				city, country, postcode,
				gpsLatitude, gpsLongitude);
	}
	public boolean addAddress(String attribute, 
			String name, String address, String city, String country, String postcode,
			String gpsLatitude, String gpsLongitude) {
		
		boolean ans = false;

		if(!this.containsKey(attribute)) {
			this.addProperty(attribute, ContactLocation.addressNameKey,name);
			this.addProperty(attribute, ContactLocation.addressAddressKey,address);
			this.addProperty(attribute, ContactLocation.cityKey,city);
			this.addProperty(attribute, ContactLocation.countryKey,country);
			this.addProperty(attribute, ContactLocation.postCodeKey,postcode);
			this.addProperty(attribute, ContactLocation.gpsLatitute,gpsLatitude);
			this.addProperty(attribute, ContactLocation.gpsLongitude,gpsLongitude);
			ans = true;
		}
		return ans;
	}

}
