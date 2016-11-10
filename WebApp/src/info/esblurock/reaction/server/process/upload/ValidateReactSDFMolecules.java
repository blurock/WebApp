package info.esblurock.reaction.server.process.upload;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.react.mechanisms.BRS.BRSMechanism;
import info.esblurock.reaction.data.transaction.ActionsUsingIdentificationCode;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.upload.types.ValidatedReactSDFMoleculesFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class ValidateReactSDFMolecules extends ProcessBase {
	UploadFileTransaction upload;
	ValidatedReactSDFMoleculesFile validate;
	
	String uploadS;
	String validateS;
	
	public ValidateReactSDFMolecules() {
		super();
	}

	public ValidateReactSDFMolecules(ProcessInputSpecificationsBase input) {
		super(input);
	}

	@Override
	public void initialization() {
		uploadS = "info.esblurock.reaction.data.upload.types.SDFMoleculesFileUpload";
		validateS = "info.esblurock.reaction.data.upload.types.ValidatedReactSDFMoleculesFile";
	}

	@Override
	protected String getProcessName() {
		return "ValidateReactSDFMolecules";
	}

	@Override
	protected String getProcessDescription() {
		return "Validate the uploaded Reaction SDF Molecules";
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
		validate = new ValidatedReactSDFMoleculesFile(user, upload.getFilename(), outputSourceCode, upload.getSourceType());
		objectOutputs.add(validate);
	}

	@Override
	protected void createObjects() throws IOException {
		BRSMechanism mech = new BRSMechanism();
		String datastring = ActionsUsingIdentificationCode.getUploadedAsString(upload.getFileCode());
		mech.readMolecules(datastring);
	}

}
