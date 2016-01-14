package info.esblurock.reaction.data.transaction;

import info.esblurock.reaction.client.data.DatabaseObject;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class TransactionInfo extends DatabaseObject {

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
	}
	
	public void addRDFKey(String key) {
		rdfKeyWords.add(key);
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

}
