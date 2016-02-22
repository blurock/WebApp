package info.esblurock.reaction.data.chemical.mechanism;

import java.util.HashMap;

import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.reaction.data.chemical.elements.ChemicalElementListData;
import info.esblurock.reaction.data.chemical.elements.CreateChemicalElementListData;
import info.esblurock.reaction.data.chemical.molecule.CreateMechanismMoleculeListData;
import info.esblurock.reaction.data.chemical.molecule.GenerateMoleculeKeywords;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeListData;
import info.esblurock.reaction.data.chemical.reaction.CreateMechanismReactionListData;
import info.esblurock.reaction.data.chemical.reaction.MechanismReactionListData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class CreateChemicalMechanismData {
	
	GenerateMoleculeKeywords generateKeywords;
	String mechanismKeyword;
	HashMap<String,String> moleculeNamesTable;
	String keywordBase;
	
	CreateMechanismMoleculeListData createMoleculeList;
	CreateMechanismReactionListData createReactionList;
	
	
	public CreateChemicalMechanismData(String keywordBase) {
		this.keywordBase = keywordBase;
	}
	
	public ChemicalMechanismData create(String mechanismKeyword, ChemkinMechanism mechanism, TransactionInfo transaction) {
		
		System.out.println("CreateChemicalMechanismData Create/Store: ChemicalElementListData");
		CreateChemicalElementListData createElementList = new CreateChemicalElementListData();
		ChemicalElementListData   elementList = createElementList.create(mechanism.getElementList());
		
		System.out.println("CreateChemicalMechanismData Create/Store: MechanismMoleculeListData");
		createMoleculeList = new CreateMechanismMoleculeListData(mechanismKeyword);
		MechanismMoleculeListData moleculeList = createMoleculeList.create(mechanism.getSpeciesList(),transaction);
		moleculeNamesTable = createMoleculeList.getMoleculeMap();
		
		System.out.println("CreateChemicalMechanismData Create/Store: MechanismReactionListData");
		createReactionList = new CreateMechanismReactionListData(mechanismKeyword,moleculeNamesTable,false);
		MechanismReactionListData reactionList = createReactionList.create(mechanism.getReactionList(), transaction);
		
		ChemicalMechanismData data = new ChemicalMechanismData(elementList, moleculeList, reactionList);
			
		return data;
	}





}
