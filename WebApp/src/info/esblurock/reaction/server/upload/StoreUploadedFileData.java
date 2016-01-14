package info.esblurock.reaction.server.upload;

import org.apache.tools.ant.taskdefs.SQLExec.DelimiterType;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreUploadedFileData extends StoreObject {
	static public String uploaded = "Uploaded";
	
	

	public StoreUploadedFileData(String keyword, DatabaseObject object,
			TransactionInfo transaction) {
		super(keyword, object, transaction);
		// TODO Auto-generated constructor stub
	}
	
	
	protected void storeObject() {
		store(object);
	}	
	protected void storeRDF() {
		UploadedFileData upload = (UploadedFileData) object;
		storeObjectRDF(object);
	}
}
