package info.esblurock.reaction.data.chemical.molecule;

import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.reaction.data.CreateData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class CreateMechanismMoleculeData extends CreateData {
	
	static String delimitor = "#";

	String keywordBase;
	String keyword;

	static public String createMoleculeKey(String base, String label) {
		String key = base + delimitor + label;
		return key;
	}
	public CreateMechanismMoleculeData(String keywordBase) {
		super();
		this.keywordBase = keywordBase;
	};
	
	public MechanismMoleculeData create(ChemkinMolecule molecule) {
		keyword = createMoleculeKey(keywordBase,molecule.getLabel() );
		MechanismMoleculeData data = new MechanismMoleculeData(molecule.getLabel(),keywordBase);		
		return data;
	}

	public String getKeyword() {
		return keyword;
	}
	
}
