package info.esblurock.reaction.server.process.chemkin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import info.esblurock.react.mechanisms.chemkin.ChemkinCoefficients;
import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.react.mechanisms.chemkin.ChemkinReaction;
import info.esblurock.react.mechanisms.chemkin.ChemkinReactionList;
import info.esblurock.react.mechanisms.chemkin.ThirdBodyMolecules;
import info.esblurock.react.mechanisms.chemkin.ThirdBodyWeight;
import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.chemical.reaction.ChemkinCoefficientsData;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;
import info.esblurock.reaction.data.chemical.reaction.GenerateReactionKeywords;
import info.esblurock.reaction.data.chemical.reaction.MechanismReactionListData;
import info.esblurock.reaction.data.chemical.reaction.ThirdBodyMoleculesData;
import info.esblurock.reaction.data.chemical.reaction.ThirdBodyWeightsData;
import info.esblurock.reaction.data.transaction.chemkin.MechanismMoleculesToDatabaseTransaction;
import info.esblurock.reaction.data.transaction.chemkin.MechanismReactionsToDatabaseTransaction;
import info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.datastore.StorageAndRetrievalUtilities;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.queries.ChemicalMechanismDataQuery;
import info.esblurock.reaction.server.utilities.WriteObjectTransactionToDatabase;

public class MechanismReactionsToDatabase extends ProcessBase {
	String uploadS;
	String moleculesS;
	String toDatabaseS;

	/**
	 * The reaction keyword generator. From the reaction generate a unique
	 * keyword (including the keywordBase -- which basically is the mechanism
	 * name)
	 */
	GenerateReactionKeywords keywordGenerator;
	Map<String, String> moleculeNamesTable;
	ChemicalMechanismDataQuery mechanismQuery;

	public MechanismReactionsToDatabase() {
		super();
	}

	public MechanismReactionsToDatabase(ProcessInputSpecificationsBase input) {
		super(input);
	}

	@Override
	public void initialization() {
		mechanismQuery = new ChemicalMechanismDataQuery();
		keywordGenerator = new GenerateReactionKeywords(keyword);
		uploadS = "info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload";
		moleculesS = "info.esblurock.reaction.data.transaction.chemkin.MechanismMoleculesToDatabaseTransaction";
		toDatabaseS = "info.esblurock.reaction.data.transaction.chemkin.MechanismReactionsToDatabaseTransaction";
	}

	@Override
	protected String getProcessName() {
		return "MechanismReactionsToDatabase";
	}

	@Override
	protected String getProcessDescription() {
		return "Store mechanism molecules to database";
	}

	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		ArrayList<String> input = new ArrayList<String>();
		input.add(moleculesS);
		input.add(uploadS);
		return input;
	}

	@Override
	protected ArrayList<String> getOutputTransactionObjectNames() {
		ArrayList<String> output = new ArrayList<String>();
		output.add(toDatabaseS);
		return output;
	}

	@Override
	protected void initializeOutputObjects() {
		super.initializeOutputObjects();
		MechanismReactionsToDatabaseTransaction rxntransaction = new MechanismReactionsToDatabaseTransaction(user,
				outputSourceCode, keyword);
		objectOutputs.add(rxntransaction);
	}

	@Override
	protected void createObjects() throws IOException {
		ChemkinMechanism mechanism = parse();

		MechanismMoleculesToDatabaseTransaction moltransaction = (MechanismMoleculesToDatabaseTransaction) getInputSource(
				moleculesS);
		moleculeNamesTable = moltransaction.getMoleculeMap();

		ChemkinReactionList reactionList = mechanism.getReactionList();

		ArrayList<DatabaseObject> chemkinReactionList = new ArrayList<DatabaseObject>();
		for (ChemkinReaction reaction : reactionList) {
			ChemkinReactionData rxndata = create(reaction);
			chemkinReactionList.add(rxndata);
		}
		MechanismReactionListData data = new MechanismReactionListData(keyword);
		data.setNumberOfReaction(chemkinReactionList.size());

		StorageAndRetrievalUtilities.storeDatabaseObjects(chemkinReactionList);
		WriteObjectTransactionToDatabase.writeObjectWithTransaction(user, keyword, outputSourceCode, data);
	}

	/**
	 * parse the text to form {@link ChemkinMechanism}
	 * 
	 * @return The mechanism data
	 * @throws IOException
	 *             if there is a problem with the parsing (should have been
	 *             checked previously)
	 */
	private ChemkinMechanism parse() throws IOException {
		ChemkinMechanismFileUpload upload = (ChemkinMechanismFileUpload) getInputSource(uploadS);
		String commentString = "!";
		ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(upload, commentString);
		ChemkinMechanism mechanism = new ChemkinMechanism();
		mechanism.parse(chemkinstring, commentString);
		return mechanism;
	}

	/**
	 * The keyword is generated from the reactants and products using
	 * {@link GenerateReactionKeywords}
	 * 
	 * @param reaction
	 *            The reaction parsed from the text
	 * @return the generated keyword
	 */
	private String getKeyword(ChemkinReaction reaction) {
		ArrayList<String> reactantNames = createMoleculeArrayList(reaction.getReactants());
		ArrayList<String> productNames = createMoleculeArrayList(reaction.getProducts());
		String reactionEquation = keywordGenerator.getReactionName(reactantNames, productNames);
		String keyword = keywordGenerator.getReactionFullName(reactionEquation);
		return keyword;
	}

	/**
	 * From {@link ChemkinReaction} form the {@link ChemkinReactionData} This
	 * routine parses through all the coefficient types using createCoeffs:
	 * forward, reverse, low, high troe, sri, plog The third body is parsed
	 * using createThirdBody
	 * 
	 * @param reaction
	 *            that has been parsed from the text
	 * @return The reaction data
	 */
	private ChemkinReactionData create(ChemkinReaction reaction) {
		String rxnkeyword = getKeyword(reaction);
		ArrayList<String> reactantNames = createMoleculeArrayList(reaction.getReactants());
		ArrayList<String> productNames = createMoleculeArrayList(reaction.getProducts());

		ArrayList<String> reactantReactionNames = new ArrayList<String>();
		for (String name : reactantNames) {
			String rxnmolname = moleculeNamesTable.get(name);
			reactantReactionNames.add(rxnmolname);
		}

		ArrayList<String> productReactionNames = new ArrayList<String>();
		for (String name : productNames) {
			String rxnmolname = moleculeNamesTable.get(name);
			productReactionNames.add(rxnmolname);
		}
		ChemkinCoefficientsData forwardCoefficients = null;
		if (reaction.getForwardCoefficients() != null) {
			forwardCoefficients = createCoeffs(reaction.getForwardCoefficients());
		}

		ChemkinCoefficientsData reverseCoefficients = null;
		if (reaction.getReverseCoefficients() != null) {
			reverseCoefficients = createCoeffs(reaction.getReverseCoefficients());
		}

		ChemkinCoefficientsData lowCoefficients = null;
		if (reaction.getLowCoefficients() != null) {
			lowCoefficients = createCoeffs(reaction.getLowCoefficients());
		}

		ChemkinCoefficientsData highCoefficients = null;
		if (reaction.getHighCoefficients() != null) {
			highCoefficients = createCoeffs(reaction.getHighCoefficients());
		}

		ChemkinCoefficientsData troeCoefficients = null;
		if (reaction.getTroeCoefficients() != null) {
			troeCoefficients = createCoeffs(reaction.getTroeCoefficients());
		}

		ChemkinCoefficientsData sriCoefficients = null;
		if (reaction.getSriCoefficients() != null) {
			sriCoefficients = createCoeffs(reaction.getSriCoefficients());
		}

		ArrayList<ChemkinCoefficientsData> plogCoefficients = null;
		if (reaction.getPlogCoefficients() != null) {
			plogCoefficients = new ArrayList<ChemkinCoefficientsData>();
			for (ChemkinCoefficients plog : reaction.getPlogCoefficients()) {
				ChemkinCoefficientsData plogC = createCoeffs(plog);
				plogCoefficients.add(plogC);
			}
		}

		ThirdBodyMoleculesData thirdBodyMolecules = null;
		if (reaction.getThirdBodyMolecules() != null) {
			thirdBodyMolecules = createThirdBody(reaction.getThirdBodyMolecules());
		}

		ChemkinReactionData data = new ChemkinReactionData(keyword, rxnkeyword, reactantReactionNames,
				productReactionNames, forwardCoefficients, reverseCoefficients, lowCoefficients, highCoefficients,
				troeCoefficients, sriCoefficients, plogCoefficients, thirdBodyMolecules);

		return data;
	}

	/**
	 * Creates a list of constants from an String[]
	 * 
	 * @param original
	 *            the parsed constants
	 * @return the list of string constants
	 */
	private ArrayList<String> transferConstants(String[] original) {
		ArrayList<String> coeffs = null;
		if (original != null) {
			coeffs = new ArrayList<String>();
			String[] tcoeffs = original;
			for (int i = 0; i < tcoeffs.length; i++) {
				coeffs.add(tcoeffs[i]);
			}
		}
		return coeffs;
	}

	/**
	 * Set up a {@link ChemkinCoefficientsData}
	 * 
	 * @param coeffs
	 * @return the coefficients
	 */
	private ChemkinCoefficientsData createCoeffs(ChemkinCoefficients coeffs) {
		ArrayList<String> coeffvalues = transferConstants(coeffs.getCoeffs());
		ChemkinCoefficientsData data = new ChemkinCoefficientsData(coeffs.isForward(), coeffs.isReverse(),
				coeffs.isLow(), coeffs.isTroe(), coeffs.isHigh(), coeffs.isPlog(), coeffs.isSri(), coeffs.getA(),
				coeffs.getN(), coeffs.getEa(), coeffvalues);
		return data;
	}

	/**
	 * Create a list of labels from a set of {@link ChemkinMolecule} classes
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

	/**
	 * Create {@link ThirdBodyMoleculesData} from the third body parsed from the
	 * file A set of {@link ThirdBodyWeightsData} is created.
	 * 
	 * @param thirdbodies
	 * @return
	 */
	private ThirdBodyMoleculesData createThirdBody(ThirdBodyMolecules thirdbodies) {
		ArrayList<ThirdBodyWeightsData> thirdBodyMoleculeKeys = new ArrayList<ThirdBodyWeightsData>();

		Set<String> keys = thirdbodies.keySet();
		for (String key : keys) {
			ThirdBodyWeight thirdbody = thirdbodies.get(key);
			double weight = thirdbody.getWeight();
			ThirdBodyWeightsData third = new ThirdBodyWeightsData(thirdbody.getMolecule().getLabel(), weight);
			thirdBodyMoleculeKeys.add(third);
		}
		ThirdBodyMoleculesData thirds = new ThirdBodyMoleculesData(thirdBodyMoleculeKeys);
		return thirds;
	}

}