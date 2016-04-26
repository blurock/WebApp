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
	ArrayList<MechanismMoleculeData> moleculeList;
	
	public CreateMechanismMoleculeListData(String keywordBase) {
		this.keywordBase = keywordBase;
		createMolecule = new CreateMechanismMoleculeData(keywordBase);
		moleculeMap = new HashMap<String, String>();
	}

	public MechanismMoleculeListData create(ChemkinMoleculeList speciesList) {
		moleculeList = new ArrayList<MechanismMoleculeData>();
		Set<String> keys = speciesList.keySet();
		for (String key : keys) {
			ChemkinMolecule molecule = speciesList.get(key);
			MechanismMoleculeData mol = createMolecule.create(molecule);
			moleculeList.add(mol);
			String molname = CreateMechanismMoleculeData.createMoleculeKey(mol.getMechanismKeyword(), mol.getMoleculeName());
			moleculeMap.put(mol.getMoleculeName(), molname);			
		}
		MechanismMoleculeListData mollistdata = new MechanismMoleculeListData(keywordBase, moleculeList.size());
		return mollistdata;
	}
	
	public void create(MechanismMoleculeListData mechmollist, TransactionInfo transaction) {
		System.out.println("MechanismMoleculeData mol:" + mechmollist);
		
		StoreMechanismMoleculeListData storelist = new StoreMechanismMoleculeListData(keywordBase,mechmollist,transaction,true);
		storelist.finish();
		
		for (MechanismMoleculeData mol : moleculeList) {
			String keyword = CreateMechanismMoleculeData.createMoleculeKey(keywordBase,mol.getMoleculeName() );
			StoreMechanismMoleculeData store 
				= new StoreMechanismMoleculeData(keyword, mol,transaction, true);
			addStore(store);
		}
		flushCreate();
		for(MechanismMoleculeData data : moleculeList) {
			String keyword = CreateMechanismMoleculeData.createMoleculeKey(keywordBase,data.getMoleculeName() );
			System.out.println("keyword='" + keyword + "', Key='" + data.getKey() + "'");
			storelist.storeObjectRDF(keyword, data);
		}
		addStore(storelist);
	}

	public HashMap<String, String> getMoleculeMap() {
		return moleculeMap;
	}

}
