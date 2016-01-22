package info.esblurock.reaction.data;

import java.util.Date;

import javax.jdo.PersistenceManager;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.server.datastore.PMF;
	

public class StoreObject {
	PersistenceManager pm = PMF.get().getPersistenceManager();
	
	public static String typeDelimiter = "#";
	public static String creationDate = "InputDate";
	public static String dateType = "Date";
	public static String objectPredicate = "Object";
	public static String stringType = "String";
	public static String enteredBy = "EnteredBy";
	
	protected String keyword;
	protected DatabaseObject object;
	protected TransactionInfo transaction;
	private String Key;
	
	public StoreObject(String keyword, DatabaseObject object, TransactionInfo transaction) {
		this.keyword = keyword;
		this.object = object;
		this.transaction = transaction;
		
		storeRDF();
		storeObject();
	}
	public void finish() {
		storeStringRDF(enteredBy,transaction.getUser());
		storeStringRDF(creationDate,DateAsString.dateAsString(transaction.getCreationDate()));
		transaction.setStoredObjectKey(object.getKey());
		pm.makePersistent(transaction);
		pm.detachCopy(transaction);
		Key = transaction.getKey();
	}
	protected void store(DatabaseObject object) {
		String key = object.getKey();
		if(key == null) {
			DatabaseObject o = pm.makePersistent(object);
		}
	}
	protected void storeStringRDF(String predicate, String description) {
		String typepredicate = predicate + typeDelimiter + stringType;
		KeywordRDF objectrdf = new KeywordRDF(keyword, typepredicate, description);
		DatabaseObject o = pm.makePersistent(objectrdf);
		transaction.addRDFKey(o.getKey());
	}
	protected void storeObjectRDF(DatabaseObject object) {
		store(object);
		String key = object.getKey();
		String classname = object.getClass().getName();
		String typepredicate = classname + typeDelimiter + objectPredicate;
		KeywordRDF objectrdf = new KeywordRDF(keyword, typepredicate, key.toString());
		store(objectrdf);
		transaction.addRDFKey(objectrdf.getKey());
	}
	
	protected void storeObject() {
		store(object);
	}

	protected void storeRDF() {
		Date date = new Date();
		storeStringRDF(creationDate, DateAsString.dateAsString(date));
	}

	public String getKey() {
		return Key;
	}
	public void remove() {
	}
}
