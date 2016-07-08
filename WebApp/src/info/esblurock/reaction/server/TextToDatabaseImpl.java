package info.esblurock.reaction.server;

import java.io.IOException;
import java.util.Set;

import javax.jdo.PersistenceManager;

import java.util.logging.Logger;

import info.esblurock.reaction.client.TextToDatabase;
import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.GenerateKeywordFromDescription;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.FileSourceSpecification;
import info.esblurock.reaction.data.upload.StoreTextSetUploadData;
import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.server.authorization.TaskTypes;
import info.esblurock.reaction.server.datastore.PMF;
import info.esblurock.reaction.server.event.RegisterTransaction;
import info.esblurock.reaction.server.process.DataProcesses;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.description.DataDescriptionSpecification;
import info.esblurock.reaction.server.process.upload.SourcefFileUploadInput;
import info.esblurock.reaction.server.queries.TransactionInfoQueries;
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
	public String textToDatabase(
			String className, String sourceType, 
			String keyword, String textName, String text) throws IOException {
		verify(uploadText, TaskTypes.dataInput);
		ContextAndSessionUtilities util = getUtilities();
		String userS = util.getUserName();
		String source = className + "#" + sourceType;
		RegisterTransaction.register(util.getUserInfo(),
				TaskTypes.dataInput,source, 
				RegisterTransaction.checkLevel1);
		
		Class cls;
		try {
			cls = Class.forName(className);
			Object obj = cls.newInstance();
			if(FileSourceSpecification.class.isAssignableFrom(obj.getClass())) {
				FileSourceSpecification specInstance = (FileSourceSpecification) obj;
				specInstance.setSourceType(sourceType);
				specInstance.setTextName(textName);
				specInstance.setTextBody(text);
				String transactionObjectType = specInstance.getClass().getName();
				String idCode = ManageDataSourceIdentification.getDataSourceIdentification(userS);
				TransactionInfo info = new TransactionInfo(userS, keyword, transactionObjectType, idCode);
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
		return source;
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
		String inputkeyword = GenerateKeywordFromDescription.createKeyword(data.getDescription());
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
		verify(uploadText, TaskTypes.dataInput);
		ContextAndSessionUtilities util = getUtilities();
		String userS = util.getUserName();
		String keyword = GenerateKeywordFromDescription.createKeyword(descrdata);
		System.out.println("Keyword: " + keyword);
		try {
		TransactionInfoQueries.transactionExists(userS,keyword, DescriptionDataData.class.getName());
		} catch(Exception ex) {
			System.out.println("Exception:" + ex);
			throw ex;
		}
		return keyword;
	}
	public String registerDataInputDescription(DescriptionDataData descrdata) throws IOException {
		String keyword = GenerateKeywordFromDescription.createKeyword(descrdata);
		String processName = "RegisterDataDescription";
		ContextAndSessionUtilities util = getUtilities();
		String userS = util.getUserName();
		String source = "Register: '" + keyword + "'";
		RegisterTransaction.register(util.getUserInfo(),
				TaskTypes.dataInput,source, 
				RegisterTransaction.checkLevel1);
		DataDescriptionSpecification specs = new DataDescriptionSpecification(descrdata,userS, "");
		ProcessBase process = DataProcesses.getProcess(processName,specs);
		String ans = process.process();
		return ans;
		
		
		
	}
	
}
