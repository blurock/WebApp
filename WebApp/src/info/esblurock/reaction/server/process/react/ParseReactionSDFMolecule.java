package info.esblurock.reaction.server.process.react;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;


import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.exception.CDKException;

import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.info.react.data.molecules.CML.CMLReactMolecule;
import info.esblurock.react.mechanisms.BRS.BRSMechanism;
import thermo.data.structure.structure.AtomCounts;
import thermo.data.structure.structure.StructureAsCML;

public class ParseReactionSDFMolecule {

	ArrayList<CMLReactMolecule> cmlmolecules;
	ArrayList<String> moleculesAsCML;
	
	
	public void parse(String datastring) throws IOException, CDKException {
		cmlmolecules = new ArrayList<CMLReactMolecule>();
		moleculesAsCML = new ArrayList<String>();
		BRSMechanism mech = new BRSMechanism();
		mech.readMolecules(datastring);
		
		Set<String> keys = mech.Molecules.keySet();
		for(String key : keys)  {
			System.out.println(" Molecule: '" + key + "'---------------------------------");
			ReactMolecule molecule = (ReactMolecule) mech.Molecules.get(key);
			CMLReactMolecule cmlmol = new CMLReactMolecule();
			cmlmol.setData(molecule);
			System.out.println("CMLMolecule-----------------------------------------------");
			System.out.println(cmlmol.toString());
			cmlmolecules.add(cmlmol);
			System.out.println("Molecule-----------------------------------------------");
			byte[] bytes = cmlmol.restore();
			System.out.println("restore-----------------------------------------------");
			String moleculeAsCML = new String(bytes);
			System.out.println("restore-----------------------------------------------");
			System.out.println("Molecule as cml: '" + moleculeAsCML + "'");
			moleculesAsCML.add(moleculeAsCML);
			System.out.println("StringAsCML-----------------------------------------------");
			StructureAsCML ascml = new StructureAsCML(key, moleculeAsCML);
			AtomContainer container = ascml.getMolecule();
			AtomCounts counts = new AtomCounts(container);
			String isomername = counts.isomerName();
			System.out.println("Isomer: " + isomername);
	}
	}
}
