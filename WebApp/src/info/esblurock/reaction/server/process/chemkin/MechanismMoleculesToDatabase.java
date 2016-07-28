package info.esblurock.reaction.server.process.chemkin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import info.esblurock.react.mechanisms.chemkin.ChemkinElementList;
import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.react.mechanisms.chemkin.ChemkinMoleculeList;
import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.chemical.elements.ChemicalElementListData;
import info.esblurock.reaction.data.chemical.molecule.GenerateMoleculeKeywords;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeData;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.data.transaction.chemkin.MechanismMoleculesToDatabaseTransaction;
import info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload;
import info.esblurock.reaction.data.upload.types.ValidatedChemkinMechanismFile;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class MechanismMoleculesToDatabase extends ProcessBase {
	String delimitor = "#";
	
	String validateS;
	String toDatabaseS;
	String uploadS;
	
	ChemkinMechanismFileUpload upload;
	MechanismMoleculesToDatabaseTransaction moltransaction;

	HashMap<String, String> moleculeMap;
	ArrayList<MechanismMoleculeData> moleculeList;

	public MechanismMoleculesToDatabase() {
		super();
	}

	public MechanismMoleculesToDatabase(ProcessInputSpecificationsBase input) {
		super(input);
	}

	@Override
	protected String getProcessName() {
		return "MechanismMoleculesToDatabase";
	}

	@Override
	protected String getProcessDescription() {
		return "Store mechanism molecules to database";
	}
	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		ArrayList<String> input = new ArrayList<String>();
		input.add(validateS);
		input.add(uploadS);
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
		moltransaction = new MechanismMoleculesToDatabaseTransaction(user, outputSourceCode, keyword,0);
		objectOutputs.add(moltransaction);
	}

	@Override
	protected void createObjects() throws IOException {
		upload = (ChemkinMechanismFileUpload) getInputSource(uploadS);
		moleculeMap = new HashMap<String, String>();
		String commentString = "!";
		StoreObject store = new StoreObject(user, keyword, outputSourceCode);
		ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(upload, commentString);
		ChemkinMechanism mechanism = new ChemkinMechanism();
		mechanism.parse(chemkinstring, commentString);
		
		ChemkinElementList elementList= mechanism.getElementList();
		ArrayList<String> lst = new ArrayList<String>();
		Set<String> keys = elementList.keySet();
		for(String key : keys) {
			lst.add(key);
		}
		ChemicalElementListData elements = new ChemicalElementListData(keyword,lst);
		store.store(elements);
		
		ArrayList<MechanismMoleculeData> mollist = create(mechanism.getSpeciesList());
		for (MechanismMoleculeData mol : mollist) {
			DatabaseObject obj = (DatabaseObject) mol;
			store.store(obj);
		}
		store.flushStore();
		moltransaction.setMoleculeCount(mollist.size());
		moltransaction.setMoleculeMap(moleculeMap);
	}

	@Override
	public void initialization() {
		uploadS = "info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload";
		validateS = "info.esblurock.reaction.data.upload.types.ValidatedChemkinMechanismFile";
		toDatabaseS = "info.esblurock.reaction.data.transaction.chemkin.MechanismMoleculesToDatabaseTransaction";
	}
	public ArrayList<MechanismMoleculeData> create(ChemkinMoleculeList speciesList) {
		ArrayList<MechanismMoleculeData> moleculeList = new ArrayList<MechanismMoleculeData>();
		Set<String> keys = speciesList.keySet();
		for (String key : keys) {
			ChemkinMolecule molecule = speciesList.get(key);
			MechanismMoleculeData mol = new MechanismMoleculeData(user, molecule.getLabel(), keyword);
			String molname = GenerateMoleculeKeywords.getDataKeyword(mol);
			moleculeList.add(mol);
			moleculeMap.put(mol.getMoleculeName(), molname);			
		}
		return moleculeList;
	}

}
