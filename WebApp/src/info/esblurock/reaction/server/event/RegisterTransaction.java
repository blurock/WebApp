package info.esblurock.reaction.server.event;

import java.io.IOException;

import info.esblurock.reaction.client.ui.login.UserDTO;


public class RegisterTransaction {
	static public int checkLevel0 = 0;
	static public int checkLevel1 = 1;
	static public int checkLevel2 = 2;
	static public int checkLevel3 = 3;

	private static int maxCount = 1000;
	
	
	static public void register(UserDTO user, String event, String eventinfo, int checklevel) throws IOException {
		System.out.println("RegisterTransaction: " + user.getName() + ": " + event + ":" + eventinfo);
		SessionEvent sessionevent = new SessionEvent(
				user.getName(), user.getIP(), event, eventinfo);
		if(checklevel > checkLevel0) {
			TransactionCount count = VerifyServerTransaction.pm.getObjectById(TransactionCount.class, user.getName());
			int c = count.incrementCount();
			System.out.println("RegisterTransaction: count=" + c);
			if(c > maxCount) {
				throw new IOException("Transaction count exceeds maximum: contact administrator");
			}
		}
		VerifyServerTransaction.pm.makePersistent(sessionevent);
	}
}
