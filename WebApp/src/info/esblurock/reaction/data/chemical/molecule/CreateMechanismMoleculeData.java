package info.esblurock.reaction.data.chemical.molecule;

import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class CreateMechanismMoleculeData {
	
	String delimitor = "#";

	String keywordBase;
	TransactionInfo transaction;

	public CreateMechanismMoleculeData(String keywordBase) {
		super();
		this.keywordBase = keywordBase;
		this.transaction = transaction;
	};
	
	public MechanismMoleculeData create(ChemkinMolecule molecule, TransactionInfo transaction) {
		System.out.println("CreateMechanismMoleculeData: base: " + keywordBase + "mol: " + molecule.getLabel());
		String keyword = keywordBase + delimitor + molecule.getLabel();
		MechanismMoleculeData data = new MechanismMoleculeData(molecule.getLabel(),keywordBase);
		
		StoreMechanismMoleculeData store = new StoreMechanismMoleculeData(keyword,data, transaction);
		
		return data;
	}
}
