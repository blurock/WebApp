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
import info.esblurock.reaction.client.panel.description.DataDescription;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.description.StoreDescriptionData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.StoreTextSetUploadData;
import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.server.authorization.TaskTypes;
import info.esblurock.reaction.server.datastore.PMF;
import info.esblurock.reaction.server.event.RegisterTransaction;
import info.esblurock.reaction.server.process.DataProcesses;
import info.esblurock.reaction.server.process.upload.HttpUploadFileProcess;
import info.esblurock.reaction.server.queries.TransactionInfoQueries;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;
import info.esblurock.reaction.server.utilities.ContextAndSessionUtilities;
import info.esblurock.reaction.server.utilities.ManageDataSourceIdentification;

public class TextToDatabaseImpl extends ServerBase implements TextToDatabase {

	private static final long serialVersionUID = 1L;
	static String uploadText = "UploadText";
	static String uploadHTTP = "UploadHTTP";
	static String deleteUploadedFile = "RemoveUploadedFile";
	static String keywordDelimitor = "#";
	
	private static Logger log = Logger.getLogger(TextToDatabaseImpl.class.getName());
	
	InputStreamToLineDatabase  input = new InputStreamToLineDatabase();
	StringToKeyConversion conversion = new StringToKeyConversion();


	@Override
	public String textToDatabase(String name, String text) throws IOException {
		verify(uploadText, TaskTypes.dataInput);
		String source = "Text";
		
		ContextAndSessionUtilities util = getUtilities();
		String userS = util.getUserName();
		RegisterTransaction.register(util.getUserInfo(),TaskTypes.dataInput,source, RegisterTransaction.checkLevel1);
		
		String ans = null;
		try {
			InputStream stream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
			BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			//UploadFileTransaction upload = input.uploadFile(userS, name, source, br);
			//ans = upload.getKey();
		} catch (UnsupportedEncodingException e) {
			ans = "ERROR: " + e.toString();
		} catch (IOException e) {
			ans = "ERROR: " + e.toString();
		}
		
		return ans;
	}
	@Override
	public String httpToDatabase(String keyword, String http) throws IOException {
		verify(uploadHTTP,TaskTypes.dataInput);
		log.info("User verified: to read http address: " + http);
		String processName = "HTTPReadChemkinMechanismFile";
	
		ContextAndSessionUtilities util = getUtilities();
		String userS = util.getUserName();
		String register = "HTTP, " + http;
		RegisterTransaction.register(util.getUserInfo(),
				TaskTypes.dataInput,register, 
				RegisterTransaction.checkLevel1);
		DataProcesses dataprocess = DataProcesses.valueOf(processName);
		HttpUploadFileProcess process = (HttpUploadFileProcess) dataprocess.getProcess(userS, keyword, "");
		process.setHttp(http);
		String ans = process.process();
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
		String inputkeyword = generateInputKeyword(data.getDescription());
		verify(uploadText,TaskTypes.dataInput);
		String idCode = ManageDataSourceIdentification.getDataSourceIdentification(userKey);
		String classname = data.getClass().getName();
		TransactionInfo transaction = new TransactionInfo(userKey,inputkeyword,classname,idCode);
		StoreTextSetUploadData store = new StoreTextSetUploadData(keyword, data, transaction);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		transaction.setStoredObjectKey(data.getKey());
		pm.makePersistent(transaction);
		pm.close();
		store.finish();
		return transaction.getKey();
	}
	public String checkSubmitInputData(DescriptionDataData descrdata) throws IOException {
		String keyword = generateInputKeyword(descrdata);
		TransactionInfoQueries.transactionExists(keyword, TextSetUploadData.class.getName());
		return keyword;
	}
	
	public String generateInputKeyword(DescriptionDataData data) {
		return data.getSourcekey() + keywordDelimitor + data.getKeyword();
	}
}
