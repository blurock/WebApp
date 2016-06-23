package info.esblurock.reaction.data.delete;

import java.io.IOException;

import java.util.logging.Logger;
import javax.jdo.PersistenceManager;

import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.transaction.ActionsUsingIdentificationCode;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.server.datastore.PMF;
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
		String ans = "";
		System.out.println("deleteTransactionInfoFromKey: " + fullkey);
		TransactionInfo info = TransactionInfoQueries.getTransactionFromKeyString(fullkey);
		if(info != null)
			System.out.println("deleteTransactionInfoFromKey - TransactionInfo: " + info.getKeyword());
		else
			System.out.println("deleteTransactionInfoFromKey - Info not found");
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
		System.out.println("delete: transaction.getStoredObjectKey(): " + transaction.getStoredObjectKey());
		if (transaction.getStoredObjectKey() != null) {
			String fullclassname = transaction.getTransactionObjectType();
			String key = transaction.getStoredObjectKey();
			System.out.println("Delete: " + fullclassname + "  " + key);
			DeleteDataStructures.deleteObject(fullclassname, key);
			log.info("Done deleting object");
		}
		log.info("Delete TransactionInfo: " + transaction.getKeyword());
		ActionsUsingIdentificationCode.deleteFromIdentificationCode(KeywordRDF.class, transaction.getSourceCode());

		try {
			log.info("PMF.get(): " + transaction.getKeyword());
			PersistenceManager pm = PMF.get().getPersistenceManager();
			pm.flush();
			log.info("pm.getObjectById: " + transaction.getKeyword() + "\n" + transaction.getKey());
			transaction = pm.getObjectById(TransactionInfo.class, transaction.getKey());
			log.info("deletePersistent: " + transaction.getKeyword());
			pm.deletePersistent(transaction);
			pm.close();
			log.info("Done Delete TransactionInfo: ");
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

	/*
	 * private boolean deleteSetOfTransactionKeys(SetOfTransactionRDFs
	 * transkeys, StringBuilder build) { boolean error =
	 * deleteArrayOfKeys(transkeys.getRdfKeyWords(),build);
	 * pm.deletePersistent(transkeys); return error; } private boolean
	 * deleteArrayOfKeys(ArrayList<String> keys, StringBuilder build) { boolean
	 * error = false; for(String key : keys) { try { System.out.println(
	 * "Delete RDF: " + key); KeywordRDF rdf =
	 * pm.getObjectById(KeywordRDF.class, key); pm.deletePersistent(rdf); }
	 * catch (JDOObjectNotFoundException ex) { error = true; build.append(
	 * "SetOfTransactionKeys not found: " + key + "\n"); } } return error; }
	 */
	/*
	 * StringBuilder build = new StringBuilder(); build.append(
	 * "ERROR:  TransactionInfo: \n"); boolean error =
	 * deleteArrayOfKeys(transaction.getRdfKeyWords(), build); for (String key :
	 * transaction.getKeySet()) { try { SetOfTransactionRDFs keys =
	 * pm.getObjectById(SetOfTransactionRDFs.class, key); boolean e =
	 * deleteSetOfTransactionKeys(keys,build); error = error || e; } catch
	 * (JDOObjectNotFoundException ex) { error = true; build.append(
	 * "SetOfTransactionKeys not found: " + key + "\n"); } } System.out.println(
	 * "Done Deleting RDF");
	 * 
	 * 
	 * if(error) { throw new IOException(build.toString()); }
	 */

}
