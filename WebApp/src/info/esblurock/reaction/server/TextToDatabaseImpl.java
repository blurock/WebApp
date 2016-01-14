package info.esblurock.reaction.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import org.apache.log4j.Logger;

import info.esblurock.reaction.client.TextToDatabase;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.StoreTextSetUploadData;
import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.server.authorization.TaskTypes;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;
import info.esblurock.reaction.server.utilities.ContextAndSessionUtilities;

public class TextToDatabaseImpl extends ServerBase implements TextToDatabase {

	private static final long serialVersionUID = 1L;
	static String uploadText = "UploadText";
	static String uploadHTTP = "UploadHTTP";
	static String deleteUploadedFile = "RemoveUploadedFile";
	
	private static org.apache.log4j.Logger log = Logger.getLogger(TextToDatabaseImpl.class);
	
	InputStreamToLineDatabase  input = new InputStreamToLineDatabase();
	StringToKeyConversion conversion = new StringToKeyConversion();


	@Override
	public String textToDatabase(String name, String text) throws Exception {
		
		String ans = null;
		ContextAndSessionUtilities util = getUtilities();
		try {
			InputStream stream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
			BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			String source = "Text";
			String userS = util.getUserName();
			verify(uploadText, TaskTypes.dataInput);
			UploadFileTransaction upload = input.uploadFile(userS, name, source, br);
			ans = upload.getKey();
		} catch (UnsupportedEncodingException e) {
			ans = "ERROR: " + e.toString();
		} catch (IOException e) {
			ans = "ERROR: " + e.toString();
		}
		
		return ans;
	}
	@Override
	public String httpToDatabase(String http) throws Exception {
		String ans = null;
		ContextAndSessionUtilities util = getUtilities();
		try {
			URL url = new URL(http);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			String source = "HTTP";
			String userS = util.getUserName();
			verify(uploadHTTP,TaskTypes.dataInput);
			UploadFileTransaction upload = input.uploadFile(userS, http, source, br);
			ans = upload.getKey();
		} catch (UnsupportedEncodingException e) {
			ans = "ERROR: " + e.toString();
		} catch (IOException e) {
			ans = "ERROR: " + e.toString();
		}
		
		return ans;
	}
	@Override
	public Set<UploadFileTransaction> getSetOfUploadedFiles() {
		return input.getUploadedFiles();
	}
	
	@Override
	public String removeUploadedFile(String key) throws Exception {
		verify(deleteUploadedFile, TaskTypes.dataDelete);
		return input.removeUpload(key);
	}
	
	@Override
	public String storeTextSetUploadData(TextSetUploadData data) throws Exception {
		String keyword = data.getDescription().getKeyword();
		String userKey = data.getDescription().getInputkey();
		verify(uploadText,TaskTypes.dataInput);
		TransactionInfo transaction = new TransactionInfo(userKey,keyword,data.getClass().getName());
		StoreTextSetUploadData store = new StoreTextSetUploadData(keyword, data, transaction);
		store.finish();
		return store.getKey();
	}
	
}
