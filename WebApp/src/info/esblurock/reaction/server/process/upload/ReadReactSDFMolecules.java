package info.esblurock.reaction.server.process.upload;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.data.upload.ChemkinMechanismFileSpecification;
import info.esblurock.reaction.data.upload.types.SDFMoleculesFileUpload;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class ReadReactSDFMolecules extends ReadFileBaseProcess {
	ChemkinMechanismFileSpecification specification;
	
	public ReadReactSDFMolecules() {
		
	}
	public ReadReactSDFMolecules(ProcessInputSpecificationsBase specs) {
		super(specs);
	}
	public void initialization() {
		super.initialization();
		specificationS = "info.esblurock.reaction.data.upload.SDFMoleculesFileSpecification";
		uploadS = "info.esblurock.reaction.data.upload.types.SDFMoleculesFileUpload";
	}

	@Override
	protected String getProcessName() {
		return "ReadReactSDFMolecules";
	}

	@Override
	protected String getProcessDescription() {
		return "Read in a REACT SDF from client";
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
		upload = new SDFMoleculesFileUpload(user, textName, outputSourceCode, sourceType, 0);
		objectOutputs.add(upload);
	}

}
