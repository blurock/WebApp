package info.esblurock.reaction.data;

import info.esblurock.reaction.server.datastore.AttributeValuePair;
import info.esblurock.reaction.server.datastore.SetOfAttributeValuePairs;

import org.junit.Test;

public class SetOfAttributeValuePairsTest {

	@Test
	public void test() {
		AttributeValuePair pair1 = new AttributeValuePair("attribute1", "value1");
		AttributeValuePair pair2 = new AttributeValuePair("attribute2", "value2");
		AttributeValuePair pair3 = new AttributeValuePair("attribute3", "value3");
		AttributeValuePair pair4 = new AttributeValuePair("attribute4", "value4");
		
		SetOfAttributeValuePairs pairs = new SetOfAttributeValuePairs("SetOfPairs");
		
		pairs.add(pair1);
		pairs.add(pair2);
		pairs.add(pair3);
		pairs.add(pair4);
		
		pairs.toString();
		
		System.out.println(pairs.toString());
	}

}
