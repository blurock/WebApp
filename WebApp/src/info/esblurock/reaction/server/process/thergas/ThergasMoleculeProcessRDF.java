package info.esblurock.reaction.server.process.thergas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.chemical.thermo.ThergasMoleculeData;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.data.transaction.thergas.ThergasMoleculeThermodynamics;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.queries.QueryBase;

public class ThergasMoleculeProcessRDF extends ProcessBase {
	String toDatabaseS;
	String rdfTransactionS;

	static public String tableSource = "ThermodynamicTableSource";
	static public String nancyStructureString = "NancyStructureString";
	static public String opticSymmetry = "OpticSymmetry";
	static public String internalSymmetry = "InternalSymmetry";
	static public String externalSymmetry = "ExternalSymmetry";
	static public String carbonSet = "CarbonSet";
	static public String isomer = "Isomer";
	static public String standardEnthalpy = "StandardEnthalpy";
	static public String standardEntropy = "StandardEntropy";
	static public String moleculeName = "MoleculeName";
	static public String heatCapacity = "HeatCapacity";
	
	public ThergasMoleculeProcessRDF() {
	}
	public ThergasMoleculeProcessRDF(ProcessInputSpecificationsBase input) {
		super(input);
	}

	@Override
	public void initialization() {
		toDatabaseS = "info.esblurock.reaction.data.transaction.thergas.ThergasMoleculesToDatabaseTransaction";
		rdfTransactionS = "info.esblurock.reaction.data.transaction.thergas.ThergasMoleculesRDFTransaction";
	}

	@Override
	protected String getProcessName() {
		return "ThergasMoleculeProcessRDF";
	}

	@Override
	protected String getProcessDescription() {
		return "Store RDFs for thergas molecules";
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
	protected void createObjects() throws IOException {
		List<DatabaseObject> structureobjs = 
				QueryBase.getDatabaseObjectsFromSingleProperty(ThergasMoleculeData.class.getName(), 
						"datasetKeyword", keyword);
		List<DatabaseObject> thermoobjs = 
				QueryBase.getDatabaseObjectsFromSingleProperty(ThergasMoleculeThermodynamics.class.getName(), 
						"datasetKeyword", keyword);
		StoreObject store = new StoreObject(user,keyword, outputSourceCode);
		for(DatabaseObject obj : structureobjs) {
			ThergasMoleculeData structure = (ThergasMoleculeData) obj;
			store.setKeyword(structure.getMoleculeName().trim());
			store.storeStringRDF(tableSource, structure.getTableName());
			store.storeStringRDF(nancyStructureString,structure.getNancyStructureString());
			store.storeStringRDF(internalSymmetry,structure.getInternalSymmetry().toString());
			store.storeStringRDF(externalSymmetry,structure.getExternalSymmetry().toString());
			store.storeStringRDF(opticSymmetry,structure.getOpticSymmetry().toString());
			store.storeStringRDF(moleculeName,structure.getMoleculeName());
			store.storeStringRDF(carbonSet,getCarbonSet(structure));
			store.storeStringRDF(isomer,getIsomer(structure));			
		}
		for(DatabaseObject obj : thermoobjs) {
			ThergasMoleculeThermodynamics thermo = (ThergasMoleculeThermodynamics) obj;
			store.setKeyword(thermo.getMoleculeName().trim());
			store.storeStringRDF(standardEnthalpy, thermo.getStandardEnthalpy().toString());
			store.storeStringRDF(standardEntropy, thermo.getStandardEntropy().toString());
			store.storeStringRDF(heatCapacity, getHeatCapacity(thermo));
		}
		store.flushStore();
	}
	private String getHeatCapacity(ThergasMoleculeThermodynamics thermo) {
		ArrayList<Double> cpValues = thermo.getCpValues();
		StringBuilder build = new StringBuilder();
		String temperatures = "300,400,500,600,800,1000,1500";
		StringTokenizer tok = new StringTokenizer(temperatures,",");
		boolean first = true;
		for(Double cp:cpValues) {
			if(first) {
				first = false;
			} else {
				build.append(",");
			}
			String temperature = tok.nextToken();
			build.append(temperature);
			build.append(":");
			build.append(cp.toString());
		}
		return build.toString();
	}
	private String getIsomer(ThergasMoleculeData structure) {
		ArrayList<Integer> atomCounts = structure.getAtomCounts();
		ArrayList<String> atomNames = structure.getAtomNames();
		Iterator<Integer> countiter = atomCounts.iterator();
		Iterator<String> nameiter = atomNames.iterator();
		StringBuilder build = new StringBuilder();
		while(countiter.hasNext()) {
			String name = nameiter.next();
			Integer count = countiter.next();
			build.append(name);
			if(count.intValue() != 1) {
				build.append(count.toString());
			}
		}
		return build.toString().toLowerCase();
	}
	private String getCarbonSet(ThergasMoleculeData structure) {
		ArrayList<Integer> atomCounts = structure.getAtomCounts();
		ArrayList<String> atomNames = structure.getAtomNames();
		Iterator<Integer> countiter = atomCounts.iterator();
		Iterator<String> nameiter = atomNames.iterator();
		String carbonset = null;
		while(countiter.hasNext() && carbonset != null) {
			String name = nameiter.next();
			Integer count = countiter.next();
			if(name.matches("C")) {
				carbonset = "C" + count.toString();
			}
		}
		if(carbonset == null) {
			carbonset = "C0";
		}
		return carbonset;
	}

}
