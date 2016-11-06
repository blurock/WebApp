package info.esblurock.reaction.server.process.upload;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.data.transaction.ActionsUsingIdentificationCode;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.upload.types.ValidatedThergasMoleculeFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import jThergas.data.read.JThergasReadStructureThermo;
import jThergas.exceptions.JThergasReadException;

public class ValidateThergasMoleculesFile  extends ProcessBase {
	UploadFileTransaction upload;
	ValidatedThergasMoleculeFile validate;

	String uploadS;
	String validateS;
	
	public ValidateThergasMoleculesFile() {
	}
	public ValidateThergasMoleculesFile(ProcessInputSpecificationsBase input) {
		super(input);
	}
	
	@Override
	public void initialization() {
		uploadS = "info.esblurock.reaction.data.upload.types.ThergasMoleculeFileUpload";
		validateS = "info.esblurock.reaction.data.upload.types.ValidatedThergasMoleculeFile";
	}

	@Override
	protected String getProcessName() {
		return "ValidateThergasMoleculesFile";
	}

	@Override
	protected String getProcessDescription() {
		return "Validate the uploaded Thergas molecules";
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
		validate = new ValidatedThergasMoleculeFile(user, upload.getFilename(), outputSourceCode, upload.getSourceType());
		objectOutputs.add(validate);
	}

	@Override
	protected void createObjects() throws IOException {
		try {
			JThergasReadStructureThermo readtherm = new JThergasReadStructureThermo();
			String datastring = ActionsUsingIdentificationCode.getUploadedAsString(upload.getFileCode());
			readtherm.readAndParse(datastring);
		} catch (IOException ex) {
			storeNewTransactions();
			throw ex;
		} catch (JThergasReadException e) {
			throw new IOException(e.toString());
		}
	}

}
