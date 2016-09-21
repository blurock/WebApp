package info.esblurock.reaction.client;

import info.esblurock.reaction.data.description.DataSetReference;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.data.upload.UploadFileTransaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TextToDatabaseAsync {

	void textToDatabase(String processName, String sourceType, String name, String textName, String text, AsyncCallback<String> callback);

	void getSetOfUploadedFiles(AsyncCallback<Set<UploadFileTransaction>> callback);

	void removeUploadedFile(String key, AsyncCallback<String> callback);

	void storeTextSetUploadData(TextSetUploadData data, AsyncCallback<String> callback);

	void checkSubmitInputData(DescriptionDataData descrdata, AsyncCallback<String> callback);

	void registerDataInputDescription(DescriptionDataData descrdata, 
			ArrayList<DataSetReference> referenceList,
			AsyncCallback<String> callback);

	void keywordsFromText(String text, AsyncCallback<HashSet<String>> callback);

}
