package info.esblurock.reaction.data.chemical.molecule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.react.mechanisms.chemkin.ChemkinMoleculeList;
import info.esblurock.reaction.data.CreateData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class CreateMechanismMoleculeListData extends CreateData {

	String keywordBase;
	CreateMechanismMoleculeData createMolecule;
	HashMap<String, String> moleculeMap;

	public CreateMechanismMoleculeListData(String keywordBase) {
		this.keywordBase = keywordBase;
		createMolecule = new CreateMechanismMoleculeData(keywordBase);
		moleculeMap = new HashMap<String, String>();
	}

	public MechanismMoleculeListData create(ChemkinMoleculeList speciesList) {
		ArrayList<MechanismMoleculeData> molecules = new ArrayList<MechanismMoleculeData>();
		Set<String> keys = speciesList.keySet();
		for (String key : keys) {
			ChemkinMolecule molecule = speciesList.get(key);
			MechanismMoleculeData mol = createMolecule.create(molecule);
			molecules.add(mol);
			String molname = CreateMechanismMoleculeData.createMoleculeKey(mol.getMechanismKeyword(), mol.getMoleculeName());
			moleculeMap.put(mol.getMoleculeName(), molname);			
		}
		MechanismMoleculeListData mollistdata = new MechanismMoleculeListData(molecules);
		return mollistdata;
	}
	
	public void create(MechanismMoleculeListData mechmollist, TransactionInfo transaction) {
		System.out.println("MechanismMoleculeData mol:" + mechmollist);
		for (MechanismMoleculeData mol : mechmollist.getMolecules()) {
			String keyword = CreateMechanismMoleculeData.createMoleculeKey(keywordBase,mol.getMoleculeName() );
			StoreMechanismMoleculeData store 
				= new StoreMechanismMoleculeData(keyword, mol,transaction, false);
			addStore(store);
		}
		this.merge(createMolecule);
	}

	public HashMap<String, String> getMoleculeMap() {
		return moleculeMap;
	}

}
