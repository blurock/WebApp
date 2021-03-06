package info.esblurock.reaction.server;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.client.ui.login.UserDTO;
import info.esblurock.reaction.server.event.VerifyServerTransaction;
import info.esblurock.reaction.server.utilities.ContextAndSessionUtilities;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ServerBase  extends RemoteServiceServlet {

	private static final long serialVersionUID = 1L;
	
	public static final String googleAPIKey = "AIzaSyAM533K4aia4ObLQLsix476xrQ1YBqpdVo";
	
	protected ContextAndSessionUtilities getUtilities() {
		HttpSession session = getThreadLocalRequest().getSession();
		ContextAndSessionUtilities util 
			= new ContextAndSessionUtilities(getServletContext(), session);
		return util;
	}
	
	protected void verify(String task, String tasktype) throws IOException {
		ContextAndSessionUtilities util = getUtilities();
		String sessionid = util.getId();
		String ip = this.getThreadLocalRequest().getLocalAddr();
		UserDTO user = util.getUserInfo();
		VerifyServerTransaction.verify(user, task, ip, sessionid, tasktype);
	}

	protected ArrayList<String> getPrivledges(String level) throws IOException {
		return VerifyServerTransaction.getPrivledges(level);
	}

	public String fileAsInput(String className, String sourceType, String textName, String text, String user,
			String keyword) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
