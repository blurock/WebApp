package info.esblurock.reaction.data.chemical.molecule;

import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class CreateMechanismMoleculeData {
	
	String delimitor = "#";

	String keywordBase;
	String keyword;

	public CreateMechanismMoleculeData(String keywordBase) {
		super();
		this.keywordBase = keywordBase;
	};
	
	public MechanismMoleculeData create(ChemkinMolecule molecule, TransactionInfo transaction) {
		System.out.println("CreateMechanismMoleculeData: base: " + keywordBase + "mol: " + molecule.getLabel());
		keyword = keywordBase + delimitor + molecule.getLabel();
		MechanismMoleculeData data = new MechanismMoleculeData(molecule.getLabel(),keywordBase);		
		return data;
	}

	public String getKeyword() {
		return keyword;
	}
	
}
