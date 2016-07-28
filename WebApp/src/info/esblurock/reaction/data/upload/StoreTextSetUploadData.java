package info.esblurock.reaction.data.upload;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.description.StoreDescriptionData;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreTextSetUploadData  extends StoreObject {
	static String textSourceType = "TextInputSourceType";
	static String textSourceName = "TextSourceName";
	static String textType = "TextType";
	static String textInformationKey = "TextInformationKey";
	static String createdBy = "CreatedBy";
	public StoreTextSetUploadData(String keyword, DatabaseObject object,
			TransactionInfo transaction) {
		super(keyword, object, transaction);
	}
	protected void storeObject() {
		super.storeObject();
		TextSetUploadData data = (TextSetUploadData) object;
		data.getDescription().setParentKey(data.getKey());
		StoreDescriptionData description = new StoreDescriptionData(data.getDescription().getKeyword(),
				data.getDescription(), transaction,false);
		flushStore();
	}
	protected void storeRDF() {
		TextSetUploadData data = (TextSetUploadData) object;
		storeObjectRDF(data);
		DescriptionDataData description = data.getDescription();
		isA(data.getDescription().getDataType());
		storeStringRDF(createdBy, description.getSourcekey());
		for(InputTextSource source : data.getInputTextSources()) {
			storeStringRDF(textSourceType,source.getSourceType());
			storeStringRDF(textSourceName,source.getTextname());
			storeStringRDF(textType,source.getTextType());
			storeStringRDF(textInformationKey, source.getID());
		}
	}
	
}
