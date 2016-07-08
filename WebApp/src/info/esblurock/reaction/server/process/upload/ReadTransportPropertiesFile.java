package info.esblurock.reaction.server.process.upload;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.data.upload.types.TransportFileUpload;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;

public class ReadTransportPropertiesFile extends ReadFileBaseProcess {

	public ReadTransportPropertiesFile() {
		super();
	}

	public ReadTransportPropertiesFile(ProcessInputSpecificationsBase specs) {
		super(specs);
		input = new InputStreamToLineDatabase();
	}
	public void initialization() {
		super.initialization();
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
		return super.getInputTransactionObjectNames();
	}

	@Override
	protected ArrayList<String> getOutputTransactionObjectNames() {
		return super.getOutputTransactionObjectNames();
	}
	
	@Override
	protected void initializeOutputObjects() throws IOException {
		super.initializeOutputObjects();
		upload = new TransportFileUpload(user, textName, outputSourceCode, sourceType, 0);
		objectOutputs.add(upload);
	}

}
