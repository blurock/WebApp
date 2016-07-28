package info.esblurock.reaction.server.process.chemkin.rdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.esblurock.reaction.client.GenerateKeywords;
import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.chemical.molecule.GenerateMoleculeKeywords;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeData;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.data.transaction.chemkin.MechanismMoleculesToDatabaseTransaction;
import info.esblurock.reaction.data.transaction.chemkin.rdf.MechanismMoleculeRDFTransaction;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.queries.ChemicalMechanismDataQuery;

public class MechanismMoleculeProcessRDF extends ProcessBase {
	String toDatabaseS;
	String rdfTransactionS;
	MechanismMoleculesToDatabaseTransaction moltransaction;
	MechanismMoleculeRDFTransaction rdfTransaction;
	static public String sourceMolecule = "MoleculeOfSource";
	static public String mechanismSource = "mechanismSource";
	static public String chemkinMechanism = "CHEMKINMechanism";
	static public String mechanismMolecule = "MechanismMolecule";
	static public String isMechanismMolecule = "isMechanismMolecule";
	static public String speciesName = "SpeciesName";
	static public String speciesMechDelimitor = "#";

	public MechanismMoleculeProcessRDF() {
		super();
	}

	public MechanismMoleculeProcessRDF(ProcessInputSpecificationsBase input) {
		super(input);
	}

	@Override
	public void initialization() {
		toDatabaseS = "info.esblurock.reaction.data.transaction.chemkin.MechanismMoleculesToDatabaseTransaction";
		rdfTransactionS = "info.esblurock.reaction.data.transaction.chemkin.rdf.MechanismMoleculeRDFTransaction";
	}

	@Override
	protected String getProcessName() {
		return "MechanismMoleculeProcessRDF";
	}

	@Override
	protected String getProcessDescription() {
		return "Store RDFs for mechanism molecules";
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
	protected void initializeOutputObjects() throws IOException {
		super.initializeOutputObjects();
		rdfTransaction = new MechanismMoleculeRDFTransaction(user, outputSourceCode, keyword);
		objectOutputs.add(rdfTransaction);
	}

	@Override
	protected void createObjects() throws IOException {
		StoreObject store = new StoreObject(user,keyword, outputSourceCode);
		List<DatabaseObject> moleculelist = ChemicalMechanismDataQuery.moleculesFromMechanismName(keyword);
		String datakey = GenerateKeywords.keywordFromDataKeyword(keyword);
		String sourcekey = GenerateKeywords.sourceFromDataKeyword(keyword);
		store.storeStringRDF(chemkinMechanism, datakey);
		store.storeStringRDF(mechanismSource, sourcekey);

		for (DatabaseObject object : moleculelist) {
			MechanismMoleculeData molecule = (MechanismMoleculeData) object;
			String fullname = GenerateMoleculeKeywords.getDataKeyword(molecule);
			String name = molecule.getMoleculeName().toLowerCase();
			store.setKeyword(keyword);
			store.storeStringRDF(mechanismMolecule,fullname);
			store.setKeyword(fullname);
			store.storeObjectRDF(fullname, molecule);
			store.storeStringRDF(isMechanismMolecule, name);
			store.setKeyword(sourcekey);
			store.storeStringRDF(sourceMolecule,name);
		}
		store.finish();
		rdfTransaction.setRdfCount(store.getRdfCount());
	}

}
