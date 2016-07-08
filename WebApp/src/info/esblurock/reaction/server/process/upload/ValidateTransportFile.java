package info.esblurock.reaction.server.process.upload;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.data.chemical.transport.ListOfTransportProperties;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.upload.types.ValidatedTransportFile;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class ValidateTransportFile extends ProcessBase {
	UploadFileTransaction upload;
	ValidatedTransportFile validate;

	String uploadS;
	String validateS;

	public ValidateTransportFile() {
		super();
	}

	public ValidateTransportFile(ProcessInputSpecificationsBase input) {
		super(input);
	}

	public void initialization() {
		validateS = "info.esblurock.reaction.data.upload.types.ValidatedTransportFile";
		uploadS = "info.esblurock.reaction.data.upload.types.TransportFileUpload";
	}

	@Override
	protected String getProcessName() {
		return "ValidateTransportPropertiesFile";
	}

	@Override
	protected String getProcessDescription() {
		return "Validate a transport file from client box and see if correct";
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
		validate = new ValidatedTransportFile(user, upload.getFilename(), outputSourceCode, upload.getSourceType());
		objectOutputs.add(validate);
	}

	@Override
	protected void createObjects() throws IOException {
		String commentString = "!";
		try {
			ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(upload, commentString);
			ListOfTransportProperties lst = new ListOfTransportProperties();
			lst.parse(keyword, chemkinstring);
		} catch (IOException ex) {
			storeNewTransactions();
			throw ex;
		}
	}
}
