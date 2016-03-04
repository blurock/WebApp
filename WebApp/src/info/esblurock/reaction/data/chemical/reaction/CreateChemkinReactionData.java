package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import info.esblurock.react.mechanisms.chemkin.ChemkinCoefficients;
import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.react.mechanisms.chemkin.ChemkinReaction;
import info.esblurock.react.mechanisms.chemkin.ThirdBodyMolecules;
import info.esblurock.react.mechanisms.chemkin.ThirdBodyWeight;
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

		String reactionKeyword = keywordGenerator.getReactionName(reactantNames, productNames);
		String keyword = keywordBase + ":" + reactionKeyword;
		
		System.out.println("Reaction Keyword: " + reactionKeyword);
		createCoeffData = new CreateChemkinCoefficientsData(reactionKeyword);
		createThirdBody = new CreateThirdBodyMoleculesData(reactionKeyword, moleculeNamesTable);

		ChemkinCoefficientsData forwardCoefficients = null;
		if (reaction.getForwardCoefficients() != null) {
			String forwardS = keyword + ":Forward";
			createCoeffData.setReactionKeyword(forwardS);
			forwardCoefficients = createCoeffData.create(reaction.getForwardCoefficients(), transaction);
		}

		ChemkinCoefficientsData reverseCoefficients = null;
		if (reaction.getReverseCoefficients() != null) {
			String reverseS = keyword + ":Reverse";
			createCoeffData.setReactionKeyword(reverseS);
			reverseCoefficients = createCoeffData.create(reaction.getReverseCoefficients(), transaction);
		}

		ChemkinCoefficientsData troeCoefficients = null;
		if (reaction.getTroeCoefficients() != null) {
			String troeS = keyword + ":Troe";
			createCoeffData.setReactionKeyword(troeS);
			troeCoefficients = createCoeffData.create(reaction.getTroeCoefficients(), transaction);
		}

		ChemkinCoefficientsData lowCoefficients = null;
		if (reaction.getLowCoefficients() != null) {
			String lowS = keyword + ":LowPressure";
			createCoeffData.setReactionKeyword(lowS);
			lowCoefficients = createCoeffData.create(reaction.getLowCoefficients(), transaction);
		}

		ThirdBodyMoleculesData thirdBodyMolecules = null;
		if (reaction.getThirdBodyMolecules() != null) {
			thirdBodyMolecules = createThirdBody.create(reaction.getThirdBodyMolecules(), transaction);
		}

		ChemkinReactionData data = new ChemkinReactionData(reactantNames, productNames, forwardCoefficients,
				reverseCoefficients, lowCoefficients, troeCoefficients, thirdBodyMolecules);
		StoreChemkinReactionData store = new StoreChemkinReactionData(reactionKeyword, data, transaction, storeObject);
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
