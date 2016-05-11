package info.esblurock.reaction.client;

import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.data.upload.UploadFileTransaction;

import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TextToDatabaseAsync {

	void textToDatabase(String name, String text, AsyncCallback<String> callback);

	void httpToDatabase(String keyword, String http, AsyncCallback<String> callback);

	void getSetOfUploadedFiles(AsyncCallback<Set<UploadFileTransaction>> callback);

	void removeUploadedFile(String key, AsyncCallback<String> callback);

	void storeTextSetUploadData(TextSetUploadData data, AsyncCallback<String> callback);

	void checkSubmitInputData(DescriptionDataData descrdata, AsyncCallback<String> callback);

}
