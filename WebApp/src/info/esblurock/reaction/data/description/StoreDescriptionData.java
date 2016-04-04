package info.esblurock.reaction.data.description;

import java.util.Date;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.DateAsString;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreDescriptionData extends StoreObject {
	
	static String descriptionString = "Description";
	static String oneLineDescription = "OneLine";
	static String fullDescription = "FullDescription";
	static String sourceOfData= "SourceOfData";

	public StoreDescriptionData(String keyword, DatabaseObject object,
			TransactionInfo transaction) {
		super(keyword,object,transaction);
	}
	public StoreDescriptionData(String keyword, DatabaseObject object,
			TransactionInfo transaction, boolean storeObject) {
		super(keyword,object,transaction,storeObject);
	}
	
	protected void storeObject() {
		super.storeObject();
	}

	protected void storeRDF() {
		DescriptionDataData data = (DescriptionDataData) object;
		storeObjectRDF(data);
		storeStringRDF(oneLineDescription,data.getOnlinedescription());
		storeStringRDF(fullDescription,data.getFulldescription());
		storeStringRDF(sourceOfData,data.getSourcekey());
		Date sourcedate = data.getSourceDate();
		if(sourcedate == null) {
			sourcedate = new Date();
		}
		String sourcedateS = DateAsString.dateAsString(sourcedate);
		storeStringRDF(sourceOfData,sourcedateS);
		
	}

}
