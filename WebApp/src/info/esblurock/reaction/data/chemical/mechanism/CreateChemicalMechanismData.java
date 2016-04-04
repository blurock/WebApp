package info.esblurock.reaction.data.chemical.mechanism;

import java.util.HashMap;

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

public class CreateChemicalMechanismData extends CreateData {
	
	public static String delimitor = "#";
	
	GenerateMoleculeKeywords generateKeywords;
	String mechanismKeyword;
	HashMap<String,String> moleculeNamesTable;
	String keywordBase;
	
	CreateMechanismMoleculeListData createMoleculeList;
	CreateMechanismReactionListData createReactionList;
	StoreChemkinMechanismData storeMechanism;
	ChemicalMechanismData mechanismData;
	
	static public String createMechanismName(String source,String keyword) {
		String name = source + delimitor + keyword;
		return name;
	}
	
	public CreateChemicalMechanismData(String keywordBase) {
		this.keywordBase = keywordBase;
	}
	
	public ChemicalMechanismData create(String mechanismKeyword, ChemkinMechanism mechanism, TransactionInfo transaction) {
		this.mechanismKeyword = mechanismKeyword;
		System.out.println("CreateChemicalMechanismData Create/Store: ChemicalElementListData");
		CreateChemicalElementListData createElementList = new CreateChemicalElementListData();
		ChemicalElementListData   elementList = createElementList.create(mechanism.getElementList());
		this.merge(createElementList);
		
		System.out.println("CreateChemicalMechanismData Create/Store: MechanismMoleculeListData");
		createMoleculeList = new CreateMechanismMoleculeListData(mechanismKeyword);
		MechanismMoleculeListData moleculeList = createMoleculeList.create(mechanism.getSpeciesList(),transaction);
		moleculeNamesTable = createMoleculeList.getMoleculeMap();
		this.merge(createMoleculeList);
		
		System.out.println("CreateChemicalMechanismData Create/Store: MechanismReactionListData");
		createReactionList = new CreateMechanismReactionListData(mechanismKeyword,moleculeNamesTable,false);
		MechanismReactionListData reactionList = createReactionList.create(mechanism.getReactionList(), transaction);
		this.merge(createReactionList);
		
		mechanismData = new ChemicalMechanismData(elementList, moleculeList, reactionList);
		this.flushCreate();
		storeMechanism = new StoreChemkinMechanismData(mechanismKeyword,mechanismData,transaction,true);
		

		return mechanismData;
	}

	public void finish() {
		storeMechanism.finish();
	}



}
