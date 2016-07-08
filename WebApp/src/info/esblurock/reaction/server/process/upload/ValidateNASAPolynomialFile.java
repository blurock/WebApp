package info.esblurock.reaction.server.process.upload;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.data.chemical.thermo.ParseNASAPolynomialSet;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.upload.types.ValidatedNASAPolynomialFile;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class ValidateNASAPolynomialFile   extends ProcessBase {
	UploadFileTransaction upload;
	ValidatedNASAPolynomialFile validate;
	
	String uploadS;
	String validateS;

	public ValidateNASAPolynomialFile() {
		super();
	}

	public ValidateNASAPolynomialFile(ProcessInputSpecificationsBase input) {
		super(input);
	}

	@Override
	public void initialization() {
		uploadS   = "info.esblurock.reaction.data.upload.types.NASAPolynomialFileUpload";
		validateS = "info.esblurock.reaction.data.upload.types.ValidatedNASAPolynomialFile";
	}

	@Override
	protected String getProcessName() {
		return "ValidateNASAPolynomialFile";
	}

	@Override
	protected String getProcessDescription() {
		return "Validate the uploaded NASA polynomial";
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
		System.out.println("ValidatedNASAPolynomialFile::initializeOutputObjects(): " + upload);
		validate = new ValidatedNASAPolynomialFile(user, upload.getFilename(), outputSourceCode, upload.getSourceType());
		objectOutputs.add(validate);
	}

	@Override
	protected void createObjects() throws IOException {
		String commentString = "!";
		try {
			ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(upload, commentString);
			ParseNASAPolynomialSet parse = new ParseNASAPolynomialSet();
			parse.parse(keyword, chemkinstring);
		} catch (IOException ex) {
			storeNewTransactions();
			throw ex;
		}
	}

}
