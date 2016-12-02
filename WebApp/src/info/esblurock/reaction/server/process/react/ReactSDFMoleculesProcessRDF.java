package info.esblurock.reaction.server.process.react;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.GenerateKeywords;
import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.chemical.molecule.SDFMoleculeStructure;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.transaction.chemkin.MechanismMoleculesToDatabaseTransaction;
import info.esblurock.reaction.data.transaction.chemkin.rdf.MechanismMoleculeRDFTransaction;
import info.esblurock.reaction.data.transaction.reaction.ReactSDFMoleculesToDatabaseTransaction;
import info.esblurock.reaction.data.transaction.reaction.rdf.ReactSDFMoleculesTransactionRDF;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.process.description.RegisterDataDescription;
import info.esblurock.reaction.server.queries.QueryBase;

public class ReactSDFMoleculesProcessRDF extends ProcessBase {
	String toDatabaseS;
	String rdfTransactionS;

	ReactSDFMoleculesToDatabaseTransaction moltransaction;
	ReactSDFMoleculesTransactionRDF rdfTransaction;

	static public String dataSource = "dataSource";
	static public String sdfSet     = "SDFMoleculeSet";
	static public String SDFmoleculestructure = "SDFMoleculeStructure";
	static public String moleculeInSet = "SDFSet";
	
	static public String dataSetKeyword;
	static public String moleculeName = "MoleculeName";
	static public String inchi = "InChI";
	static public String CAS = "CAS";
	static public String SMILES = "SMILES";
	static public String NancyString = "NancySMILES";
	static public String isomerName = "Isomer";
	static public String cmlStructure = "CMLStructure";
	static public String carbonSet = "CarbonSet";

	public ReactSDFMoleculesProcessRDF() {
	}

	public ReactSDFMoleculesProcessRDF(ProcessInputSpecificationsBase specs) {
		super(specs);
	}

	@Override
	public void initialization() {
		toDatabaseS = "info.esblurock.reaction.data.transaction.reaction.ReactSDFMoleculesToDatabaseTransaction";
		rdfTransactionS = "info.esblurock.reaction.data.transaction.reaction.rdf.ReactSDFMoleculesTransactionRDF";
	}

	@Override
	protected String getProcessName() {
		return "ReactSDFMoleculesProcessRDF";
	}

	@Override
	protected String getProcessDescription() {
		return "Reaction SDF molecules process RDF";
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
		rdfTransaction = new ReactSDFMoleculesTransactionRDF(user, outputSourceCode, keyword);
		objectOutputs.add(rdfTransaction);
	}

	@Override
	protected void createObjects() throws IOException {
		DatabaseObject info = this.getInputSource(ReactSDFMoleculesToDatabaseTransaction.class.getName());
		if(info != null) {
			ReactSDFMoleculesToDatabaseTransaction transaction = (ReactSDFMoleculesToDatabaseTransaction) info;
		StoreObject store = new StoreObject(user,keyword, outputSourceCode);
		String datakey = GenerateKeywords.keywordFromDataKeyword(keyword);
		String sourcekey = GenerateKeywords.sourceFromDataKeyword(keyword);
		store.storeStringRDF(sdfSet, datakey);
		store.storeStringRDF(dataSource, sourcekey);
		store.storeStringRDF(RegisterDataDescription.dataSetType, SDFmoleculestructure);
		List<DatabaseObject> objs = QueryBase.getDatabaseObjectsFromSingleProperty(SDFMoleculeStructure.class.getName(),
				"fileCode",transaction.getFileCode());
		for(DatabaseObject obj : objs) {
			SDFMoleculeStructure molecule = (SDFMoleculeStructure) obj;
			store.storeStringRDF(moleculeInSet,molecule.getMoleculeName());
			store.setKeyword(molecule.getMoleculeName());
			if(molecule.getCmlStructure() != null) {
				store.storeStringRDF(cmlStructure, molecule.getCmlStructure());
			}
			if(molecule.getInchi() != null) {
				store.storeStringRDF(inchi, molecule.getInchi());
			}
			if(molecule.getIsomerName() != null) {
				store.storeStringRDF(isomerName, molecule.getIsomerName());
			}
			if(molecule.getSMILES() != null) {
				store.storeStringRDF(SMILES, molecule.getSMILES());
			}
			if(molecule.getCarbonSet() != null) {
				store.storeStringRDF(carbonSet, molecule.getCarbonSet());
			}
		}

		store.finish();
		} else {
			
		}
	}

}
