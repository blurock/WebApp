package info.esblurock.reaction.data;

import info.esblurock.reaction.server.datastore.AttributeValuePair;
import info.esblurock.reaction.server.datastore.SetsOfPropertyAttributes;

import org.junit.Test;

public class SetsOfPropertyAttributesTest {

	@Test
	public void test() {
		
		SetsOfPropertyAttributes sets = new SetsOfPropertyAttributes("ASetOfValues");
		
		//AttributeValuePair pair11 = new AttributeValuePair("attribute11", "value11");
		//AttributeValuePair pair12 = new AttributeValuePair("attribute12", "value12");
		AttributeValuePair pair13 = new AttributeValuePair("attribute13", "value13");
		AttributeValuePair pair14 = new AttributeValuePair("attribute14", "value14");

		AttributeValuePair pair21 = new AttributeValuePair("attribute21", "value21");
		AttributeValuePair pair22 = new AttributeValuePair("attribute22", "value22");
		AttributeValuePair pair23 = new AttributeValuePair("attribute23", "value23");
		AttributeValuePair pair24 = new AttributeValuePair("attribute24", "value24");
		
		sets.addProperty("SetOfPairs1", "attribute11", "value11");
		sets.addProperty("SetOfPairs1", "attribute12", "value12");
		sets.addProperty("SetOfPairs1", pair13);
		sets.addProperty("SetOfPairs1", pair14);
		
		sets.addProperty("SetOfPairs2", pair21);
		sets.addProperty("SetOfPairs2", pair22);
		sets.addProperty("SetOfPairs2", pair23);
		sets.addProperty("SetOfPairs2", pair24);

		
		System.out.println(sets.toString());

	}

}
