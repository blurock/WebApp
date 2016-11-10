package info.esblurock.reaction.server.process.upload;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.data.upload.ChemkinMechanismFileSpecification;
import info.esblurock.reaction.data.upload.types.ReactMolCorrespondencesFileUpload;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class ReadReactMolCorrespondencesFile  extends ReadFileBaseProcess {
	ChemkinMechanismFileSpecification specification;
	
	public ReadReactMolCorrespondencesFile() {	
	}
	public ReadReactMolCorrespondencesFile(ProcessInputSpecificationsBase specs) {
		super(specs);
	}

	public void initialization() {
		super.initialization();
		specificationS = "info.esblurock.reaction.data.upload.ReactMolCorrespondencesFileSpecification";
		uploadS = "info.esblurock.reaction.data.upload.types.ReactMolCorrespondencesFileUpload";
	}

	@Override
	protected String getProcessName() {
		return "ReadReactMolCorrespondencesFile";
	}

	@Override
	protected String getProcessDescription() {
		return "Read in a Molecule name correspondence file from client";
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
		System.out.println("user: " + user
				+ "\ntextName: " + textName
				+ "\noutputSourceCode: " + outputSourceCode
				+ "\nsourceType: " + sourceType);
		upload = new ReactMolCorrespondencesFileUpload(user, textName, outputSourceCode, sourceType, 0);
		objectOutputs.add(upload);
	}
}
