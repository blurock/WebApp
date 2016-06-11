package info.esblurock.reaction.server.process.upload;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.upload.ChemkinMechanismFileSpecification;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload;
import info.esblurock.reaction.data.upload.types.CreateBufferedReaderForSourceFile;
import info.esblurock.reaction.server.StringToKeyConversion;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;

public class ReadChemkinMechanismFile extends ProcessBase {
	static String deleteUploadedFile = "RemoveUploadedFile";
	static String keywordDelimitor = "#";
	InputStreamToLineDatabase input;
	StringToKeyConversion conversion;

	UploadFileTransaction upload;
	protected String textBody;
	protected String textName;
	protected String sourceType;

	String descriptionS;
	String specificationS;
	String uploadS;
	DescriptionDataData description;
	ChemkinMechanismFileSpecification specification;

	public ReadChemkinMechanismFile() {
		super();
	}

	public ReadChemkinMechanismFile(ProcessInputSpecificationsBase specs) {
		super(specs);
		input = new InputStreamToLineDatabase();
		conversion = new StringToKeyConversion();
	}
	public void initialization() {
		descriptionS   = "info.esblurock.reaction.data.description.DescriptionDataData";
		specificationS = "info.esblurock.reaction.data.upload.ChemkinMechanismFileSpecification";
		uploadS = "info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload";
	}

	@Override
	protected String getProcessName() {
		return "ReadChemkinMechanismFile";
	}

	@Override
	protected String getProcessDescription() {
		return "Read in a CHEMKIN mechanism from client box and see if correct";
	}
	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		ArrayList<String> input = new ArrayList<String>();
		input.add(descriptionS);
		input.add(specificationS);
		return input;
	}

	@Override
	protected ArrayList<String> getOutputTransactionObjectNames() {
		ArrayList<String> output = new ArrayList<String>();
		output.add(uploadS);
		return output;
	}
	@Override
	protected void initializeOutputObjects() {
		super.initializeOutputObjects();
		upload = new ChemkinMechanismFileUpload(user, textName, outputSourceCode, sourceType, 0);
		objectOutputs.add(upload);
	}
    protected void setUpInputDataObjects() throws IOException {
    	super.setUpInputDataObjects();
    	description = (DescriptionDataData) getInputSource(descriptionS);
    	specification = (ChemkinMechanismFileSpecification) getInputSource(specificationS);
		textBody = specification.getTextBody();
		textName = specification.getTextName();
		sourceType = specification.getSourceType();
    }
/**
 * 
 */
	protected void createObjects() throws IOException {
		BufferedReader br = CreateBufferedReaderForSourceFile.getBufferedReader(sourceType, textName, textBody);
		String commentString = "!";
		log.info("User verified: to read text: " + textName);
		upload = input.uploadFile(upload, br);
		try {
			ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(upload, commentString);
			ChemkinMechanism mechanism = new ChemkinMechanism();
			mechanism.parse(chemkinstring, commentString);
		} catch (IOException ex) {
			storeNewTransactions();
			throw ex;
		}

	}
	public String getTextBody() {
		return textBody;
	}
	public String getTextName() {
		return textName;
	}
	public String getSourceType() {
		return sourceType;
	}
}
