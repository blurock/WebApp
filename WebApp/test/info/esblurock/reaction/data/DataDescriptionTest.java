package info.esblurock.reaction.data;

import info.esblurock.reaction.data.description.DescriptionData;

import org.junit.Test;

public class DataDescriptionTest {

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
		System.out.println("Data set up");
		
		//System.out.println(data.getAttributeValue(DescriptionData.data,"attribute"));
		
		System.out.println(data.toString());
	}

}
