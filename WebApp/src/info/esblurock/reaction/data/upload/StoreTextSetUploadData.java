package info.esblurock.reaction.data.upload;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.description.StoreDescriptionData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreTextSetUploadData  extends StoreObject {
	static String textSourceType = "TextInputSourceType";
	static String textSourceName = "TextSourceName";
	static String textType = "TextType";
	static String textInformationKey = "TextInformationKey";
	
	public StoreTextSetUploadData(String keyword, DatabaseObject object,
			TransactionInfo transaction) {
		super(keyword, object, transaction);
	}
	protected void storeObject() {
		super.storeObject();
		TextSetUploadData data = (TextSetUploadData) object;
		data.getDescription().setParentKey(data.getKey());
		StoreDescriptionData description = new StoreDescriptionData(data.getDescription().getKeyword(),
				data.getDescription(), transaction);
		
	}
	protected void storeRDF() {
		TextSetUploadData data = (TextSetUploadData) object;
		storeObjectRDF(data);
		for(InputTextSource source : data.getInputTextSources()) {
			storeStringRDF(textSourceType,source.getSourceType());
			storeStringRDF(textSourceName,source.getTextname());
			storeStringRDF(textType,source.getTextType());
			storeStringRDF(textInformationKey, source.getID());
		}
	}
	
}
