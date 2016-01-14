package info.esblurock.reaction.data;

import info.esblurock.reaction.server.datastore.AttributeValuePair;

import org.json.JSONObject;
import org.junit.Test;


public class AttributeValuePairTest {

	@Test
	public void test() {
		
		
		
		System.out.println("AttributeValuePair");
		AttributeValuePair pair = new AttributeValuePair("attribute", "value");
		System.out.println("JSONObject");
		try {
			JSONObject obj = new JSONObject();
		} catch(Exception ex) {
			System.out.println("Ex: " + ex.toString());
		}
		System.out.println("toJSON");
		
		String pairS = pair.toString();
		System.out.println("Attribute Value Pair:");
		System.out.println(pairS);
		
	}

}
