package info.esblurock.reaction.data.chemical.molecule;

import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.reaction.data.CreateData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class CreateMechanismMoleculeData extends CreateData {
	
	static String delimitor = "#";

	String keywordBase;
	String keyword;
	String user;

	static public String createMoleculeKey(String base, String label) {
		String key = base + delimitor + label;
		return key;
	}
	public CreateMechanismMoleculeData(String user, String keywordBase) {
		super();
		this.keywordBase = keywordBase;
		this.user = user;
	};
	
	public MechanismMoleculeData create(ChemkinMolecule molecule) {
		keyword = createMoleculeKey(keywordBase,molecule.getLabel() );
		MechanismMoleculeData data = new MechanismMoleculeData(user, molecule.getLabel(),keywordBase);		
		return data;
	}

	public String getKeyword() {
		return keyword;
	}
	
}
