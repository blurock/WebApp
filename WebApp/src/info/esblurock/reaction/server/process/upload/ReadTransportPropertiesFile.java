package info.esblurock.reaction.server.process.upload;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.data.chemical.transport.ListOfTransportProperties;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.upload.NASAPolynomialFileSpecification;
import info.esblurock.reaction.data.upload.TransportFileSpecification;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.upload.types.CreateBufferedReaderForSourceFile;
import info.esblurock.reaction.data.upload.types.TransportFileUpload;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;

public class ReadTransportPropertiesFile extends ProcessBase {

	
	InputStreamToLineDatabase input;
	UploadFileTransaction upload;
	protected String textBody;
	protected String textName;
	protected String sourceType;

	String descriptionS;
	String specificationS;
	String uploadS;
	DescriptionDataData description;
	TransportFileSpecification specification;


	public ReadTransportPropertiesFile() {
		super();
	}

	public ReadTransportPropertiesFile(ProcessInputSpecificationsBase specs) {
		super(specs);
		input = new InputStreamToLineDatabase();
	}
	public void initialization() {
		descriptionS = "info.esblurock.reaction.data.description.DescriptionDataData";
		specificationS = "info.esblurock.reaction.data.upload.TransportFileSpecification";
		uploadS = "info.esblurock.reaction.data.upload.types.TransportFileUpload";
	}
	@Override
	protected String getProcessName() {
		return "ReadTransportPropertiesFile";
	}

	@Override
	protected String getProcessDescription() {
		return "Read in a transport file from client box and see if correct";
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
		upload = new TransportFileUpload(user, textName, outputSourceCode, sourceType, 0);
		objectOutputs.add(upload);
	}
    protected void setUpInputDataObjects() throws IOException {
    	super.setUpInputDataObjects();
    	description = (DescriptionDataData) getInputSource(descriptionS);
    	specification = (TransportFileSpecification) getInputSource(specificationS);
		textBody = specification.getTextBody();
		textName = specification.getTextName();
		sourceType = specification.getSourceType();    	
    }

	@Override
	protected void createObjects() throws IOException {
		BufferedReader br = CreateBufferedReaderForSourceFile.getBufferedReader(sourceType, textName, textBody);
		String commentString = "!";
		log.info("User verified: to read text: " + textName);
		upload = input.uploadFile(upload, br);
		try {
			ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(upload, commentString);
			ListOfTransportProperties lst = new ListOfTransportProperties();
			lst.parse(keyword, chemkinstring);
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
