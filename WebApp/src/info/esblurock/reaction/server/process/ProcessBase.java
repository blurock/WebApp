package info.esblurock.reaction.server.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.server.datastore.StorageAndRetrievalUtilities;
import info.esblurock.reaction.server.queries.TransactionInfoQueries;
import info.esblurock.reaction.server.utilities.ManageDataSourceIdentification;

/**
 * @author edwardblurock
 * 
 *         This is the base class of the process
 * 
 *         The process() method is performs the whole method.
 * 
 *         The createObjects() is where the set up input and output objects are
 *         used to perform the method.
 * 
 *         The information from {@link DataProcesses} is used to set up the data
 *         structures for this process. The {@link DatabaseObject} and
 *         {@link TransactionInfo} pairs for input and output are given by the
 *         names of the classes specified in {@link DataProcesses}.
 * 
 *         The getInputTransactionObjectNames() (abstract) routine specifies the
 *         needed transaction types (as Strings) that are needed to perform the
 *         operation. The getOutputTransactionObjectNames() (abstract) routine
 *         specifies the transaction types (as Strings) that are created after
 *         performance of the process. The specific {@link DatabaseObject} and
 *         {@link TransactionInfo} objects to retrieve are determined by:
 *         <ul>
 *         <li>user: The user name (creator of the {@link DatabaseObject})
 *         <li>keyword: the specific keyword specifying the object set
 *         <li>sourceCode: the code specifying which object
 *         <ul>
 *
 *         The keyword is generated from the createObjectKeyword() in
 *         {@link DescriptionDataData}
 * 
 *         The inputSourceCode and the outputSourceCode specify the specific set
 *         that the input and the output refers to. These codes are mainly used
 *         for deletion of a whole set of objects.
 * 
 */
public abstract class ProcessBase {

	protected static Logger log = Logger.getLogger(ProcessBase.class.getName());

	/**
	 * The input object class names (transactionObjectType in
	 * {@link TransactionInfo}) The corresponding {@link TransactionInfo} is
	 * found with:
	 * <ul>
	 * <li>keyword: The keyword of the data set when the object was created
	 * <li>object class name (transactionObjectType)
	 * </ul>
	 **/
	protected ArrayList<String> transactionObjectTypeInputs;

	/** The output transaction object class name (as a string) **/
	protected ArrayList<String> transactionObjectTypeOutputs;

	/* The input database objects */
	protected ArrayList<DatabaseObject> objectInputs;

	/** the output {@link TransactionInfo} **/
	protected ArrayList<TransactionInfo> transactionOutputs;
	/** the list of output database objects */
	protected ArrayList<DatabaseObject> objectOutputs;

	/** The name of the process */
	// protected String processName;
	// protected DataProcesses process;
	protected String user;
	protected String keyword;
	protected String inputSourceCode;
	protected String outputSourceCode;

	/**
	 * empty constructor The empty constructor is used for registration and to
	 * access the input and output requirements
	 */
	public ProcessBase() {
		super();
		this.user = null;
		this.keyword = null;
		this.inputSourceCode = null;
		initialization();
	}

	abstract public void initialization();

	/**
	 * 
	 * @param processName:
	 *            Used to determine which proces (within {@link DataProcesses}
	 * @param user:
	 *            The user/creator of the {@link DatabaseObject}
	 * @param keyword:
	 *            The keyword specifying the set of objects
	 * @param sourceCode:
	 *            The code specifying elements within the set
	 */
	public ProcessBase(ProcessInputSpecificationsBase input) {
		super();
		if (input.getUserName() != null) {
			this.user = input.getUserName();
		}
		if (input.getKeyword() != null) {
			this.keyword = input.getKeyword();
		}
		if (input.getSourceCode() != null) {
			this.inputSourceCode = input.getSourceCode();
		}
		initialization();
	}

	protected abstract String getProcessName();

	protected abstract String getProcessDescription();

	protected abstract ArrayList<String> getInputTransactionObjectNames();

	protected abstract ArrayList<String> getOutputTransactionObjectNames();

	/**
	 * The public routine to perform the process
	 * 
	 * This routine calls a set of standard procedures, some of which will be
	 * overrided.
	 * <ul>
	 * <li>setUpInputDataObjects: fill tranactionInputs and objectInputs using
	 * transactionObjectTypeInputs
	 * <li>initializeOutputObjects: fill objectOutputs with (empty) output
	 * {@link DatabaseObject} and get next outputSourceCode
	 * <li>storeOutputObjects: Store the output (empty) {@link DatabaseObject}
	 * (from objectOutputs)
	 * <li>initializeOutputTranactions: set up and store the output
	 * {@link TransactionInfo} using transactionObjectTypeOutputs
	 * <li>addObjectTransactionInfo: add the key to the output
	 * {@link DatabaseObject} to the respective {@link TransactionInfo}
	 * <li>storeNewTransactions(): Store the output {@link TransactionInfo}
	 * (with all info filled in)
	 * <li>createObjects(): This is the heart of the process using the input and
	 * output objects set up.
	 * <li>storeOutputObjects(): Store the output {@link DatabaseObject} after
	 * the process is performed
	 * <li>storeNewTransactions():
	 * </ul>
	 * 
	 * @return A string resulting from the process
	 * @throws IOException
	 *             is an error occurs
	 */
	public String process() throws IOException {
		setUpInputDataObjects();
		initializeOutputObjects();
		initializeOutputTranactions();
		try {
			System.out.println("process(): createObjects()");
			createObjects();
			System.out.println("process(): storeOutputObjects()");
			storeOutputObjects();
			System.out.println("process(): addObjectTransactionInfo()");
			addObjectTransactionInfo();
			System.out.println("process(): storeNewTransactions()");
			storeNewTransactions();
			System.out.println("process(): DONE");
		} catch (Exception ex) {
			ex.printStackTrace();
			if(IOException.class.isAssignableFrom(ex.getClass())) {
				throw ex;
			} else {
				throw new IOException(ex.toString());
			}
 		}
		String answer = getProcessName() + " [" + user + ": " + keyword + " (" + outputSourceCode + ") ]";
		return answer;
	}

	/**
	 * Set up the initial input database objects from the list of class names in
	 * transactionObjectTypeInputs
	 * 
	 * The result is to fill objectInputs of transactions from the database.
	 * First a list of {@link TransactionInfo} is retrieved using keyword and
	 * the string name from transactionObjectTypeInputs. Second, a list of
	 * {@link DatabaseObject} is retrieved from the user name, object key,
	 * inputSourceCode, storedObjectKey in {@link TransactionInfo}
	 * 
	 * @throws IOException
	 */
	protected void setUpInputDataObjects() throws IOException {
		transactionObjectTypeInputs = getInputTransactionObjectNames();
		ArrayList<TransactionInfo> tranactionInputs = getSetOfTransactionInfos(transactionObjectTypeInputs);
		objectInputs = getListOfClassObjects(tranactionInputs);
		if (tranactionInputs.size() > 0) {
			TransactionInfo info = tranactionInputs.get(0);
			if (keyword == null) {
				keyword = info.getKeyword();
			}
			if (inputSourceCode == null) {
				inputSourceCode = info.getSourceCode();
			}
			if (user == null) {
				user = info.getUser();
			}
		}
	}

	/**
	 * Set of the list of output database objects from the list of class names
	 * (empty versions are set up) The result is to fill objectOutputs: The
	 * initialization empty is allocate here and the overrided funtion add the
	 * {@link DatabaseObject} needed The sourceCode (outputSourceCode) is set up
	 * using the user name
	 * 
	 * @throws IOException
	 */
	protected void initializeOutputObjects() throws IOException {
		outputSourceCode = ManageDataSourceIdentification.getDataSourceIdentification(user);
		objectOutputs = new ArrayList<DatabaseObject>();
	}

	/**
	 * Store the output TransactionInfo (into transactionOutputs) with the names
	 * of the database objects
	 * 
	 * The list of object names are given through a call to
	 * getOutputTransactionObjectNames() of {@link DataProcesses} For each
	 * {@link DatabaseObject} name, a {@link TransactionInfo} is formed using
	 * user and keyword (from constructor), infoname (from
	 * getOutputTransactionObjectNames(), and outputSourceCode (formed in
	 * initializeOutputObjects()). The storedObjectKey of
	 * {@link TransactionInfo} is null for now (to be filled in
	 * addObjectTransactionInfo())
	 */
	protected void initializeOutputTranactions() {
		transactionObjectTypeOutputs = getOutputTransactionObjectNames();
		transactionOutputs = new ArrayList<TransactionInfo>();
		for (String infoname : transactionObjectTypeOutputs) {
			TransactionInfo info = new TransactionInfo(user, keyword, infoname, outputSourceCode);
			transactionOutputs.add(info);
		}
	}

	/**
	 * Fill in the database output information
	 * 
	 * @throws IOException
	 *             if there is an error in the processing
	 */
	protected abstract void createObjects() throws IOException;

	/**
	 * Store the output database objects (from objectOutputs)
	 * 
	 */
	protected void storeOutputObjects() {
		try {
		StorageAndRetrievalUtilities.storeDatabaseObjects(objectOutputs);
		} catch(Exception ex) {
			log.log(Level.WARNING,"storeOutputObjects(): exception");
			for(DatabaseObject obj : objectOutputs) {
				log.log(Level.WARNING,obj.toString());
			}
			throw ex;
		}
	}

	/**
	 * add the key to the output database objects to the respective
	 * {@link TransactionInfo}
	 * 
	 */
	protected void addObjectTransactionInfo() {
		for (DatabaseObject obj : objectOutputs) {
			String name = obj.getClass().getName();
			for (TransactionInfo info : transactionOutputs) {
				if (info.getTransactionObjectType().equals(name)) {
					info.setStoredObjectKey(obj.getKey());
				}
			}
		}
	}

	/**
	 * Store the output {@link TransactionInfo} (using transactionOutputs)
	 * 
	 */
	protected void storeNewTransactions() {
		StorageAndRetrievalUtilities.storeListOfTransactionInfo(transactionOutputs);
	}

	/**
	 * For each object type in objectnames, using keyword, find the list
	 * {@link TransactionInfo}
	 * 
	 * @param objectnames:
	 *            The list of input object names
	 * @return The retrieved {@link TransactionInfo} (one for each object name)
	 * @throws IOException
	 */
	protected ArrayList<TransactionInfo> getSetOfTransactionInfos(ArrayList<String> objectnames) throws IOException {
		ArrayList<TransactionInfo> infos = new ArrayList<TransactionInfo>();
		for (String objectname : objectnames) {
			ArrayList<TransactionInfo> info = getTransactionInfo(objectname,true);
			infos.addAll(info);
		}
		return infos;
	}

	/**
	 * getTransactionInfo * Get the input {@link TransactionInfo} using the
	 * keyword and objecttype
	 * 
	 * @param sourceCode
	 *            The
	 * @param objecttype
	 * @return
	 * @throws IOException
	 */
	protected ArrayList<TransactionInfo> getTransactionInfo(String objecttype, boolean singleton) throws IOException {
		ArrayList<TransactionInfo> info = TransactionInfoQueries.getTransactionFromKeywordAndObjectType(user, keyword,
				objecttype, singleton);
		return info;
	}

	/**
	 * From a set of {@link TransactionInfo}, retrieve a set of
	 * {@link DatabaseObject}
	 * 
	 * Use transactionObjectType in {@link TransactionInfo} for the name
	 * comparison Use storedObjectKey in {@link TransactionInfo} to retrieve the
	 * object
	 * 
	 * @param list
	 *            of input {@link TransactionInfo}
	 * @return The corresponding list of input {@link DatabaseObject}
	 * @throws IOException
	 */
	protected ArrayList<DatabaseObject> getListOfClassObjects(ArrayList<TransactionInfo> set) throws IOException {
		String error = "";
		ArrayList<DatabaseObject> objects = new ArrayList<DatabaseObject>();
		for (TransactionInfo info : set) {
			DatabaseObject obj;
			try {
				obj = TransactionInfoQueries.getClassObjectFromTransactionInfo(info);
				objects.add(obj);
			} catch (ClassNotFoundException e) {
				error += "\nClass object not found: " + info.getTransactionObjectType();
			}
		}
		if (error.length() > 0) {
			throw new IOException(error);
		}
		return objects;
	}

	/**
	 * getOutputSource Retrieve the object associated with the class name from
	 * the output objects
	 * 
	 * @param classname
	 *            The full name of the class
	 * @return The data object in the output objects with this name
	 * @throws IOException
	 */
	protected DatabaseObject getFromOutputObjects(String classname) throws IOException {
		DatabaseObject output = null;
		boolean notdone = true;
		Iterator<DatabaseObject> iter = objectOutputs.iterator();
		while (notdone && iter.hasNext()) {
			DatabaseObject obj = iter.next();
			if (obj.getClass().getName().equals(classname)) {
				output = obj;
				notdone = false;
			}
		}
		if (output == null) {
			throw new IOException(
					"Process: " + this.getClass().getName() + " - Object not found in inputs: '" + classname + "'");
		}
		return output;
	}

	/**
	 * getInputSource Retrieve the object associated with the class name from
	 * the input objects
	 * 
	 * @param classname
	 *            The full name of the class
	 * @return The data object in the input objects with this name
	 * @throws IOException
	 */
	protected DatabaseObject getInputSource(String classname) throws IOException {
		boolean notdone = true;
		DatabaseObject object = null;
		Iterator<DatabaseObject> iter = objectInputs.iterator();
		while (notdone && iter.hasNext()) {
			object = iter.next();
			if (object.getClass().getName().equals(classname)) {
				notdone = false;
			}
		}
		if (object == null) {
			throw new IOException(
					"Process: " + this.getClass().getName() + " - Object not found in inputs: '" + classname + "'");
		}
		return object;
	}

	public ArrayList<String> getTransactionObjectTypeInputs() {
		return transactionObjectTypeInputs;
	}

	public ArrayList<String> getTransactionObjectTypeOutputs() {
		return transactionObjectTypeOutputs;
	}
}
