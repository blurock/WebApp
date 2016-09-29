package info.esblurock.reaction.server.process.chemkin;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.client.GenerateKeywords;
import info.esblurock.reaction.data.chemical.molecule.GenerateMoleculeKeywords;
import info.esblurock.reaction.data.chemical.molecule.isomer.CreateIsomerData;
import info.esblurock.reaction.data.chemical.molecule.isomer.IsomerData;
import info.esblurock.reaction.data.chemical.thermo.NASAPolynomialData;
import info.esblurock.reaction.data.chemical.thermo.ParseNASAPolynomialSet;
import info.esblurock.reaction.data.store.StoreDatabaseObject;
import info.esblurock.reaction.data.transaction.chemkin.NASAPolynomialsToDatabaseTransaction;
import info.esblurock.reaction.data.upload.types.NASAPolynomialFileUpload;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;
import thermo.data.benson.NASAPolynomial;
import thermo.data.benson.SetOfThermodynamicInformation;
import thermo.data.benson.ThermodynamicInformation;

public class NASAPolynomialsToDatabase extends ProcessBase {
	NASAPolynomialFileUpload upload;
	InputStreamToLineDatabase input;
	NASAPolynomialsToDatabaseTransaction nasaToDatabase;
	

	String validateS;
	String toDatabaseS;
	String uploadS;

	ArrayList<NASAPolynomialData> nasapolynomials;
	
	public NASAPolynomialsToDatabase() {
		super();
	}

	public NASAPolynomialsToDatabase(ProcessInputSpecificationsBase input) {
		super(input);
	}

	@Override
	public void initialization() {
		validateS = "info.esblurock.reaction.data.upload.types.ValidatedNASAPolynomialFile";
		uploadS     = "info.esblurock.reaction.data.upload.types.NASAPolynomialFileUpload";
		toDatabaseS = "info.esblurock.reaction.data.transaction.chemkin.NASAPolynomialsToDatabaseTransaction";
	}

	@Override
	protected String getProcessName() {
		return "NASAPolynomialsToDatabase";
	}

	@Override
	protected String getProcessDescription() {
		return "Store NASA Polynomials to database";
	}

	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		ArrayList<String> input = new ArrayList<String>();
		input.add(uploadS);
		input.add(validateS);
		return input;
	}

	@Override
	protected ArrayList<String> getOutputTransactionObjectNames() {
		ArrayList<String> output = new ArrayList<String>();
		output.add(toDatabaseS);
		return output;
	}
	@Override
	protected void initializeOutputObjects() throws IOException {
		super.initializeOutputObjects();
		nasaToDatabase = (NASAPolynomialsToDatabaseTransaction ) 
				new NASAPolynomialsToDatabaseTransaction(user, outputSourceCode, keyword,0);
		objectOutputs.add(nasaToDatabase);
	}

	@Override
	protected void createObjects() throws IOException {
		upload = (NASAPolynomialFileUpload) getInputSource(uploadS);
		String commentString = "!";
		StoreDatabaseObject store = new StoreDatabaseObject();
		ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(upload, commentString);
		ParseNASAPolynomialSet parse = new ParseNASAPolynomialSet();
		SetOfThermodynamicInformation thermoset = parse.parse(keyword, chemkinstring);
		int count = 0;
		for(ThermodynamicInformation thermo : thermoset) {
			NASAPolynomial nasa = (NASAPolynomial) thermo;
			NASAPolynomialData nasadata = create(nasa);
			store.store(nasadata);
			count++;
		}
		store.flushStore();
		nasaToDatabase.setPolynomialCount(new Integer(count));
	}
	/**
	 * Creates the NASA polynomial
	 *
	 * @param nasa the NASA polynomial that 
	 * @return the NASA polynomial data
	 */
	public NASAPolynomialData create(NASAPolynomial nasa) {
		String molname = GenerateKeywords.generateMoleculeKeyword(keyword, nasa.getName());
		CreateIsomerData createisomer = new CreateIsomerData(molname);
		IsomerData isomer = createisomer.create(nasa);
		
		
		Double enthalpy = new Double(nasa.getStandardEnthalpy298());
		Double entropy = new Double(nasa.getStandardEntropy298());
		
		
		String moleculeName = nasa.getName();
		String phase = nasa.phase;
		Double lowerT = new Double(nasa.lowerT);
		Double middleT = new Double(nasa.middleT);
		Double upperT = new Double(nasa.upperT);
		
		ArrayList<Double> upper = new ArrayList<Double>();
		for(int i=0;i<7;i++) {
			Double c = new Double(nasa.upper[i]);
			upper.add(c);
		}
		ArrayList<Double> lower = new ArrayList<Double>();
		for(int i=0;i<7;i++) {
			Double c = new Double(nasa.lower[i]);
			lower.add(c);
		}
		
		NASAPolynomialData NASA = new NASAPolynomialData(keyword, moleculeName, isomer, 
				lowerT, middleT, upperT,
				enthalpy, entropy,
				upper, lower, phase);
		return NASA;	
	}

}
