package info.esblurock.reaction.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.data.description.DescriptionDataData;

public interface ReactionProcessUploadedLinesAsync {

	void processUploadedMechanism(DescriptionDataData description, String key, String filename, boolean process, AsyncCallback<String> callback);

}
