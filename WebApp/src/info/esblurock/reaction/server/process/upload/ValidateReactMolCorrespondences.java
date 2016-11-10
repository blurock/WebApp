package info.esblurock.reaction.server.process.upload;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.react.mechanisms.BRS.BRSMechanism;
import info.esblurock.reaction.data.reaction.AlternativeMolecules;
import info.esblurock.reaction.data.transaction.ActionsUsingIdentificationCode;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.upload.types.ValidatedReactMolCorrespondencesFile;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class ValidateReactMolCorrespondences  extends ProcessBase {
	UploadFileTransaction upload;
	ValidatedReactMolCorrespondencesFile validate;
	
	String uploadS;
	String validateS;
	
	public ValidateReactMolCorrespondences() {
		super();
	}

	public ValidateReactMolCorrespondences(ProcessInputSpecificationsBase input) {
		super(input);
	}

	@Override
	public void initialization() {
		uploadS = "info.esblurock.reaction.data.upload.types.ReactMolCorrespondencesFileUpload";
		validateS = "info.esblurock.reaction.data.upload.types.ValidatedReactMolCorrespondencesFile";
	}

	@Override
	protected String getProcessName() {
		return "ValidateReactMolCorrespondences";
	}

	@Override
	protected String getProcessDescription() {
		return "Validate the uploaded Reaction Molecule Correspondences";
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
		output.add(validateS);
		return output;
	}
	@Override
	protected void initializeOutputObjects() throws IOException {
		super.initializeOutputObjects();
		upload = (UploadFileTransaction) getInputSource(uploadS);
		validate = new ValidatedReactMolCorrespondencesFile(user, upload.getFilename(), outputSourceCode, upload.getSourceType());
		objectOutputs.add(validate);
	}

	@Override
	protected void createObjects() throws IOException {
		String commentString = "!";
		ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(upload, commentString);
		AlternativeMolecules alt = new AlternativeMolecules();
		alt.parse(chemkinstring);
}
}
