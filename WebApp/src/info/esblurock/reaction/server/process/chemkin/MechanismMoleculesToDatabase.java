package info.esblurock.reaction.server.process.chemkin;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.reaction.data.StoreDatabaseObject;
import info.esblurock.reaction.data.chemical.molecule.CreateMechanismMoleculeListData;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeData;
import info.esblurock.reaction.data.transaction.chemkin.MechanismMoleculesToDatabaseTransaction;
import info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class MechanismMoleculesToDatabase extends ProcessBase {
	String uploadS;
	String toDatabaseS;
	ChemkinMechanismFileUpload upload;
	ArrayList<MechanismMoleculeData> moleculeList;
	CreateMechanismMoleculeListData createMoleculeList;
	MechanismMoleculesToDatabaseTransaction moltransaction;

	public MechanismMoleculesToDatabase() {
		super();
	}

	public MechanismMoleculesToDatabase(ProcessInputSpecificationsBase input) {
		super(input);
	}

	@Override
	protected String getProcessName() {
		return "MechanismMoleculesToDatabase";
	}

	@Override
	protected String getProcessDescription() {
		return "Store mechanism molecules to database";
	}
	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		ArrayList<String> input = new ArrayList<String>();
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
		moltransaction = new MechanismMoleculesToDatabaseTransaction(user, outputSourceCode, keyword,0);
		objectOutputs.add(moltransaction);
	}

	@Override
	protected void createObjects() throws IOException {
		upload = (ChemkinMechanismFileUpload) getInputSource(uploadS);
		String commentString = "!";
		ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(upload, commentString);
		ChemkinMechanism mechanism = new ChemkinMechanism();
		mechanism.parse(chemkinstring, commentString);
		createMoleculeList = new CreateMechanismMoleculeListData(user, keyword);
		createMoleculeList.create(mechanism.getSpeciesList());
		StoreDatabaseObject store = new StoreDatabaseObject();
		for (MechanismMoleculeData mol : createMoleculeList.getMoleculeList()) {
			store.store(mol);
		}
		store.flushStore();
		moltransaction.setMoleculeCount(createMoleculeList.getMoleculeList().size());
		moltransaction.setMoleculeMap(createMoleculeList.getMoleculeMap());
	}

	@Override
	public void initialization() {
		uploadS = "info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload";
		toDatabaseS = "info.esblurock.reaction.data.transaction.chemkin.MechanismMoleculesToDatabaseTransaction";
	}

}
