package info.esblurock.reaction.client.panel.transaction;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.TextSetUploadData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The Interface TransactionService.
 */
@RemoteServiceRelativePath("transactionservice")
public interface TransactionService extends RemoteService {

	/**
	 * The Class Util.
	 */
	public static class Util {

		/** The instance. */
		private static TransactionServiceAsync instance;

		/**
		 * Gets the single instance of Util.
		 *
		 * @return single instance of Util
		 */
		public static TransactionServiceAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(TransactionService.class);
			}
			return instance;
		}
	}

	/**
	 * Gets the all transactions.
	 *
	 * @return the all transactions
	 */
	List<TransactionInfo> getAllTransactions();

	/**
	 * Gets the all upload transactions.
	 *
	 * @return the all upload transactions
	 */
	List<TextSetUploadData> getAllUploadTransactions();

	/**
	 * Gets the transactions.
	 *
	 * @param user
	 *            the user
	 * @param keyword
	 *            the keyword
	 * @param objecttype
	 *            the objecttype
	 * @return the transactions
	 */
	List<TransactionInfo> getTransactions(String user, String keyword, String objecttype);

	String deleteTransactionInfoFromKey(String objectType, String keyword, String transactionkey) throws IOException;

	/**
	 * Removes the transaction with type and keyword.
	 * 
	 * The object type and the keyword are used to search for the proper
	 * {@link TransactionInfo}
	 *
	 * @param objecttype
	 *            the object type (as in the {@link TransactionInfo})
	 * @param keyword
	 *            the keyword of the object
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	String removeTransactionWithTypeAndKeyword(String objecttype, String keyword) throws Exception;

	/**
	 * Removes the associated {@link TransactionInfo} associated with the object
	 * given by the key The object (and it associated subobject, if any, are
	 * deleted).
	 *
	 * @param key
	 *            The key of the object
	 * @return the output of the deletion
	 * @throws Exception
	 *             the exception
	 */
	String removeTransactionOfObject(String key) throws Exception;

	/**
	 * Gets the user set.
	 *
	 * @return the user set
	 */
	Set<String> getUserSet();

	/**
	 * Gets the keyword set.
	 *
	 * @return the keyword set
	 */
	Set<String> getKeywordSet();

	/**
	 * Gets the object type set.
	 *
	 * @return the object type set
	 */
	Set<String> getObjectTypeSet();

	/**
	 * Removes KeywordRDF entries from specified date
	 *
	 * @param date
	 *            The date from which and including to delete
	 * @return the result
	 */
	public String removeFromRDFsFromDate(Date date);
/**
 * 
 * @param keyword: The keyword representing 
 * @return The names of the processes that have prerequisites to run
 * @throws IOException
 */
	public Set<String> findValidProcessing(String keyword) throws IOException;
	
	
	public String runProcess(String processName,String keyword) throws IOException;

}
