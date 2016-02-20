package info.esblurock.reaction.data.chemical.molecule;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreMechanismMoleculeData  extends StoreObject {
	
	
	static public String mechanismMolecule = "MechanismMolecule";
	static public String speciesName = "SpeciesName";
	static public String speciesMechDelimitor = "#";

	public StoreMechanismMoleculeData(String keyword, DatabaseObject object, TransactionInfo transaction) {
		super(keyword, object, transaction);
	}
	protected void storeObject() {
		super.storeObject();
	}
	
	protected void storeRDF() {
		MechanismMoleculeData molecule = (MechanismMoleculeData) object;
		storeObjectRDF(molecule);
		storeStringRDF(mechanismMolecule,molecule.getMechanismKeyword());
		storeStringRDF(speciesName,molecule.getMoleculeName());
	}
}
