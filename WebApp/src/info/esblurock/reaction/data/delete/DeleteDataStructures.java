package info.esblurock.reaction.data.delete;

import java.io.IOException;

import javax.jdo.PersistenceManager;

import com.ibm.icu.util.StringTokenizer;

import info.esblurock.reaction.data.chemical.mechanism.ChemicalMechanismData;
import info.esblurock.reaction.data.upload.DeleteTextSetUploadData;
import info.esblurock.reaction.server.datastore.PMF;

public enum DeleteDataStructures {

	TextSetUploadData {
		@Override
		public String deleteStructure(String key) throws IOException {
			DeleteTextSetUploadData delete = new DeleteTextSetUploadData();
			String ans = delete.delete(key);
			return ans;
		}
	},
	ChemkinMechanism {
		@Override
		public String deleteStructure(String key) throws IOException {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			ChemicalMechanismData mech = pm.getObjectById(ChemicalMechanismData.class, key);
			if(mech != null) {
				pm.deletePersistent(mech);
			} else {
				throw new IOException("ChemkinMechanismData deleteStructure fail with key: " + key);
			}
			return key;
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