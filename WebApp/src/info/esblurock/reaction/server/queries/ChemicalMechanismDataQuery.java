package info.esblurock.reaction.server.queries;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.chemical.elements.ChemicalElementListData;
import info.esblurock.reaction.data.chemical.mechanism.ChemicalMechanismData;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeData;
import info.esblurock.reaction.data.chemical.reaction.ChemkinCoefficientsData;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;
import info.esblurock.reaction.data.chemical.reaction.MechanismReactionListData;
import info.esblurock.reaction.data.chemical.reaction.PhotoChemicalReaction;
import info.esblurock.reaction.data.chemical.thermo.NASAPolynomialData;
import info.esblurock.reaction.data.chemical.transport.SpeciesTransportProperty;


public class ChemicalMechanismDataQuery extends QueryBase {

	static public List<DatabaseObject> elementsFromMechanismName(String mechanismName) throws IOException {
		String mechanismKeyword = "mechanismKeyword";
		String classname = ChemicalElementListData.class.getName();
		return getDatabaseObjectsFromSingleProperty(classname, mechanismKeyword, mechanismName);
	}
	static public List<DatabaseObject> moleculesFromMechanismName(String mechanismName) throws IOException {
		String mechanismKeyword = "mechanismKeyword";
		String classname = MechanismMoleculeData.class.getName();
		return getDatabaseObjectsFromSingleProperty(classname, mechanismKeyword, mechanismName);
	}
	static public List<DatabaseObject> photoReactionsFromMechanismName(String mechanismName) throws IOException {
		String mechanismKeyword = "mechanismKeyword";
		String classname = PhotoChemicalReaction.class.getName();
		return getDatabaseObjectsFromSingleProperty(classname, mechanismKeyword, mechanismName);
	}
	static public List<DatabaseObject> reactionsFromMechanismName(String mechanismName) throws IOException {
		String mechanismKeyword = "mechanismKeyword";
		String classname = ChemkinReactionData.class.getName();
		return getDatabaseObjectsFromSingleProperty(classname, mechanismKeyword, mechanismName);
	}
	static public List<DatabaseObject> coefficientsFromMechanismName(String mechanismName) throws IOException {
		String mechanismKeyword = "mechanismKeyword";
		String classname = ChemkinCoefficientsData.class.getName();
		return getDatabaseObjectsFromSingleProperty(classname, mechanismKeyword, mechanismName);
	}
	static public List<DatabaseObject> coefficientsFromReactionName(String reactionName) throws IOException {
		String reactionKeyword = "reactionKeyword";
		String classname = ChemkinCoefficientsData.class.getName();
		return getDatabaseObjectsFromSingleProperty(classname, reactionKeyword, reactionName);
	}
	static public List<DatabaseObject> nasaPolynomialsFromMechanismName(String mechanismName) throws IOException {
		String mechanismKeyword = "mechanismKeyword";
		String classname = NASAPolynomialData.class.getName();
		return getDatabaseObjectsFromSingleProperty(classname, mechanismKeyword, mechanismName);
	}
	static public List<DatabaseObject> transportPropertiesFromMechanismName(String mechanismName) throws IOException {
		String mechanismKeyword = "mechanismKeyword";
		String classname = SpeciesTransportProperty.class.getName();
		return getDatabaseObjectsFromSingleProperty(classname, mechanismKeyword, mechanismName);
	}
	/*
	static public ArrayList<Key> elementKeysFromMechanismName(String mechanismName) {
		String mechanismKeyword = "mechanismKeyword";
		ArrayList<Key> keys = getObjectKeysFromSingleProperty(ChemicalElementListData.class,mechanismKeyword,mechanismName);
		return keys;
	}
	static public ArrayList<Key> moleculeKeysFromMechanismName(String mechanismName) {
		String mechanismKeyword = "mechanismKeyword";
		ArrayList<Key> keys = getObjectKeysFromSingleProperty(MechanismMoleculeData.class,mechanismKeyword,mechanismName);
		return keys;
	}
	static public ArrayList<Key> reactionKeysFromMechanismName(String mechanismName) {
		String mechanismKeyword = "mechanismKeyword";
		ArrayList<Key> keys = getObjectKeysFromSingleProperty(ChemkinReactionData.class,mechanismKeyword,mechanismName);
		return keys;
	}
	*/
	static public void deleteChemicalMechanismDataFromName(String mechanismName) {
		String mechanismKeyword = "mechanismKeyword";
		deleteFromIdentificationCode(ChemicalElementListData.class,mechanismKeyword,mechanismName);
		deleteFromIdentificationCode(MechanismMoleculeData.class,mechanismKeyword,mechanismName);
		deleteFromIdentificationCode(ChemkinReactionData.class,mechanismKeyword,mechanismName);
		deleteFromIdentificationCode(MechanismReactionListData.class,mechanismKeyword,mechanismName);
		//deleteFromIdentificationCode(MechanismMoleculeListData.class,mechanismKeyword,mechanismName);
	}
	
	static public void deleteChemicalMechanismDataFromKey(String key)  throws IOException  {
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
