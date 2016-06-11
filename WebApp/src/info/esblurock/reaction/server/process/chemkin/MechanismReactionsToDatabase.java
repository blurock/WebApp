package info.esblurock.reaction.server.process.chemkin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.react.mechanisms.chemkin.ChemkinReaction;
import info.esblurock.react.mechanisms.chemkin.ChemkinReactionList;
import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;
import info.esblurock.reaction.data.chemical.reaction.CreateChemkinReactionData;
import info.esblurock.reaction.data.chemical.reaction.MechanismReactionListData;
import info.esblurock.reaction.data.transaction.chemkin.MechanismMoleculesToDatabaseTransaction;
import info.esblurock.reaction.data.transaction.chemkin.MechanismReactionsToDatabaseTransaction;
import info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.datastore.StorageAndRetrievalUtilities;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.queries.ChemicalMechanismDataQuery;
import info.esblurock.reaction.server.utilities.WriteObjectTransactionToDatabase;

public class MechanismReactionsToDatabase  extends ProcessBase {
	String uploadS;
	String moleculesS;
	String toDatabaseS;
	
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
		MechanismReactionsToDatabaseTransaction rxntransaction = new MechanismReactionsToDatabaseTransaction(user, outputSourceCode, keyword);
		objectOutputs.add(rxntransaction);
	}

	ChemkinMechanism parse() throws IOException {
		ChemkinMechanismFileUpload upload = (ChemkinMechanismFileUpload) getInputSource(uploadS);
		String commentString = "!";
		ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(upload, commentString);
		ChemkinMechanism mechanism = new ChemkinMechanism();
		mechanism.parse(chemkinstring, commentString);
		return mechanism;
	}
	@Override
	protected void createObjects() throws IOException {
		ChemkinMechanism mechanism = parse();

		MechanismMoleculesToDatabaseTransaction moltransaction = (MechanismMoleculesToDatabaseTransaction) getInputSource(moleculesS);
		Map<String,String> moleculeNamesTable = moltransaction.getMoleculeMap();
		
		ChemkinReactionList reactionList = mechanism.getReactionList();
		CreateChemkinReactionData createReaction = new CreateChemkinReactionData(keyword, moleculeNamesTable,true);
		
		ArrayList<DatabaseObject> chemkinReactionList = new ArrayList<DatabaseObject>();
		for(ChemkinReaction reaction : reactionList) {
			ChemkinReactionData rxndata = createReaction.create(reaction);
			chemkinReactionList.add(rxndata);
		}		
		MechanismReactionListData data = new MechanismReactionListData(keyword);
		data.setNumberOfReaction(chemkinReactionList.size());
		
		StorageAndRetrievalUtilities.storeDatabaseObjects(chemkinReactionList);
		WriteObjectTransactionToDatabase.writeObjectWithTransaction(user, keyword, outputSourceCode, data);
	}
}
