package info.esblurock.reaction.data.transaction;

import info.esblurock.reaction.client.callback.StandardStringReturnCallback;
import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.client.panel.transaction.TransactionService;
import info.esblurock.reaction.client.panel.transaction.TransactionServiceAsync;
import info.esblurock.reaction.server.datastore.PMF;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class TransactionInfo extends DatabaseObject {

	int maxSize =  100;
	
	@Persistent
	String storedObjectKey;
	
    @Persistent
    String user;
    
    @Persistent
    String keyword;
    
    @Persistent
    Date inputDate;
    
    @Persistent
    String transactionObjectType;
    
    @Persistent
    ArrayList<String> rdfKeyWords;
    
    @Persistent
    ArrayList<String> keySet;
    
    public TransactionInfo() {
    }

	public TransactionInfo(String user,
			String keyword,
			String transactionObjectType) {
		super();
		rdfKeyWords = new ArrayList<String>();
		this.user = user;
		this.keyword = keyword;
		this.inputDate = new Date();
		this.transactionObjectType = transactionObjectType;
		this.keySet = new ArrayList<String>();
	}
	
	public boolean addRDFKey(String key) {
		rdfKeyWords.add(key);
		boolean max = false;
		if(rdfKeyWords.size() >= maxSize)
			max = true;
		return max;
	}
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getTransactionObjectType() {
		return transactionObjectType;
	}

	public void setTransactionObjectType(String transactionObjectType) {
		this.transactionObjectType = transactionObjectType;
	}

	public ArrayList<String> getRdfKeyWords() {
		return rdfKeyWords;
	}
	public SetOfTransactionKeys replaceKeySet() {
		SetOfTransactionKeys keyset = new SetOfTransactionKeys(rdfKeyWords);
		rdfKeyWords = new ArrayList<String>();
		return keyset;
	}
	public String getStoredObjectKey() {
		return storedObjectKey;
	}

	public void setStoredObjectKey(String storedObjectKey) {
		this.storedObjectKey = storedObjectKey;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void addKeyToKeySet(String key) {
		keySet.add(key);
	}

	public ArrayList<String> getKeySet() {
		return keySet;
	}
	
}
