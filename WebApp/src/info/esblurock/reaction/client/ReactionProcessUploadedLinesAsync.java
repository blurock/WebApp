package info.esblurock.reaction.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.data.description.DescriptionDataData;

// TODO: Auto-generated Javadoc
/**
 * The Interface ReactionProcessUploadedLinesAsync.
 */
public interface ReactionProcessUploadedLinesAsync {

	/**
	 * Process uploaded mechanism.
	 *
	 * @param description the description
	 * @param key the key
	 * @param filename the filename
	 * @param process the process
	 * @param callback the callback
	 */
	void processUploadedMechanism(DescriptionDataData description, String key, String filename, boolean process, AsyncCallback<String> callback);

	/**
	 * Process uploaded set of nasa polynomial.
	 *
	 * @param description the description
	 * @param key the key
	 * @param filename the filename
	 * @param process the process
	 * @param callback the callback
	 */
	void processUploadedSetOfNASAPolynomial(DescriptionDataData description, String key, String filename,
			boolean process, AsyncCallback<String> callback);
	/**
	 * Delete text set upload data.
	 *
	 * @param key the key
	 * @param callback the callback
	 */
	void deleteTextSetUploadData(String key, AsyncCallback<String> callback);


}
