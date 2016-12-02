package info.esblurock.reaction.server.process.react;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.aromaticity.Aromaticity;
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.aromaticity.ElectronDonation;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.graph.Cycles;
import org.openscience.cdk.inchi.InChIGenerator;
import org.openscience.cdk.inchi.InChIGeneratorFactory;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.xmlcml.cml.element.CMLMolecule;
import org.xmlcml.cml.element.CMLName;
import org.openscience.cdk.smiles.SmilesGenerator;

import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.info.react.data.molecules.CML.CMLReactMolecule;
import info.esblurock.info.react.data.molecules.isomers.Isomer;
import info.esblurock.react.mechanisms.BRS.BRSMechanism;
import info.esblurock.reaction.data.chemical.molecule.SDFMoleculeStructure;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.data.transaction.ActionsUsingIdentificationCode;
import info.esblurock.reaction.data.transaction.reaction.ReactSDFMoleculesToDatabaseTransaction;
import info.esblurock.reaction.data.upload.types.SDFMoleculesFileUpload;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import thermo.data.structure.structure.AtomCounts;
import thermo.data.structure.structure.StructureAsCML;

public class ReactSDFMoleculesToDatabase extends ProcessBase {
	static public String moleculeName = "MoleculeName";
	static public String moleculeStructure = "MoleculeStructure";

	String delimitor = "#";

	String validateS;
	String toDatabaseS;
	String uploadS;

	SDFMoleculesFileUpload upload;
	ReactSDFMoleculesToDatabaseTransaction moltransaction;

	public ReactSDFMoleculesToDatabase() {
		super();
	}

	public ReactSDFMoleculesToDatabase(ProcessInputSpecificationsBase input) {
		super(input);
	}

	@Override
	protected String getProcessName() {
		return "ReactSDFMoleculesToDatabase";
	}

	@Override
	protected String getProcessDescription() {
		return "Store reaction SDF molecules to database";
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
		moltransaction = new ReactSDFMoleculesToDatabaseTransaction(user, outputSourceCode, keyword, 0);
		objectOutputs.add(moltransaction);
	}

	@Override
	protected void createObjects() throws IOException {
		upload = (SDFMoleculesFileUpload) getInputSource(uploadS);
		StoreObject store = new StoreObject(user, keyword, outputSourceCode);
		String datastring = ActionsUsingIdentificationCode.getUploadedAsString(upload.getFileCode());
		BRSMechanism mech = new BRSMechanism();
		mech.readMolecules(datastring);
		Set<String> keys = mech.Molecules.keySet();
		System.out.println(keys);
		Aromaticity aromaticity = new Aromaticity(ElectronDonation.cdk(),
                Cycles.all());
		
		for (String key : keys) {
			try {
				ReactMolecule molecule = (ReactMolecule) mech.Molecules.get(key);
				CMLReactMolecule cmlmol = new CMLReactMolecule(molecule);
				AtomContainer atm = cmlmol.toCML();
				StructureAsCML cmlstruct;
				cmlstruct = new StructureAsCML(atm);
				CMLMolecule mol = cmlstruct.getCMLMolecule();
				CMLName name = new CMLName();
				name.setConvention("IUPAC");
				name.setAttribute("IUPAC", "butame");
				mol.appendChild(name);
				//byte[] bytes = cmlmol.restore();
				AtomCounts counts = new AtomCounts(atm);
				Isomer isomer = new Isomer(counts);
				Integer carbonI = counts.get("C");
				
				SDFMoleculeStructure data = new SDFMoleculeStructure(keyword, cmlstruct.getNameOfStructure(),
						cmlstruct.getCmlStructureString());
				data.setIsomerName(isomer.getName());
				String carbonset = "C" + carbonI.toString();
				data.setCarbonSet(carbonset);
				InChIGeneratorFactory inchifactory = InChIGeneratorFactory.getInstance();
				InChIGenerator gen = inchifactory.getInChIGenerator(atm);
				String inchi = gen.getInchi();
				data.setInchi(inchi);
				
				SmilesGenerator sg      = new SmilesGenerator();
				String smiles = sg.create(atm);
				data.setKey(smiles);
				boolean aromatic = aromaticity.apply(atm);

				store.store(data);
			} catch (CDKException e) {
				throw new IOException("Error in processing molecule: " + key);
			}
		}
		store.flushStore();
	}

	@Override
	public void initialization() {
		uploadS = "info.esblurock.reaction.data.upload.types.SDFMoleculesFileUpload";
		validateS = "info.esblurock.reaction.data.upload.types.ValidatedReactSDFMoleculesFile";
		toDatabaseS = "info.esblurock.reaction.data.transaction.reaction.ReactSDFMoleculesToDatabaseTransaction";
	}
}
