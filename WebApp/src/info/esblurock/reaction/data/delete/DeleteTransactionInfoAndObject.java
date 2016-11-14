package info.esblurock.reaction.data.delete;

import java.io.IOException;

import java.util.logging.Logger;
import javax.jdo.PersistenceManager;

import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.transaction.ActionsUsingIdentificationCode;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.server.queries.QueryBase;
import info.esblurock.reaction.server.queries.TransactionInfoQueries;

/**
 * The Class DeleteTransactionInfo.
 * 
 * This is the class that is used to delete all associated objects from an
 * object: This includes:
 * <ul>
 * <li>The associated {@link TransactionInfo} and the associated RDF info.
 * <li>The objects contained within the object (through
 * {@link DeleteDataStructures})
 * <li>The object itself
 * </ul>
 */
public class DeleteTransactionInfoAndObject {
	/** The persistence manager. */
	private static final Logger log = Logger.getLogger(DeleteTransactionInfoAndObject.class.getName());

	/**
	 * deleteFromObjectKey
	 * 
	 * This is the delete routine that should be called by all objects to ensure
	 * that all associated objects are deleted.
	 * 
	 * Delete transaction info and the object from object key
	 * 
	 * The {@link TransactionInfo} is found using the object key
	 * (getTransactionFromObjectKey) and then the {@link TransactionInfo} is
	 * delete (which also deletes the object.
	 *
	 * @param key the key of the object to be deleted @return the string from
	 * the delete @throws IOException Signals that an I/O exception has
	 * occurred. @throws
	 */
	public String deleteFromTypeAndKeyword(String objectType, String keyword) throws IOException {
		TransactionInfo info = TransactionInfoQueries.getFirstTransactionFromKeywordAndObjectType(keyword, objectType);
		String ans = this.delete(info);
		return ans;
	}

	/**
	 * deleteFromObjectKey
	 * 
	 * This is the delete routine that should be called by all objects to ensure
	 * that all associated objects are deleted.
	 * 
	 * Delete transaction info and the object from object key
	 * 
	 * The {@link TransactionInfo} is found using the object key
	 * (getTransactionFromObjectKey) and then the {@link TransactionInfo} is
	 * delete (which also deletes the object.
	 *
	 * @param key the key of the object to be deleted @return the string from
	 * the delete @throws IOException Signals that an I/O exception has
	 * occurred. @throws
	 */
	public String deleteFromObjectKey(String key) throws IOException {
		TransactionInfo info = TransactionInfoQueries.getFirstTransactionFromObjectKey(key);
		String ans = this.delete(info);
		return ans;
	}

	/**
	 * Delete the {@link TransactionInfo} from its key.
	 * 
	 * @param fullkey
	 *            the key to the {@link TransactionInfo} from the database
	 * @return the string showing the result of the delete
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String deleteTransactionInfoFromKey(String fullkey) throws IOException {
		String ans = "Deleted Transaction";
		System.out.println("deleteTransactionInfoFromKey: " + fullkey);
		TransactionInfo info = TransactionInfoQueries.getTransactionFromKeyString(fullkey);
		if(info != null) {
			ans = "TransactionInfo: deleted";
			System.out.println("deleteTransactionInfoFromKey - TransactionInfo: " + info.getKeyword());
		} else {
			ans = "TransactionInfo: not found";
			System.out.println("deleteTransactionInfoFromKey - Info not found");
		}
		delete(info);
		return ans;
	}

	/**
	 * Delete the {@link TransactionInfo} and its associated objects
	 * 
	 * this deletes:
	 * <ul>
	 * <li>the list of RDF classes
	 * <li>the associated object (by calling its delete routine
	 * <li>The TransactionInfo itself
	 * </ul>
	 * 
	 * The object is delete by finding the object with the object key
	 * 
	 * <ul>
	 * <li>the getTransactionObjectType() of {@link TransactionInfo} is called
	 * to get the string name of the object
	 * <li>This is used to find the delete routine in
	 * {@link DeleteDataStructures} class.
	 * <li>The delete routine is called to delete the object
	 * </ul>
	 *
	 * @param transaction
	 *            the transaction
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	private String delete(TransactionInfo transaction) throws IOException {
		String transactionKey = transaction.getKey();
		if (transaction.getStoredObjectKey() != null) {
			String fullclassname = transaction.getTransactionObjectType();
			String key = transaction.getStoredObjectKey();
			DeleteDataStructures.deleteObject(fullclassname, key);
		}
		log.info("Delete TransactionInfo: " + transaction.getKeyword());
		ActionsUsingIdentificationCode.deleteFromIdentificationCode(KeywordRDF.class, transaction.getSourceCode());

		try {
			QueryBase.deleteWithStringKey(TransactionInfo.class, transaction.getKey());
		} catch (Exception ex) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			pm.close();
			throw new IOException("TransactionInfo:\n " + ex.toString());
		}
		String ans = "SUCCESS: ";
		return ans;
	}

	/**
	 * getTransaction From the object key find the TransactionInfo (from
	 * storedObjectKey).
	 *
	 * @param key
	 *            the key of the object
	 * @return the associated transaction
	 * @throws IOException
	 */
	private TransactionInfo getTransactionFromObjectKey(String key) throws IOException {
		return TransactionInfoQueries.getFirstTransactionFromObjectKey(key);
	}
}
