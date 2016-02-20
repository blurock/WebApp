package info.esblurock.reaction.data.chemical.molecule;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.react.mechanisms.chemkin.ChemkinMoleculeList;
import info.esblurock.reaction.data.transaction.TransactionInfo;


public class CreateMechanismMoleculeListData {
	
	String keywordBase;
	CreateMechanismMoleculeData createMolecule;
	HashMap<String,String> moleculeMap;
	
	
	public CreateMechanismMoleculeListData(String keywordBase) {
		this.keywordBase = keywordBase;
		createMolecule = new CreateMechanismMoleculeData(keywordBase);
		moleculeMap = new HashMap<String,String>();
	}
	
	public MechanismMoleculeListData create(ChemkinMoleculeList speciesList, TransactionInfo transaction) {
		System.out.println("CreateMechanismMoleculeListData: " + keywordBase);
		ArrayList<MechanismMoleculeData> molecules = new ArrayList<MechanismMoleculeData>();
		Set<String> keys = speciesList.keySet();
		for(String key : keys) {
			ChemkinMolecule molecule = speciesList.get(key);
			MechanismMoleculeData mol = createMolecule.create(molecule,transaction);
			moleculeMap.put(mol.getMoleculeName(), mol.getMechanismKeyword());
			molecules.add(mol);
		}
		MechanismMoleculeListData mollistdata = new MechanismMoleculeListData(molecules);
		return mollistdata;	
	}

	public HashMap<String, String> getMoleculeMap() {
		return moleculeMap;
	}

}
