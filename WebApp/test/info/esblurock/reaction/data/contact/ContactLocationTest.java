package info.esblurock.reaction.data.contact;

import info.esblurock.reaction.server.datastore.contact.ContactLocation;

import org.junit.Test;

public class ContactLocationTest {

	@Test
	public void test() {
		ContactLocation location = new ContactLocation(
		"attribute", 
		"name", "address", 
		"city", "country", "postcode",
		"gpsLatitude", "gpsLongitude"
		);
		
		System.out.println(location.toString());
	}

}
