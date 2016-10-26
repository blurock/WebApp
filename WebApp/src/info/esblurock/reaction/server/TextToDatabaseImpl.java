package info.esblurock.reaction.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.PersistenceManager;

import org.json.JSONObject;

import com.google.appengine.api.datastore.Text;
import com.google.gwt.core.client.JavaScriptObject;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.util.JSON;

import java.util.logging.Logger;

import info.esblurock.react.parse.keywords.KeywordsFromText;
import info.esblurock.react.parse.keywords.SetOfKeyWords;
import info.esblurock.reaction.client.TextToDatabase;
import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.GenerateKeywordFromDescription;
import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.description.DataSetReference;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.FileSourceSpecification;
import info.esblurock.reaction.data.upload.StoreTextSetUploadData;
import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.server.authorization.TaskTypes;
import info.esblurock.reaction.server.event.RegisterTransaction;
import info.esblurock.reaction.server.process.DataProcesses;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.description.DataDescriptionSpecification;
import info.esblurock.reaction.server.process.description.DataSetReferencesSpecifications;
import info.esblurock.reaction.server.queries.QueryBase;
import info.esblurock.reaction.server.queries.TransactionInfoQueries;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;
import info.esblurock.reaction.server.utilities.ContextAndSessionUtilities;
import info.esblurock.reaction.server.utilities.ManageDataSourceIdentification;
import info.esblurock.reaction.server.utilities.WriteObjectTransactionToDatabase;

public class TextToDatabaseImpl extends ServerBase implements TextToDatabase {
	private static final long serialVersionUID = 1L;
	static String uploadText = "UploadText";
	static String uploadHTTP = "UploadHTTP";
	static String deleteUploadedFile = "RemoveUploadedFile";

	private static Logger log = Logger.getLogger(TextToDatabaseImpl.class.getName());

	InputStreamToLineDatabase input = new InputStreamToLineDatabase();
	StringToKeyConversion conversion = new StringToKeyConversion();

	@Override
	public String textToDatabase(String className, String sourceType, String keyword, String textName, String text)
			throws IOException {
		verify(uploadText, TaskTypes.dataInput);
		ContextAndSessionUtilities util = getUtilities();
		String userS = util.getUserName();
		String source = className + "#" + sourceType;

		RegisterTransaction.register(util.getUserInfo(), TaskTypes.dataInput, source, RegisterTransaction.checkLevel1);
		String idCode = ManageDataSourceIdentification.getDataSourceIdentification(userS);
		fileSpecification(className, sourceType, textName, text, userS, idCode, keyword);
		return source;
	}

	@Override
	public String fileAsInput(String className, String sourceType, String fileName, String text, 
			String user, String keyword) throws IOException {
		System.out.println("Keyword: '" + keyword 
				+ "'\n User='" + user  
				+ "'\n className='" + className
				+ "'\n fileName='" + fileName
				+ "'\n keyword='" + keyword);
		UploadFileTransaction upload 
			= TransactionInfoQueries.getFirstUploadFileTransactionFromKeywordUserSourceCodeAndObjectType(user,fileName);
		System.out.println("idCode='" + upload.getFileCode());
		
		FileSourceSpecification specInstance = fileSpecification(className, sourceType, 
				fileName, text, user, upload.getFileCode(), keyword);
		TransactionInfo info = new TransactionInfo(user, keyword, className, upload.getFileCode());
		WriteObjectTransactionToDatabase.writeObjectWithTransaction(user, keyword, upload.getFileCode(), specInstance);
		String ans = "Keyword: '" + keyword 
				+ "'\n User='" + user  
				+ "'\n className='" + className 
				+ "'\n idCode='" + upload.getFileCode() + "'"; 
		return ans;
	}
	
	
	private FileSourceSpecification fileSpecification(String className, String sourceType, String textName, String text, String user,
			String idCode, String keyword) throws IOException {
		FileSourceSpecification specInstance;
		try {
			Class cls = Class.forName(className);
			Object obj = cls.newInstance();
			if (FileSourceSpecification.class.isAssignableFrom(obj.getClass())) {
				specInstance = (FileSourceSpecification) obj;
				specInstance.setSourceType(sourceType);
				specInstance.setTextName(textName);
				specInstance.setTextBody(new Text(text));
				String transactionObjectType = specInstance.getClass().getName();

				TransactionInfo info = new TransactionInfo(user, keyword, transactionObjectType, idCode);
				PersistenceManager pm = PMF.get().getPersistenceManager();
				pm.makePersistent(specInstance);
				info.setStoredObjectKey(specInstance.getKey());
				pm.makePersistent(info);
				pm.close();
			} else {
				throw new IOException("Class: " + className + " not a subclass of FileSourceSpecification");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new IOException("Class: " + className + " not found");
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new IOException("Class: " + className + " cannot be instantiated");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new IOException("Class: " + className + " cannot be instantiated");
		}
		return specInstance;
	}

	@Override
	public Set<UploadFileTransaction> getSetOfUploadedFiles() {
		return input.getUploadedFiles();
	}

	@Override
	public String removeUploadedFile(String key) throws Exception {
		verify(deleteUploadedFile, TaskTypes.dataDelete);
		log.info("Verication: Task(" + deleteUploadedFile + ")  TaskType.dataDelete(" + TaskTypes.dataDelete + ")");
		System.out.println(
				"Verication: Task(" + deleteUploadedFile + ")  TaskType.dataDelete(" + TaskTypes.dataDelete + ")");
		return input.removeUpload(key);
	}

	@Override
	public String storeTextSetUploadData(TextSetUploadData data) throws Exception {
		String keyword = data.getDescription().getKeyword();
		String userKey = data.getDescription().getInputkey();
		String inputkeyword = GenerateKeywordFromDescription.createKeyword(data.getDescription());
		verify(uploadText, TaskTypes.dataInput);
		String idCode = ManageDataSourceIdentification.getDataSourceIdentification(userKey);

		String classname = data.getClass().getName();
		TransactionInfo transaction = new TransactionInfo(userKey, inputkeyword, classname, idCode);
		StoreTextSetUploadData store = new StoreTextSetUploadData(keyword, data, transaction);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		transaction.setStoredObjectKey(data.getKey());
		pm.makePersistent(transaction);
		pm.close();
		store.finish();
		return transaction.getKey();
	}

	public String checkSubmitInputData(DescriptionDataData descrdata) throws IOException {
		verify(uploadText, TaskTypes.dataInput);
		ContextAndSessionUtilities util = getUtilities();
		String userS = util.getUserName();
		String keyword = GenerateKeywordFromDescription.createKeyword(descrdata);
		System.out.println("Keyword: " + keyword);
		try {
			TransactionInfoQueries.transactionExists(userS, keyword, DescriptionDataData.class.getName());
		} catch (Exception ex) {
			System.out.println("Exception:" + ex);
			throw ex;
		}
		
		return keyword;
	}

	public String registerDataInputDescription(DescriptionDataData descrdata, ArrayList<DataSetReference> referenceList)
			throws IOException {
		String keyword = GenerateKeywordFromDescription.createKeyword(descrdata);
		String processName = "RegisterDataDescription";
		ContextAndSessionUtilities util = getUtilities();
		String userS = util.getUserName();
		String source = "Register: '" + keyword + "'";
		RegisterTransaction.register(util.getUserInfo(), TaskTypes.dataInput, source, RegisterTransaction.checkLevel1);

		System.out.println("RegisterDataDescription: " + keyword);
		DataDescriptionSpecification specs = new DataDescriptionSpecification(descrdata, userS, "");
		ProcessBase process = DataProcesses.getProcess(processName, specs);
		String ans = process.process();

		System.out.println("RegisterDataSetReferences: " + keyword);
		String registerProcess = "RegisterDataSetReferences";
		DataSetReferencesSpecifications registerspec = new DataSetReferencesSpecifications(referenceList, keyword,
				userS, "");
		process = DataProcesses.getProcess(registerProcess, registerspec);
		ans += "\n" + process.process();

		System.out.println("registerDataInputDescription\n\n" + ans);
		return ans;
	}

	public String registerReferences(String keyword, ArrayList<DataSetReference> reflist) throws IOException {
		String ans = "";
		// String keyword =
		// GenerateKeywordFromDescription.createKeyword(descrdata);
		String processName = "RegisterDataSetReferences";
		ContextAndSessionUtilities util = getUtilities();
		String userS = util.getUserName();
		String source = "Register: '" + keyword + "'";
		RegisterTransaction.register(util.getUserInfo(), TaskTypes.dataInput, source, RegisterTransaction.checkLevel1);
		DataSetReferencesSpecifications references = new DataSetReferencesSpecifications(reflist, keyword, userS, "");
		return ans;
	}

	public HashSet<String> keywordsFromText(String text) {
		String categorizeResource = "resources/en-pos-maxent.bin";
		String tokenResource = "resources/en-token.bin";
		String chunkerResource = "resources/en-chunker.bin";
		KeywordsFromText keys = new KeywordsFromText(categorizeResource, tokenResource, chunkerResource);
		keys.calculateKeyWords(text);

		Collections.sort(keys.getSingleKeyWords());
		Collections.sort(keys.getPhraseKeyWords());
		HashSet<String> set = new HashSet<String>();
		for (String key : keys.getPhraseKeyWords()) {
			set.add(key.toLowerCase());
		}
		for (String key : keys.getSingleKeyWords()) {
			set.add(key.toLowerCase());
		}
		System.out.println(set);
		return set;
	}
}
