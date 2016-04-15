package info.esblurock.reaction.server;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.jdo.PersistenceManager;


import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.server.datastore.PMF;
import info.esblurock.reaction.server.queries.TransactionInfoQueries;

public class HandleTransactions {
	private static final Logger log = Logger.getLogger(HandleTransactions.class.getName());

	static public void exception(String key, Exception ex, TransactionInfo transaction) throws IOException {
		String message = "Exception: ";
		if (transaction != null) {
			if (transaction.getKey() == null) {
				String newKey = transaction.errorKeyword(transaction.getKeyword());
				message += "Saving Unfinished Transaction: (Key='" + newKey + "') due to exception:\n " + ex.toString();
				log.log(Level.WARNING,message);
				PersistenceManager pm = PMF.get().getPersistenceManager();
				pm.makePersistent(transaction);
			} else {
				message += ex.toString();
				log.log(Level.WARNING,message);
			}
		} else {
			message += ex.toString();
		}
		if (ex instanceof IOException) {
			log.log(Level.WARNING,message + "      \n" + ex.toString());
			IOException ioex = (IOException) ex;
			throw ioex;
		} else {
			log.log(Level.WARNING,message + "      \n" + ex.toString());
			throw new IOException(message);
		}

	}

	static public void transactionExists(String key, String classname) throws IOException {
		TransactionInfoQueries.transactionExists(key, classname);
	}
}
