package info.esblurock.reaction.data.delete;

import java.io.IOException;

import javax.jdo.PersistenceManager;

import com.ibm.icu.util.StringTokenizer;

import info.esblurock.reaction.data.chemical.mechanism.ChemicalMechanismData;
import info.esblurock.reaction.data.chemical.thermo.SetOfNASAPolynomialData;
import info.esblurock.reaction.data.upload.DeleteTextSetUploadData;
import info.esblurock.reaction.server.datastore.PMF;
import info.esblurock.reaction.server.queries.ChemicalMechanismDataQuery;
import info.esblurock.reaction.server.queries.NASAPolynomialDataQuery;

public enum DeleteDataStructures {

	TextSetUploadData {
		@Override
		public String deleteStructure(String key) throws IOException {
			DeleteTextSetUploadData delete = new DeleteTextSetUploadData();
			String ans = delete.delete(key);
			return ans;
		}
	},
	ChemicalMechanismData {
		@Override
		public String deleteStructure(String key) throws IOException {			
			ChemicalMechanismDataQuery query = new ChemicalMechanismDataQuery();
			query.deleteChemicalMechanismDataFromKey(key);
			return key;
		}
		
	},
	SetOfNASAPolynomialData {

		@Override
		public String deleteStructure(String key) throws IOException {
			NASAPolynomialDataQuery query = new NASAPolynomialDataQuery();
			query.deleteNASAPolynomialDataFromKey(key);
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
		System.out.println("DeleteDataStructure: " + fullclassname);
		String root = findKeyRoot(fullclassname);
		System.out.println("DeleteDataStructure: " + root);
		String ans = valueOf(root).deleteStructure(key);
		return ans;
	}
}
