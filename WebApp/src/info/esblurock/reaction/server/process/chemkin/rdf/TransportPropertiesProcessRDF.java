package info.esblurock.reaction.server.process.chemkin.rdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.esblurock.reaction.client.GenerateKeywords;
import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.chemical.molecule.GenerateMoleculeKeywords;
import info.esblurock.reaction.data.chemical.transport.SpeciesTransportProperty;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.data.transaction.chemkin.rdf.TransportPropertiesRDFTransaction;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.queries.ChemicalMechanismDataQuery;

public class TransportPropertiesProcessRDF extends ProcessBase {
	String toDatabaseS;
	String rdfTransactionS;
	
	String transportS = "TransportProperties";
	String dipoleS = "Dipole";
	String polarizabilityS = "Polarizability";
	

	TransportPropertiesRDFTransaction rdfTransaction;	
	
	public TransportPropertiesProcessRDF() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransportPropertiesProcessRDF(ProcessInputSpecificationsBase input) {
		super(input);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialization() {
		toDatabaseS = "info.esblurock.reaction.data.transaction.chemkin.TransportPropertiesToDatabaseTransaction";
		rdfTransactionS = "info.esblurock.reaction.data.transaction.chemkin.rdf.TransportPropertiesRDFTransaction";
	}

	@Override
	protected String getProcessName() {
		return "TransportPropertiesProcessRDF";
	}

	@Override
	protected String getProcessDescription() {
		return "Store RDFs for transport properties";
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
		rdfTransaction = new TransportPropertiesRDFTransaction(user, outputSourceCode, keyword);
		objectOutputs.add(rdfTransaction);
	}

	@Override
	protected void createObjects() throws IOException {
		StoreObject store = new StoreObject(user,keyword, outputSourceCode);
		List<DatabaseObject> transportlist = ChemicalMechanismDataQuery.transportPropertiesFromMechanismName(keyword);
		for(DatabaseObject object : transportlist) {
			SpeciesTransportProperty data = (SpeciesTransportProperty) object;
			String propsS = propertiesToString(data);
			String fullname = GenerateKeywords.generateMoleculeKeyword(data.getMechanismKeyword(), data.getSpeciesName());
			store.setKeyword(fullname);
			store.storeStringRDF(transportS, propsS);
			store.storeStringRDF(dipoleS, data.getDipole());
			store.storeStringRDF(polarizabilityS, data.getPolarizability());
		}
		store.flushStore();
		rdfTransaction.setRdfCount(store.getRdfCount());
	}

	private String propertiesToString(SpeciesTransportProperty data) {
		StringBuffer buf = new StringBuffer();
		buf.append("[ ");
		buf.append(data.getCollisionDiameter());
		buf.append(", ");
		buf.append(data.getCollisionNumber());
		buf.append(", ");
		buf.append(data.getDipole());
		buf.append(", ");
		buf.append(data.getGeometricIndex());
		buf.append(", ");
		buf.append(data.getPolarizability());
		buf.append(", ");
		buf.append(data.getPotentialWellDepth());
		buf.append(" ]");
		return buf.toString();
	}

}
