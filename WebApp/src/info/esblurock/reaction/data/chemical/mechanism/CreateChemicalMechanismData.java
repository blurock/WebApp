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
		
		CreateChemicalElementListData createElementList = new CreateChemicalElementListData();
		ChemicalElementListData   elementList = createElementList.create(mechanism.getElementList());
		
		createMoleculeList = new CreateMechanismMoleculeListData(mechanismKeyword);
		MechanismMoleculeListData reactionMoleculeList = createMoleculeList.create(mechanism.getSpeciesList(),transaction);
		moleculeNamesTable = createMoleculeList.getMoleculeMap();
		
		createReactionList = new CreateMechanismReactionListData(mechanismKeyword,moleculeNamesTable,false);
		MechanismReactionListData reactionList = createReactionList.create(mechanism.getReactionList(), transaction);
		
		ChemicalMechanismData data = new ChemicalMechanismData(elementList, reactionMoleculeList, reactionList);
			
		return data;
	}





}
