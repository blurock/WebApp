package info.esblurock.reaction.data.delete;

import java.io.IOException;

import com.ibm.icu.util.StringTokenizer;

import info.esblurock.reaction.data.upload.DeleteTextSetUploadData;

public enum DeleteDataStructures {

	TextSetUploadData {
		@Override
		public String deleteStructure(String key) throws IOException {
			DeleteTextSetUploadData delete = new DeleteTextSetUploadData();
			String ans = delete.delete(key);
			return ans;
		}
	};

	public abstract String deleteStructure(String key) throws IOException;
	
	/**
	 * Find key root.
	 *
	 * @param key the key
	 * @return the string
	 */
	protected static String findKeyRoot(String key) {
		StringTokenizer tok = new StringTokenizer(key, ".");
		String ans = "";
		while (tok.hasMoreTokens()) {
			ans = tok.nextToken();
		}
		return ans;
	}
	
	public static String deleteObject(String fullclassname, String key) throws IOException {
		String root = findKeyRoot(fullclassname);
		String ans = valueOf(root).deleteStructure(key);
		return ans;
	}
}
