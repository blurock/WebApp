package info.esblurock.reaction.data.chemical.mechanism;

import java.util.ArrayList;
import java.util.HashMap;

import javax.jdo.PersistenceManager;

import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.reaction.data.CreateData;
import info.esblurock.reaction.data.chemical.elements.ChemicalElementListData;
import info.esblurock.reaction.data.chemical.elements.CreateChemicalElementListData;
import info.esblurock.reaction.data.chemical.molecule.CreateMechanismMoleculeListData;
import info.esblurock.reaction.data.chemical.molecule.GenerateMoleculeKeywords;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeListData;
import info.esblurock.reaction.data.chemical.reaction.CreateMechanismReactionListData;
import info.esblurock.reaction.data.chemical.reaction.MechanismReactionListData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.server.datastore.PMF;

public class CreateChemicalMechanismData extends CreateData {
	
	public static String delimitor = "#";
	
	GenerateMoleculeKeywords generateKeywords;
	HashMap<String,String> moleculeNamesTable;
	String keywordBase;
	
	CreateChemicalElementListData createElementList;
	CreateMechanismMoleculeListData createMoleculeList;
	CreateMechanismReactionListData createReactionList;
	StoreChemkinMechanismData storeMechanism;
	ChemicalMechanismData mechanismData;
	
	ArrayList<String> reactionKeywords;
	
	static public String createMechanismName(String source,String keyword) {
		String name = source + delimitor + keyword;
		return name;
	}
	static public String sourceFromMechanismName(String mechname) {
		int pos = mechname.indexOf(delimitor);
		String ans = "";
		if(pos > 0) {
			ans = mechname.substring(0, pos);
		}
		return ans;
	}
	static public String keywordFromMechanismName(String mechname) {
		int pos = mechname.indexOf(delimitor);
		String ans = "";
		if(pos > 0) {
			ans = mechname.substring(pos+1);
		}
		return ans;
	}
	
	public CreateChemicalMechanismData(String keywordBase) {
		this.keywordBase = keywordBase;
	}
	
	public ChemicalMechanismData create(ChemkinMechanism mechanism) {
		createElementList = new CreateChemicalElementListData();
		ChemicalElementListData   elementList = createElementList.create(mechanism.getElementList());
		
		createMoleculeList = new CreateMechanismMoleculeListData(keywordBase);
		MechanismMoleculeListData moleculeList = createMoleculeList.create(mechanism.getSpeciesList());
		moleculeNamesTable = createMoleculeList.getMoleculeMap();

		createReactionList = new CreateMechanismReactionListData(keywordBase,moleculeNamesTable,false);
		MechanismReactionListData reactionList = createReactionList.create(mechanism.getReactionList());
		reactionKeywords = createReactionList.getKeywords(mechanism.getReactionList());
	
		ChemicalMechanismData mechanismData = new ChemicalMechanismData(elementList,moleculeList,reactionList);
		
		return mechanismData;
	}
	
	
	public void create(ChemicalMechanismData mechanismData, TransactionInfo transaction) {
		storeMechanism = new StoreChemkinMechanismData(keywordBase,mechanismData,transaction,true);
		storeMechanism.finish();

		transaction.setStoredObjectKey(mechanismData.getKey());
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(transaction);
		String Key = transaction.getKey();
		storeMechanism.setTransactionKey(Key);
		pm.flush();
		pm.close();

		this.merge(createElementList);
		System.out.println("createMoleculeList.create(mechanismData.getMoleculeList(),transaction);");
		createMoleculeList.create(mechanismData.getMoleculeList(),transaction);
		this.merge(createMoleculeList);		
		System.out.println("createReactionList.create(reactionKeywords, mechanismData.getReactionList(), transaction);");
		createReactionList.create(reactionKeywords, mechanismData.getReactionList(), transaction);
		System.out.println("this.flushCreate();");
		this.flushCreate();
		System.out.println("this.merge(createReactionList);	");
		this.merge(createReactionList);	
		System.out.println("this.flushCreate();");
		this.flushCreate();
		System.out.println("DONE");
	}

	public void finish() {
		storeMechanism.finish();
	}



}
