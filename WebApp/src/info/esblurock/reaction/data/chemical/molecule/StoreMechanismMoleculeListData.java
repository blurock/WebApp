package info.esblurock.reaction.data.chemical.molecule;

import java.util.ArrayList;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;
import info.esblurock.reaction.data.chemical.reaction.MechanismReactionListData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreMechanismMoleculeListData extends StoreObject {

	ArrayList<MechanismMoleculeData> moleculeset;
	
	public StoreMechanismMoleculeListData(String keyword, DatabaseObject object, TransactionInfo transaction,
			boolean storeObject) {
		super(keyword, object, transaction, storeObject);
	}
	
	protected void storeObject() {
		super.storeObject();
	}
	
	protected void storeRDF() {
	}
	
	public void setListOfMechanismMoleculeData(ArrayList<MechanismMoleculeData> set) {
		moleculeset = set;
	}
}
