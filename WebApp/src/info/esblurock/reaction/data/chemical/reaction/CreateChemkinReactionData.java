package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import info.esblurock.react.mechanisms.chemkin.ChemkinCoefficients;
import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.react.mechanisms.chemkin.ChemkinReaction;
import info.esblurock.react.mechanisms.chemkin.ThirdBodyMolecules;
import info.esblurock.react.mechanisms.chemkin.ThirdBodyWeight;
import info.esblurock.reaction.data.chemical.molecule.GenerateMoleculeKeywords;
import info.esblurock.reaction.data.transaction.TransactionInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateChemkinReactionData.
 * 
 */
public class CreateChemkinReactionData {


	/** The molecule keys table. */
	// moleculeName (label in ChemkinMolecule) -> full molecule name
	HashMap<String, String> moleculeNamesTable;

	/**
	 * The reaction keyword generator. From the reaction generate a unique
	 * keyword (including the keywordBase -- which basically is the mechanism
	 * name)
	 */
	GenerateReactionKeywords keywordGenerator;

	/**
	 * Determines whether the objects within the reaction should be stored true:
	 * The reaction should be stored in the database false: The reaction is part
	 * of a list, then will be stored when the list is stored
	 */
	boolean storeObject;
	
	String keywordBase;

	CreateChemkinCoefficientsData createCoeffData;
	CreateThirdBodyMoleculesData createThirdBody;

	/**
	 * Instantiates a new creates the chemkin reaction data.
	 *
	 * @param keywordBase
	 *            the keyword base for all the reactions (usually the mechanism
	 *            name)
	 * @param moleculeNamesTable
	 *            the correspondence between the species label and the full
	 *            molecule name (generated earlier)
	 */
	public CreateChemkinReactionData(String keywordBase, HashMap<String, String> moleculeNamesTable,
			boolean storeObject) {
		this.keywordBase = keywordBase;
		keywordGenerator = new GenerateReactionKeywords(keywordBase);
		this.moleculeNamesTable = moleculeNamesTable;
		this.storeObject = storeObject;
	}

	/**
	 * Creates the {@link ChemkinReactionData} structure
	 * 
	 * 
	 *
	 * @param reaction
	 *            the reaction
	 * @param transaction
	 *            the transaction
	 * @return the chemkin reaction data
	 */
	public ChemkinReactionData create(ChemkinReaction reaction, TransactionInfo transaction) {
		ArrayList<String> reactantNames = createMoleculeArrayList(reaction.getReactants());
		ArrayList<String> productNames = createMoleculeArrayList(reaction.getProducts());

		String reactionEquation = keywordGenerator.getReactionName(reactantNames, productNames);
		String keyword = keywordGenerator.getReactionFullName(reactionEquation);
		
		System.out.println("Reaction Keyword: " + keyword);
		createCoeffData = new CreateChemkinCoefficientsData(keyword);
		createThirdBody = new CreateThirdBodyMoleculesData(keyword, moleculeNamesTable);

		GenerateMoleculeKeywords molKeywords = new GenerateMoleculeKeywords(keyword);
		ArrayList<String> reactantReactionNames = new ArrayList<String>();
		for(String name : reactantNames) {
			String rxnmolname = moleculeNamesTable.get(name);
			reactantReactionNames.add(rxnmolname);
		}
		ArrayList<String> productReactionNames = new ArrayList<String>();
		for(String name : productNames) {
			String rxnmolname = moleculeNamesTable.get(name);
			productReactionNames.add(rxnmolname);
		}
		
		ChemkinCoefficientsData forwardCoefficients = null;
		if (reaction.getForwardCoefficients() != null) {
			String forwardS = keyword + ":" + StoreChemkinCoefficientsData.forward;
			System.out.println("CreateChemkinReactionData: " + forwardS);
			createCoeffData.setReactionKeyword(forwardS);
			forwardCoefficients = createCoeffData.create(reaction.getForwardCoefficients(), transaction);
		}

		ChemkinCoefficientsData reverseCoefficients = null;
		if (reaction.getReverseCoefficients() != null) {
			String reverseS = keyword + ":" + StoreChemkinCoefficientsData.reverse;
			System.out.println("CreateChemkinReactionData: " + reverseS);
			createCoeffData.setReactionKeyword(reverseS);
			reverseCoefficients = createCoeffData.create(reaction.getReverseCoefficients(), transaction);
		}

		ChemkinCoefficientsData lowCoefficients = null;
		if (reaction.getLowCoefficients() != null) {
			String lowS = keyword + ":" + StoreChemkinCoefficientsData.low;
			createCoeffData.setReactionKeyword(lowS);
			lowCoefficients = createCoeffData.create(reaction.getLowCoefficients(), transaction);
		}

		ChemkinCoefficientsData highCoefficients = null;
		if (reaction.getHighCoefficients() != null) {
			String highS = keyword + ":" + StoreChemkinCoefficientsData.high;
			createCoeffData.setReactionKeyword(highS);
			highCoefficients = createCoeffData.create(reaction.getHighCoefficients(), transaction);
		}

		ChemkinCoefficientsData troeCoefficients = null;
		if (reaction.getTroeCoefficients() != null) {
			String troeS = keyword + ":" + StoreChemkinCoefficientsData.troe;
			createCoeffData.setReactionKeyword(troeS);
			troeCoefficients = createCoeffData.create(reaction.getTroeCoefficients(), transaction);
		}

		ChemkinCoefficientsData sriCoefficients = null;
		if (reaction.getSriCoefficients() != null) {
			String sriS = keyword + ":" + StoreChemkinCoefficientsData.sri;
			System.out.println("CreateChemkinReactionData: " + sriS);
			System.out.println(reaction.toString());
			createCoeffData.setReactionKeyword(sriS);
			sriCoefficients = createCoeffData.create(reaction.getSriCoefficients(), transaction);
		}

		ArrayList<ChemkinCoefficientsData> plogCoefficients = null;
		if (reaction.getPlogCoefficients() != null) {
			plogCoefficients = new ArrayList<ChemkinCoefficientsData>();
			String plogS = keyword + ":" + StoreChemkinCoefficientsData.plog;
			for(ChemkinCoefficients plog : reaction.getPlogCoefficients()) {
				createCoeffData.setReactionKeyword(plogS);
				ChemkinCoefficientsData plogC = createCoeffData.create(plog, transaction);
				plogCoefficients.add(plogC);
			}
		}

		ThirdBodyMoleculesData thirdBodyMolecules = null;
		if (reaction.getThirdBodyMolecules() != null) {
			thirdBodyMolecules = createThirdBody.create(reaction.getThirdBodyMolecules(), transaction);
		}

		ChemkinReactionData data = new ChemkinReactionData(reactantReactionNames, productReactionNames, 
				forwardCoefficients,reverseCoefficients, 
				lowCoefficients, highCoefficients, 
				troeCoefficients, sriCoefficients, 
				plogCoefficients,
				thirdBodyMolecules);

		
		StoreChemkinReactionData store = new StoreChemkinReactionData(keyword, data, transaction, storeObject);
		return data;
	}

	/**
	 * Creates the molecule array list.
	 *
	 * @param molecules
	 *            the molecules
	 * @return the array list
	 */
	private ArrayList<String> createMoleculeArrayList(ArrayList<ChemkinMolecule> molecules) {
		ArrayList<String> keys = new ArrayList<String>();
		for (ChemkinMolecule molecule : molecules) {
			keys.add(molecule.getLabel());
		}
		return keys;
	}
}
