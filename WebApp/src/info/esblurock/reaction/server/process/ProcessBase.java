package info.esblurock.reaction.server.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.server.TextToDatabaseImpl;
import info.esblurock.reaction.server.datastore.PMF;
import info.esblurock.reaction.server.queries.TransactionInfoQueries;
import info.esblurock.reaction.server.utilities.ManageDataSourceIdentification;

/**
 * @author edwardblurock
 * 
 * This is the base class of the process 
 * 
 * The information from {@link DataProcesses} is used to set up the data structures for this process.
 * The {@link DatabaseObject} and {@link TransactionInfo} pairs for input and output are given
 * by the names of the classes specified in {@link DataProcesses}.
 * 
 * The specific {@link DatabaseObject} objects to retrieve are determined by:
 * <ul>
 * <li> user: The user name (creator of the {@link DatabaseObject})
 * <li> keyword: the specific keyword specifying the object set
 * <li> sourceCode: the code specifying which object
 * <ul>
 * 
 */
public abstract class ProcessBase {
	
	protected static Logger log = Logger.getLogger(TextToDatabaseImpl.class.getName());

	
    /** The input transaction object class names (as a string)**/
	protected ArrayList<String> transactionObjectTypeInputs;
	
    /* The input database objects */
	protected ArrayList<DatabaseObject> objectInputs;
	
    /** The output transaction object class name (as a string)**/
	protected ArrayList<String> transactionObjectTypeOutputs;
	/** the output {@link TransactionInfo} **/
	protected ArrayList<TransactionInfo> transactionOutputs;
	/** the list of output database objects */
	protected ArrayList<DatabaseObject> objectOutputs;

    /** The name of the process */
	protected String processName;
	protected DataProcesses process;
    protected String user;
    protected String keyword;
    protected String inputSourceCode;
    protected String outputSourceCode;
/**
 * 
 * @param processName: Used to determine which proces (within {@link DataProcesses}
 * @param user: The user/creator of the {@link DatabaseObject}
 * @param keyword: The keyword specifying the set of objects
 * @param sourceCode: The code specifying elements within the set
 */
	public ProcessBase(String processName,String user, String keyword,String sourceCode) {
		super();
		this.processName = processName;
		this.user = user;
		this.keyword = keyword;
		this.inputSourceCode = sourceCode;
		process = DataProcesses.valueOf(processName);
	}

/** The public routine to perform the process
 * 
 * This routine calls a set of standard procedures, some of which will be overrided.
 * <ul>
 * 		<li> setUpInputDataObjects:  fill objectInputs of input transactions from the database
		<li> initializeOutputObjects(): fill outputSourceCode with (empty) output transactions
    	<li> storeOutputObjects(): Store the output database objects (from objectOutputs)
		<li> initializeOutputTranactions(): set up and store the output {@link TransactionInfo}
    	<li> addObjectTransactionInfo()add the key to the output database objects to the respective {@link TransactionInfo}
    	<li> storeNewTransactions(): Store the output {@link TransactionInfo}
    	<li> createObjects();
    	<li> storeOutputObjects();
    	<li> storeNewTransactions();
    	</ul>

 * @return  A string resulting from the process
 * @throws IOException is an error occurs
 */
    public String process() throws IOException {
		setUpInputDataObjects();
		initializeOutputObjects();
    	storeOutputObjects();
		initializeOutputTranactions();
    	addObjectTransactionInfo();
    	storeNewTransactions();
    	createObjects();
    	storeOutputObjects();
    	storeNewTransactions();
		return processName;
    }

/** Set up the initial input database objects from the list of class names in transactionObjectTypeInputs
 * 
 * The result is to fill objectInputs of transactions from the database.
 * 
 * @throws IOException
 */
    protected void setUpInputDataObjects() throws IOException {
    	log.info("setUpDataObjects(): ");
    	transactionObjectTypeInputs = process.getInputTransactionObjectNames();
    	ArrayList<TransactionInfo> tranactionInputs = getSetOfTransactionInfos(transactionObjectTypeInputs);
		objectInputs = getListOfClassObjects(tranactionInputs);
    }
/** Set of the list of output database objects from the list of class names
 * (empty versions are set up)
 * The result is to fill outputSourceCode.
 */
    protected void initializeOutputObjects() {
		outputSourceCode = ManageDataSourceIdentification.getDataSourceIdentification(user);
    }

/** Store the output TransactionInfo (into transactionOutputs) with the names of the database objects
 * 
 * The list of object names are given through a call to getOutputTransactionObjectNames() of {@link DataProcesses}
 */
    protected void initializeOutputTranactions() {
    	transactionObjectTypeOutputs = process.getOutputTransactionObjectNames();
    	transactionOutputs = new ArrayList<TransactionInfo>();
    	for(String infoname : transactionObjectTypeOutputs) {
    		TransactionInfo info = new TransactionInfo(user, keyword, infoname, outputSourceCode);
    		transactionOutputs.add(info);
    	}
    }
 /** Fill in the database output information
  * 
  * @throws IOException if there is an error in the processing
  */
    protected abstract void createObjects() throws IOException;
    
    
/** Store the output database objects (from objectOutputs)
 * 
 */
    protected void storeOutputObjects() {
    	log.info("storeOutputObjects(): " + objectOutputs);
		PersistenceManager pm = PMF.get().getPersistenceManager();
    	pm.makePersistentAll(objectOutputs);
    	pm.close();
    }
 
/** add the key to the output database objects to the respective {@link TransactionInfo}
 * 
 */
    protected void addObjectTransactionInfo() {
    	log.info("addObjectTransactionInfo(): " + transactionOutputs);
    	for(DatabaseObject obj : objectOutputs) {
    		String name = obj.getClass().getName();
			log.info("Object Type: " +  name);
    		for(TransactionInfo info : transactionOutputs) {
    			if(info.getTransactionObjectType().equals(name)) {
    				info.setStoredObjectKey(obj.getKey());
    				log.info("Transaction Type: " +  info.getTransactionObjectType());
    			}
    		}
    	}
    }
/** Store the output {@link TransactionInfo} (using transactionOutputs)
 * 
 */
    protected void storeNewTransactions() {
    	PersistenceManager pm = PMF.get().getPersistenceManager();
    	pm.makePersistentAll(transactionOutputs);
    	pm.close();
    }
/** Help routine to convert TransactionInfo
 * 
 * @param objectnames
 * @return
 * @throws IOException
 */
    protected ArrayList<TransactionInfo> getSetOfTransactionInfos(ArrayList<String> objectnames) throws IOException {
    	ArrayList<TransactionInfo> infos = new ArrayList<TransactionInfo>();
    	for(String objectname : objectnames) {
    		TransactionInfo info = getTransactionInfo(objectname);
    		infos.add(info);
    	}
    	return infos;
    }
/*
 * 
 */
    protected TransactionInfo getTransactionInfo(String objecttype) throws IOException {
    	TransactionInfo info = TransactionInfoQueries.getFirstTransactionFromKeywordAndObjectType(keyword, objecttype);
    	return info;
    }

    /** From a set of {@link TransactionInfo}, retrieve a set of {@link DatabaseObject}
     * 
     * Uses 
     * 
     * @param set
     * @return
     * @throws IOException
     */
    protected ArrayList<DatabaseObject> getListOfClassObjects(ArrayList<TransactionInfo> set) throws IOException {
    	String error = "";
    	ArrayList<DatabaseObject> objects = new ArrayList<DatabaseObject>();
    	for(TransactionInfo info : set) {
    		DatabaseObject obj;
			try {
				obj = getClassObject(info);
				objects.add(obj);
			} catch (ClassNotFoundException e) {
				error += "\nClass object not found: " + info.getTransactionObjectType();
			}
    	}
    	if(error.length() > 0) {
    		throw new IOException(error);
    	}
    	return objects;
    }
    /** From the (input) {@link TransactionInfo}, retrieve the (input) {@link DatabaseObject}
     * 
     * @param transaction to determine which database object to retrieve.
     * @return The (input) database object
     * @throws ClassNotFoundException
     */
    DatabaseObject getClassObject(TransactionInfo transaction) throws ClassNotFoundException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String classS = transaction.getTransactionObjectType();
		Class classC = Class.forName(classS);
		String key = transaction.getKey();
		DatabaseObject object = (DatabaseObject) pm.getObjectById(classC, key);
		return object;
    }

	public ArrayList<String> getTransactionObjectTypeInputs() {
		return transactionObjectTypeInputs;
	}

	public ArrayList<String> getTransactionObjectTypeOutputs() {
		return transactionObjectTypeOutputs;
	}

	public String getProcessName() {
		return processName;
	}
    

}
