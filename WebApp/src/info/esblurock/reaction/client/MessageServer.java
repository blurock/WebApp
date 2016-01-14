package info.esblurock.reaction.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("message")

public interface MessageServer extends RemoteService{
	String sendMessage(String message);
}
