package info.esblurock.reaction.client;

import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.data.upload.UploadFileTransaction;

import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("texttodatabase")
public interface TextToDatabase extends RemoteService {
	   public static class Util
	   {
	       private static TextToDatabaseAsync instance;

	       public static TextToDatabaseAsync getInstance()
	       {
	           if (instance == null)
	           {
	               instance = GWT.create(TextToDatabase.class);
	           }
	           return instance;
	       }
	   }
	   String textToDatabase(String name, String text)  throws Exception;
	   String httpToDatabase(String http) throws Exception;
	   Set<UploadFileTransaction> getSetOfUploadedFiles();
	   String removeUploadedFile(String key) throws Exception;

	String storeTextSetUploadData(TextSetUploadData data) throws Exception;
}
