package info.esblurock.reaction.server.process.react;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.silent.AtomContainer;

import info.esblurock.CML.generated.Molecule;
import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.info.react.data.molecules.CML.CMLMolecule;
import info.esblurock.info.react.data.molecules.SDF.SDFMolecule;
import info.esblurock.react.mechanisms.BRS.BRSMechanism;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.data.transaction.ActionsUsingIdentificationCode;
import info.esblurock.reaction.data.transaction.reaction.ReactSDFMoleculesToDatabaseTransaction;
import info.esblurock.reaction.data.upload.types.SDFMoleculesFileUpload;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import thermo.data.structure.structure.AtomCounts;

public class ReactSDFMoleculesToDatabase extends ProcessBase {
	static public String moleculeName = "MoleculeName";
	static public String moleculeStructure = "MoleculeStructure";
	
	
	String delimitor = "#";
	
	String validateS;
	String toDatabaseS;
	String uploadS;
	
	SDFMoleculesFileUpload upload;
	ReactSDFMoleculesToDatabaseTransaction moltransaction;


	public ReactSDFMoleculesToDatabase() {
		super();
	}

	public ReactSDFMoleculesToDatabase(ProcessInputSpecificationsBase input) {
		super(input);
	}

	@Override
	protected String getProcessName() {
		return "ReactSDFMoleculesToDatabase";
	}

	@Override
	protected String getProcessDescription() {
		return "Store reaction SDF molecules to database";
	}
	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		ArrayList<String> input = new ArrayList<String>();
		input.add(validateS);
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
	protected void initializeOutputObjects() throws IOException {
		super.initializeOutputObjects();
		moltransaction = new ReactSDFMoleculesToDatabaseTransaction(user, outputSourceCode, keyword,0);
		objectOutputs.add(moltransaction);
	}

	@Override
	protected void createObjects() throws IOException {
		upload = (SDFMoleculesFileUpload) getInputSource(uploadS);
		String commentString = "!";
		StoreObject store = new StoreObject(user, keyword, outputSourceCode);
		String datastring = ActionsUsingIdentificationCode.getUploadedAsString(upload.getFileCode());
		//store.flushStore();
		}

		
		
		

	@Override
	public void initialization() {
		uploadS = "info.esblurock.reaction.data.upload.types.SDFMoleculesFileUpload";
		validateS = "info.esblurock.reaction.data.upload.types.ValidatedReactSDFMoleculesFile";
		toDatabaseS = "info.esblurock.reaction.data.transaction.reaction.ReactSDFMoleculesToDatabaseTransaction";
	}
}
