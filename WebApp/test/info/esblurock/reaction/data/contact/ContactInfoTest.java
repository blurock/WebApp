package info.esblurock.reaction.data.contact;

import info.esblurock.reaction.server.datastore.contact.ContactInfo;

import org.junit.Test;

public class ContactInfoTest {

	@Test
	public void test() {
		
		String attribute = "attribute";
		String email = "email";
		String phone = "phone";
		String web = "web";

		ContactInfo c = new ContactInfo(attribute, email, phone, web);
		System.out.println(c.toString());	
	}

}
