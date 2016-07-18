package info.esblurock.reaction.server.event;

import java.io.IOException;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import info.esblurock.reaction.client.ui.login.UserDTO;
import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.delete.DeleteTransactionInfoAndObject;


public class RegisterTransaction {
	private static final Logger log = Logger.getLogger(RegisterTransaction.class.getName());

	static public int checkLevel0 = 0;
	static public int checkLevel1 = 1;
	static public int checkLevel2 = 2;
	static public int checkLevel3 = 3;

	private static int maxCount = 1000;
	
	
	static public void register(UserDTO user, String event, String eventinfo, int checklevel) throws IOException {
		log.info("RegisterTransaction: " + user.getName() + ": " + event + ":" + eventinfo);
		SessionEvent sessionevent = new SessionEvent(
				user.getName(), user.getIP(), event, eventinfo);
		if(checklevel > checkLevel0) {
			TransactionCount count = VerifyServerTransaction.pm.getObjectById(TransactionCount.class, user.getName());
			int c = count.incrementCount();
			log.info("RegisterTransaction: count=" + c);
			if(c > maxCount) {
				throw new IOException("Transaction count exceeds maximum: contact administrator");
			}
		}
		PersistenceManager pm = PMF.get().getPersistenceManager();
		VerifyServerTransaction.pm.makePersistent(sessionevent);
		pm.close();
	}
}
