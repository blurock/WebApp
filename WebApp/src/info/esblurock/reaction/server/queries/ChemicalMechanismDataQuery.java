package info.esblurock.reaction.server.queries;

import java.io.IOException;
import java.util.ArrayList;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;

import info.esblurock.reaction.data.chemical.elements.ChemicalElementListData;
import info.esblurock.reaction.data.chemical.mechanism.ChemicalMechanismData;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeData;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;
import info.esblurock.reaction.server.datastore.PMF;


public class ChemicalMechanismDataQuery extends QueryBase {

	public ArrayList<Key> elementKeysFromMechanismName(String mechanismName) {
		String mechanismKeyword = "mechanismKeyword";
		ArrayList<Key> keys = getObjectKeysFromSingleProperty(ChemicalElementListData.class,mechanismKeyword,mechanismName);
		return keys;
	}
	public ArrayList<Key> moleculeKeysFromMechanismName(String mechanismName) {
		String mechanismKeyword = "mechanismKeyword";
		ArrayList<Key> keys = getObjectKeysFromSingleProperty(MechanismMoleculeData.class,mechanismKeyword,mechanismName);
		return keys;
	}
	public ArrayList<Key> reactionKeysFromMechanismName(String mechanismName) {
		String mechanismKeyword = "mechanismKeyword";
		ArrayList<Key> keys = getObjectKeysFromSingleProperty(ChemkinReactionData.class,mechanismKeyword,mechanismName);
		return keys;
	}
	public void deleteChemicalMechanismDataFromName(String mechanismName){
		String mechanismKeyword = "mechanismKeyword";
		deleteFromIdentificationCode(ChemicalElementListData.class,mechanismKeyword,mechanismName);
		deleteFromIdentificationCode(MechanismMoleculeData.class,mechanismKeyword,mechanismName);
		deleteFromIdentificationCode(ChemkinReactionData.class,mechanismKeyword,mechanismName);
	}
	
	public void deleteChemicalMechanismDataFromKey(String key)  throws IOException  {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		ChemicalMechanismData mech = pm.getObjectById(ChemicalMechanismData.class, key);
		if(mech != null) {
			deleteChemicalMechanismDataFromName(mech.getMechansmName());

			pm.deletePersistent(mech);
			pm.flush();
			pm.close();
		} else {
			throw new IOException("ChemkinMechanismData deleteStructure fail with key: " + key);
		}
	}
}
