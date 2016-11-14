package info.esblurock.reaction.data.transaction;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.store.StoreObject;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

// TODO: Auto-generated Javadoc
/**
 * The Class TransactionInfo. This is the set of {@link KeywordRDF}.
 * If the number of {@link KeywordRDF} exceeds maxSize, 
 * 
 * The array of {@link KeywordRDF} elements are labels as persistent dependent
 * so that they all are stored with the {@link TransactionInfo} is made persistent 
 * (in the finish function of the {@link StoreObject} finish function
 */
@PersistenceCapable
public class TransactionInfo extends DatabaseObject {

	/** The stored object key. */
	@Persistent
	String storedObjectKey;
	
    /** The user who initiated this transaction. */
    @Persistent
    String user;
    
    /** The keyword string representing the object being stored. */
    @Persistent
    String keyword;
    
    /** The date when the transaction occurred. */
    @Persistent
    Date inputDate;
    
    /** The transaction object class name (as a string)**/
    @Persistent
    String transactionObjectType;
    
    @Persistent
    String sourceCode;
    
    /** empty constructor
     * Instantiates a new transaction info.
     */
    public TransactionInfo() {
    }

	/**
	 * Instantiates a new transaction info.
	 *
	 * @param user the user who is creating the transaction
	 * @param keyword the keyword string name of the object being represented in the transaction
	 * @param transactionObjectType the transaction object classname as String
	 */
	public TransactionInfo(String user,
			String keyword,
			String transactionObjectType,
			String sourceCode) {
		super();
		this.user = user;
		this.keyword = keyword;
		this.inputDate = new Date();
		this.transactionObjectType = transactionObjectType;
		this.sourceCode = sourceCode;
	}
	
	/**
	 * Adds the {@link KeywordRDF}.
	 * If the number of {@link KeywordRDF} elements exceeds maxSize,
	 * then they are transfered to a {@link SetOfTransactionRDFs}
	 * and that structure is stored here.
	 * This was done so as to not have too much info in this class
	 *
	 * @param rdf the rdf
	 */
	/**
	 * Gets the username.
	 *
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the user name.
	 *
	 * @param user the new user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * Gets the input date.
	 *
	 * @return the input date
	 */
	public Date getInputDate() {
		return inputDate;
	}

	/**
	 * Gets the transaction object classname.
	 *
	 * @return the transaction object type
	 */
	public String getTransactionObjectType() {
		return transactionObjectType;
	}
	
	/**
	 * Gets the stored object key.
	 *
	 * @return the stored object key
	 */
	public String getStoredObjectKey() {
		return storedObjectKey;
	}
	
	/**
	 * Sets the stored object key.
	 *
	 * @param storedObjectKey the new stored object key
	 */
	public void setStoredObjectKey(String storedObjectKey) {
		this.storedObjectKey = storedObjectKey;
	}
	
	/**
	 * Gets the keyword string of the object of this transaction.
	 *
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}
	public String errorKeyword(String keyword) {
		String errkey = "ERROR: " +  keyword;
		keyword = errkey;
		return keyword;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
}
