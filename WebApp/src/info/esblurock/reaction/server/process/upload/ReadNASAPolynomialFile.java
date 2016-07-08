package info.esblurock.reaction.server.process.upload;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.data.chemical.thermo.ParseNASAPolynomialSet;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.upload.ChemkinMechanismFileSpecification;
import info.esblurock.reaction.data.upload.NASAPolynomialFileSpecification;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.upload.types.CreateBufferedReaderForSourceFile;
import info.esblurock.reaction.data.upload.types.NASAPolynomialFileUpload;
import info.esblurock.reaction.server.StringToKeyConversion;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;
import thermo.data.benson.NASAPolynomial;
import thermo.data.benson.SetOfThermodynamicInformation;

public class ReadNASAPolynomialFile  extends ReadFileBaseProcess {
	public ReadNASAPolynomialFile() {
		super();
	}

	public ReadNASAPolynomialFile(ProcessInputSpecificationsBase specs) {
		super(specs);
		input = new InputStreamToLineDatabase();
	}
	public void initialization() {
		super.initialization();
		specificationS = "info.esblurock.reaction.data.upload.NASAPolynomialFileSpecification";	
		uploadS = "info.esblurock.reaction.data.upload.types.NASAPolynomialFileUpload";
	}

	@Override
	protected String getProcessName() {
		return "ReadNASAPolynomialFile";
	}

	@Override
	protected String getProcessDescription() {
		return "Read in a NASA polynomial from client and see if correct";
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
		upload = new NASAPolynomialFileUpload(user, textName, outputSourceCode, sourceType, 0);
		objectOutputs.add(upload);
	}
}
