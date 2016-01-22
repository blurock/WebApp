package info.esblurock.react.data;

import static org.junit.Assert.*;
import info.esblurock.reaction.contact.ContactInfo;
import info.esblurock.reaction.contact.ContactLocation;

import org.junit.Test;

public class ContactInfoTest {

	@Test
	public void test() {
		ContactLocation loc = new ContactLocation();
		ContactInfo info = new ContactInfo();
		
		info.addEmail(new String("home"), new String("ed@dot.com"));
		System.out.println(info.toString());
	}

}
