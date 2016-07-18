package info.esblurock.reaction.data.delete;

import java.io.IOException;

import java.util.StringTokenizer;

// TODO: Auto-generated Javadoc
/**
 * The Class DeleteStructuresBase.
 * 
 * This is the base class of the delete routines. 
 * the delete is called by the associated transaction
 * (so the delete does not delete the transaction).
 * 
 */
public class DeleteStructuresBase {
	
	/** The root of the full name with package. */
	protected String root;
	
	/** delete
	 * This method will delete the object and its sub-objects
	 * (this method should be called by the 
	 *
	 * @param fullkey The key of the object
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String delete(String fullkey) throws IOException {
		root = findKeyRoot(fullkey);
		return root;
	}

	/**
	 * Find key root.
	 *
	 * @param key the key
	 * @return the string
	 */
	protected String findKeyRoot(String key) {
		StringTokenizer tok = new StringTokenizer(key, ".");
		String ans = "";
		while (tok.hasMoreTokens()) {
			ans = tok.nextToken();
		}
		return ans;
	}

}
