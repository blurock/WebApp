package info.esblurock.reaction.server;

import info.esblurock.reaction.client.MessageServer;
import info.esblurock.reaction.server.datastore.WriteSimpleMessage;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MessageServerImpl extends RemoteServiceServlet implements
		MessageServer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String sendMessage(String message) {
		
		WriteSimpleMessage wm = new WriteSimpleMessage();
		wm.write(message);
		
		return message;
	}

}
