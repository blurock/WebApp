package info.esblurock.reaction.data.contact;

import info.esblurock.reaction.data.description.DescriptionData;
import info.esblurock.reaction.server.OrganizationDescriptionServiceImpl;
import info.esblurock.reaction.server.datastore.contact.ContactInfo;
import info.esblurock.reaction.server.datastore.contact.ContactLocation;
import info.esblurock.reaction.server.datastore.contact.OrganizationDescription;

import org.junit.Test;

public class OrganizationDescriptionTest {

	@Test
	public void test() {
		
		String line = "This is line 1\n"
				+ "This is line 2\n"
				+ "This is line 3\n"
				+ "This is line 4\n"
				+ "This is line 5\n";
		
		DescriptionData data = new DescriptionData("attribute", "keyword",
				"This is a one line description",
				line);

		
		String attribute = "attribute";
		String email = "email";
		String phone = "phone";
		String web = "web";

		ContactInfo c = new ContactInfo(attribute, email, phone, web);
		
		ContactLocation location = new ContactLocation(
		"attribute", 
		"name", "address", 
		"city", "country", "postcode",
		"gpsLatitude", "gpsLongitude"
		);

		OrganizationDescription description = new OrganizationDescription(data, c, location);
		
		System.out.println(description.toString());
		
		System.out.println("Call implemenation");
		OrganizationDescriptionServiceImpl impl = new OrganizationDescriptionServiceImpl();
		String output = impl.createSingleOrganizationDescription("keyword",
				"This is a one line description",line, 
				email, phone, web, 
				"name", "address", 
				"city", "country", "postcode",
				"gpsLatitude", "gpsLongitude");
		System.out.println("out of implementation");
		System.out.println(output);
	}

}
