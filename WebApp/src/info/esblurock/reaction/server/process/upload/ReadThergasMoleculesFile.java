package info.esblurock.reaction.server.process.upload;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.data.upload.types.ThergasMoleculeFileUpload;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class ReadThergasMoleculesFile extends ReadFileBaseProcess {
	public ReadThergasMoleculesFile() {
	}
	public ReadThergasMoleculesFile(ProcessInputSpecificationsBase specs) {
		super(specs);
	}
	public void initialization() {
		super.initialization();
		specificationS = "info.esblurock.reaction.data.upload.ThergasMoleculeFileSpecification";
		uploadS = "info.esblurock.reaction.data.upload.types.ThergasMoleculeFileUpload";
	}

	@Override
	protected String getProcessName() {
		return "ReadThergasMoleculesFile";
	}

	@Override
	protected String getProcessDescription() {
		return "Read in Thergas molecules from client";
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
		upload = new ThergasMoleculeFileUpload(user, textName, outputSourceCode, sourceType, 0);
		objectOutputs.add(upload);
	}
}
