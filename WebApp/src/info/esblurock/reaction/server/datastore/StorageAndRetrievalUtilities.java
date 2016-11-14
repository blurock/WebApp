package info.esblurock.reaction.server.datastore;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.server.queries.TransactionInfoQueries;

public class StorageAndRetrievalUtilities {
	private static final Logger log = Logger.getLogger(TransactionInfoQueries.class.getName());
	
	public static void storeDatabaseObjects(ArrayList<DatabaseObject> objects) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
    	pm.makePersistentAll(objects);
    	pm.flush();
    	pm.close();
		
	}
	public static void storeListOfTransactionInfo(ArrayList<TransactionInfo> infos) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
    	pm.makePersistentAll(infos);
    	pm.close();
		
	}

}
