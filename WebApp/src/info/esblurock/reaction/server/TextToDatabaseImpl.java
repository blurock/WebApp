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

import javax.jdo.PersistenceManager;

import java.util.logging.Logger;

import info.esblurock.reaction.client.TextToDatabase;
import info.esblurock.reaction.data.description.StoreDescriptionData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.StoreTextSetUploadData;
import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.server.authorization.TaskTypes;
import info.esblurock.reaction.server.datastore.PMF;
import info.esblurock.reaction.server.event.RegisterTransaction;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;
import info.esblurock.reaction.server.utilities.ContextAndSessionUtilities;
import info.esblurock.reaction.server.utilities.ManageDataSourceIdentification;

public class TextToDatabaseImpl extends ServerBase implements TextToDatabase {

	private static final long serialVersionUID = 1L;
	static String uploadText = "UploadText";
	static String uploadHTTP = "UploadHTTP";
	static String deleteUploadedFile = "RemoveUploadedFile";
	
	private static Logger log = Logger.getLogger(TextToDatabaseImpl.class.getName());
	
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
			RegisterTransaction.register(util.getUserInfo(),TaskTypes.dataInput,source, RegisterTransaction.checkLevel1);
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
		log.info("Verication: Task(" + deleteUploadedFile + ")  TaskType.dataDelete(" + TaskTypes.dataDelete + ")");
		System.out.println("Verication: Task(" + deleteUploadedFile + ")  TaskType.dataDelete(" + TaskTypes.dataDelete + ")");
		return input.removeUpload(key);
	}
	
	@Override
	public String storeTextSetUploadData(TextSetUploadData data) throws Exception {
		String keyword = data.getDescription().getKeyword();
		String userKey = data.getDescription().getInputkey();
		verify(uploadText,TaskTypes.dataInput);
		String idCode = ManageDataSourceIdentification.getDataSourceIdentification(userKey);
		String classname = data.getClass().getName();
		TransactionInfo transaction = new TransactionInfo(userKey,keyword,classname,idCode);
		StoreTextSetUploadData store = new StoreTextSetUploadData(keyword, data, transaction);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		transaction.setStoredObjectKey(data.getKey());
		pm.makePersistent(transaction);
		pm.close();
		store.finish();
		return store.getKey();
	}
}
