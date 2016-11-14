package info.esblurock.reaction.server.utilities;

import javax.jdo.PersistenceManager;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class WriteObjectTransactionToDatabase {
/**
 * The data object ({@link DatabaseObject}) and its associated {@link TransactionInfo} is stored.
 * 
 * @param user The creator of the data
 * @param keyword The keyword representing the dataset
 * @param idCode The id associated with the data item
 * @param object The transaction data object to be stored
 */
	static public void writeObjectWithTransaction(String user, String keyword, String idCode, DatabaseObject object) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String classname = object.getClass().getName();
		TransactionInfo transaction = new TransactionInfo(user,keyword,classname,idCode);
		pm.makePersistent(object);
		transaction.setStoredObjectKey(object.getKey());
		pm.makePersistent(transaction);
		pm.flush();
		pm.close();
	}
	static public void writeObjectWithoutTransaction(DatabaseObject object) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(object);
		pm.flush();
		pm.close();
		
	}
}
