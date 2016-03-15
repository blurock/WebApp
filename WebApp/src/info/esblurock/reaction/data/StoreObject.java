package info.esblurock.reaction.data;

import java.util.Date;

import javax.jdo.PersistenceManager;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.server.datastore.PMF;


/**
 * The Class StoreObject. this is the base object for the storage of objects in the database
 * There are basically two parts:
 * <ul>
 * <li> Storage of the object itself
 * <li> Storage of the RDF information
 * <ul>
 * 
 * This provides the basic operations for storage which includes the updating of the {@link TransactionInfo} data.
 * 
 */
public class StoreObject {
	final static String isAS = "isA";

	
	/** The persistence manager from JDO. */
	PersistenceManager pm = PMF.get().getPersistenceManager();

	/** The type delimiter. Used to separate the 'type' code (Object or String) and the keyword/info*/
	public static String typeDelimiter = "#";
	
	/** RDF predicate: The creation date. */
	public static String creationDate = "InputDate";
	
	/** RDF predicate: The date type. */
	public static String dateType = "Date";
	
	/** RDF predicate: The entered by. */
	public static String enteredBy = "EnteredBy";

	/** The object predicate. An class object key is stored in the object*/
	public static String objectPredicate = "Object";
	
	/** The string type. String information is stored in the object*/
	public static String stringType = "String";
	

	/** The keyword. */
	protected String keyword;
	
	/** The object. */
	protected DatabaseObject object;
	
	/** The transaction. */
	protected TransactionInfo transaction;
	
	/** The Key. */
	private String Key;
	
	/** The store object. */
	protected boolean storeObject;

	/**
	 * Instantiates a new store object.
	 *
	 * @param keyword the keyword base for the RDF information
	 * @param object the data class object
	 * @param transaction the accumulated transaction information
	 * @param storeObject the store object true if the object should be stored
	 */
	public StoreObject(String keyword, DatabaseObject object, TransactionInfo transaction, boolean storeObject) {
		start(keyword,object,transaction,storeObject);
	}

	/**
	 * Instantiates a new store object.
	 *
	 * @param keyword the keyword base for the RDF information
	 * @param object the data class object
	 * @param transaction the accumulated transaction information
	 */
	public StoreObject(String keyword, DatabaseObject object, TransactionInfo transaction) {
		start(keyword,object,transaction,true);
	}

	/**
	 * Common routine for the constructors.
	 *
	 * @param keyword the keyword base for the RDF information
	 * @param object the data class object
	 * @param transaction the accumulated transaction information
	 * @param storeObject the store object true if the object should be stored
	 */
	protected void start(String keyword, DatabaseObject object, TransactionInfo transaction, boolean storeObject) {
		this.keyword = keyword;
		this.object = object;
		this.transaction = transaction;
		this.storeObject = storeObject;
		storeObject();
		storeRDF();
	}
	
	/**
	 * Finish: to be called if all the transactions are done.
	 * 
	 * From the classes that override this method, the procedure 
	 * should do operations that occur after the object has been stored
	 * (this is meant mainly when the main object has dependent objects within it).
	 * 
	 * <ul>
	 * <li> RDFs:  enteredby and creationdata
	 * <li> Finalize the transaction by entering the object key and storing the {@link TransactionInfo}
	 */
	public void finish() {
		storeStringRDF(enteredBy, transaction.getUser());
		storeStringRDF(creationDate, DateAsString.dateAsString(transaction.getCreationDate()));
		transaction.setStoredObjectKey(object.getKey());
		pm.makePersistent(transaction);
		pm.detachCopy(transaction);
		Key = transaction.getKey();
	}

	public void isA(String objectS) {
		storeStringRDF(isAS, objectS);
	}
	/**
	 * Store if the object has not been stored yet (checking if key is null).
	 *
	 * @param object the object
	 */
	protected void store(DatabaseObject object) {
		String key = object.getKey();
		if (key == null) {
			DatabaseObject o = pm.makePersistent(object);
		}
	}

	/**
	 * Store as RDF
	 * 
	 * The String refers to that the object is a string value
	 * 
	 * The 'global' keyword of the class is the subject
	 * The predicate and the object (description) is supplied.
	 *
	 * @param predicate the predicate relation between subject and object
	 * @param description the object description of the subject
	 */
	protected void storeStringRDF(String predicate, String description) {
		String typepredicate = predicate + typeDelimiter + stringType;
		KeywordRDF objectrdf = new KeywordRDF(keyword, typepredicate, description);
		DatabaseObject o = pm.makePersistent(objectrdf);
		transaction.addRDFKey(o.getKey());
	}

	/**
	 * Store object rdf.
	 *
	 * The subject is the keyword of the object
	 * The predicate is the object predicate objectPredicate (a constant of the class).
	 * The object is the key to the object
	 * 
	 * @param object the object itself (which has already been stored in the database and has a key
	 */
	protected void storeObjectRDF(DatabaseObject object) {
		storeObjectRDF(keyword,object);
	}

	/**
	 * The subject is the keyword given
	 * The predicate is the object predicate objectPredicate (a constant of the class).
	 * The object is the key to the object
	 *
	 * @param objectkey the Object keyword to use
	 * @param object the object
	 */
	protected void storeObjectRDF(String objectkey, DatabaseObject object) {
		//store(object);
		String key = object.getKey();
		String classname = object.getClass().getName();
		String typepredicate = classname + typeDelimiter + objectPredicate;
		KeywordRDF objectrdf = new KeywordRDF(objectkey, typepredicate, key);
		store(objectrdf);
		transaction.addRDFKey(objectrdf.getKey());
	}

	/**
	 * Store object if storeObject is true.
	 */
	protected void storeObject() {
		if (storeObject) {
			System.out.println("Store: " + object.toString());
			store(object);
		}
	}

	/**
	 * Store base RDF storage.
	 * The creation date is stored
	 */
	protected void storeRDF() {
		Date date = new Date();
		storeStringRDF(creationDate, DateAsString.dateAsString(date));
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return Key;
	}

	/**
	 * Removes the.
	 */
	public void remove() {
	}
}
