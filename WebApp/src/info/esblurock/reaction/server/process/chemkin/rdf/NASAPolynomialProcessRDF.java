package info.esblurock.reaction.server.process.chemkin.rdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.chemical.molecule.GenerateMoleculeKeywords;
import info.esblurock.reaction.data.chemical.molecule.isomer.CreateIsomerData;
import info.esblurock.reaction.data.chemical.thermo.NASAPolynomialData;
import info.esblurock.reaction.data.transaction.chemkin.rdf.NASAPolynomialRDFTransaction;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.queries.ChemicalMechanismDataQuery;

public class NASAPolynomialProcessRDF extends ProcessBase {
	String toDatabaseS;
	String rdfTransactionS;

	NASAPolynomialRDFTransaction rdfTransaction;


	static public String carbonSet = "CarbonSet";
	static public String isomerPredicate = "Isomer";
	static public String isomerDataPredicate = "IsomerData";
	static public String standardEnthalpy = "StandardEnthalpy";
	static public String standardEntropy = "StandardEntropy";

	public NASAPolynomialProcessRDF() {
		super();
	}

	public NASAPolynomialProcessRDF(ProcessInputSpecificationsBase input) {
		super(input);
	}

	@Override
	public void initialization() {
		toDatabaseS = "info.esblurock.reaction.data.transaction.chemkin.NASAPolynomialsToDatabaseTransaction";
		rdfTransactionS = "info.esblurock.reaction.data.transaction.chemkin.rdf.NASAPolynomialRDFTransaction";
	}

	@Override
	protected String getProcessName() {
		return "NASAPolynomialProcessRDF";
	}

	@Override
	protected String getProcessDescription() {
		return "Store RDFs for NASA polynomials";
	}

	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		ArrayList<String> input = new ArrayList<String>();
		input.add(toDatabaseS);
		return input;
	}

	@Override
	protected ArrayList<String> getOutputTransactionObjectNames() {
		ArrayList<String> output = new ArrayList<String>();
		output.add(rdfTransactionS);
		return output;
	}

	@Override
	protected void initializeOutputObjects() {
		super.initializeOutputObjects();
		rdfTransaction = new NASAPolynomialRDFTransaction(user, outputSourceCode, keyword);
		objectOutputs.add(rdfTransaction);
	}

	@Override
	protected void createObjects() throws IOException {
		StoreObject store = new StoreObject(user, keyword, outputSourceCode);
		List<DatabaseObject> nasalist = ChemicalMechanismDataQuery.nasaPolynomialsFromMechanismName(keyword);
		for (DatabaseObject object : nasalist) {
			NASAPolynomialData data = (NASAPolynomialData) object;
			String standard = CreateIsomerData.standardIsomerName(data.getMoleculeComposition());
			String fullname = GenerateMoleculeKeywords.generateKeyword(keyword, data.getMoleculeName());
			store.setKeyword(fullname);
			store.storeStringRDF(isomerPredicate, standard);
			if (data.getStandardEnthalpy() != null)
				store.storeStringRDF(standardEnthalpy, data.getStandardEnthalpy().toString());
			if (data.getStandardEntropy() != null)
				store.storeStringRDF(standardEntropy, data.getStandardEntropy().toString());
			String isomerC = CreateIsomerData.carbonS.toUpperCase();
			Integer nCI = data.getMoleculeComposition().getAtomCount(CreateIsomerData.carbonS);
			if (nCI != null) {
				isomerC += +nCI.intValue();
			} else {
				isomerC += "0";
			}
			store.storeStringRDF(carbonSet, isomerC);
		}
		rdfTransaction.setRdfCount(store.getRdfCount());
		store.flushStore();
	}

}
