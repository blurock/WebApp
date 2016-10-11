package info.esblurock.reaction.server.process.upload;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.upload.types.ValidatedChemkinMechanismFile;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class ValidateChemkinMechanismFile  extends ProcessBase {
	UploadFileTransaction upload;
	ValidatedChemkinMechanismFile validate;
	
	String uploadS;
	String validateS;
	
	public ValidateChemkinMechanismFile() {
		super();
	}

	public ValidateChemkinMechanismFile(ProcessInputSpecificationsBase input) {
		super(input);
	}

	@Override
	public void initialization() {
		uploadS = "info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload";
		validateS = "info.esblurock.reaction.data.upload.types.ValidatedChemkinMechanismFile";
	}

	@Override
	protected String getProcessName() {
		return "ValidateChemkinMechanismFile";
	}

	@Override
	protected String getProcessDescription() {
		return "Validate the uploaded CHEMKIN mechanism";
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
		System.out.println("ValidateChemkinMechanismFile::initializeOutputObjects(): " + upload);
		validate = new ValidatedChemkinMechanismFile(user, upload.getFilename(), outputSourceCode, upload.getSourceType());
		objectOutputs.add(validate);
	}

	@Override
	protected void createObjects() throws IOException {
		String commentString = "!";
		try {
			ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(upload, commentString);
			ChemkinMechanism mechanism = new ChemkinMechanism();
			mechanism.parse(chemkinstring, commentString);
		} catch (IOException ex) {
			storeNewTransactions();
			throw ex;
		}
	}

}
