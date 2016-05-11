package info.esblurock.reaction.client;

import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.data.upload.UploadFileTransaction;

import java.io.IOException;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("texttodatabase")
public interface TextToDatabase extends RemoteService {
	public static class Util {
		private static TextToDatabaseAsync instance;

		public static TextToDatabaseAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(TextToDatabase.class);
			}
			return instance;
		}
	}

	String textToDatabase(String name, String text) throws Exception;

	Set<UploadFileTransaction> getSetOfUploadedFiles();

	String removeUploadedFile(String key) throws Exception;

	String checkSubmitInputData(DescriptionDataData descrdata) throws IOException;

	String storeTextSetUploadData(TextSetUploadData data) throws Exception;

	String httpToDatabase(String keyword, String http) throws IOException;
}
