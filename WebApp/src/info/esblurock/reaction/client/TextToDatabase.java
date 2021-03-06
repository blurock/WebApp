package info.esblurock.reaction.client;

import info.esblurock.react.parse.keywords.SetOfKeyWords;
import info.esblurock.reaction.data.description.DataSetReference;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.data.upload.UploadFileTransaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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

	String textToDatabase(String processName, String sourceType, String name, String textName, String text) throws Exception;

	Set<UploadFileTransaction> getSetOfUploadedFiles();

	String removeUploadedFile(String key) throws Exception;

	String checkSubmitInputData(DescriptionDataData descrdata) throws IOException;

	String storeTextSetUploadData(TextSetUploadData data) throws Exception;
	
	public String registerDataInputDescription(DescriptionDataData descrdata,
			ArrayList<DataSetReference> referenceList) throws IOException;
	
	public HashSet<String> keywordsFromText(String text);
	
	String fileAsInput(String className, String sourceType, String textName, String text, String user,
			String keyword)  throws IOException;

}
