package info.esblurock.reaction.server.event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import info.esblurock.reaction.client.ui.login.UserDTO;
import info.esblurock.reaction.data.delete.DeleteTransactionInfoAndObject;
import info.esblurock.reaction.server.authorization.SetOfAuthorizationLevels;
import info.esblurock.reaction.server.datastore.PMF;

public class VerifyServerTransaction {

	static public PersistenceManager pm = PMF.get().getPersistenceManager();
	private static final Logger log = Logger.getLogger(VerifyServerTransaction.class.getName());
	private static SetOfAuthorizationLevels authorization = new SetOfAuthorizationLevels();

	public static ArrayList<String> getPrivledges(String level)
			throws IOException {
		return authorization.getPrivledges(level);
	}
	public static void verify(UserDTO user, String event, String ip,
			String sessionid, String tasktype) throws IOException {
		
		System.out.println("Login verify 1:");
		
		if (authorization.authorize(user.getUserLevel(), tasktype)) {
			log.info("Login verify 2: \t" + tasktype);
			log.info("event=" + event);
			log.info("ip=" + ip + "   \tUserIP= " + user.getIP());
			log.info("sessionid=" + sessionid + " \tUser session=" + user.getSessionId());
			log.info("tasktype=" + tasktype);
			/*
			if (ip.equals(user.getIP())) {
				System.out.println("Login verify 3:" + ip);
				if (user.getSessionId().equals(sessionid)) {
					System.out.println("Login verify 4:" + user.getSessionId());
					SessionEvent sessionevent = new SessionEvent(
							user.getName(), user.getIP(), event);
					System.out.println("Login verify 5:" + event);
					pm.makePersistent(sessionevent);
					System.out.println("Login verify 6: session stored");
				} else {
					String message = "Session id mismatch "
							+ user.getSessionId() + ", " + sessionid;
					System.out.println(message);
				}
			} else {
				String message = "Session IP (" + ip + ") and User IP ("
						+ user.getIP() + ") do not match";
				System.out.println(message);
				throw new Exception(message);
			}
			*/
		} else {
			String message = "Task (" + tasktype
					+ ") Authorization Failure for " + user.getName() + "("
					+ user.getUserLevel() + ")";
			log.log(Level.WARNING,message);
			throw new IOException(message);
		}
	}
}
