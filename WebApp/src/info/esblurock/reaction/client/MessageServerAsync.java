package info.esblurock.reaction.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MessageServerAsync {

	void sendMessage(String message, AsyncCallback<String> callback);

}
