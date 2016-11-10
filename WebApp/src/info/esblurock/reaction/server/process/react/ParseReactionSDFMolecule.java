package info.esblurock.reaction.server.process.react;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.exception.CDKException;

import info.esblurock.CML.generated.Molecule;
import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.info.react.data.molecules.CML.CMLMolecule;
import info.esblurock.info.react.data.molecules.SDF.SDFMolecule;
import info.esblurock.react.mechanisms.BRS.BRSMechanism;
import thermo.data.structure.structure.AtomCounts;
import thermo.data.structure.structure.StructureAsCML;
import thermo.data.structure.structure.CML.CMLStructureType;

import org.openscience.cdk.AtomContainer;

public class ParseReactionSDFMolecule {

	ArrayList<CMLMolecule> cmlmolecules;
	ArrayList<String> moleculesAsCML;
	
	
	public void parse(String datastring) throws IOException, CDKException {
		
		cmlmolecules = new ArrayList<CMLMolecule>();
		moleculesAsCML = new ArrayList<String>();
		
		BRSMechanism mech = new BRSMechanism();

		mech.readMolecules(datastring);
		
		Set<String> keys = mech.Molecules.keySet();
		for(String key : keys)  {
			System.out.println(" Molecule: '" + key + "'---------------------------------");
			ReactMolecule molecule = (ReactMolecule) mech.Molecules.get(key);
			CMLMolecule cmlmol = new CMLMolecule();
			cmlmol.setData(molecule);
			System.out.println("CMLMolecule-----------------------------------------------");
			System.out.println(cmlmol.toString());
			cmlmolecules.add(cmlmol);
			System.out.println("Molecule-----------------------------------------------");
			Molecule mol = cmlmol.toCML();
			System.out.println(mol.toString());
			System.out.println(mol.getTitle());
			System.out.println("restore-----------------------------------------------");
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
            JAXBContext jc;
			try {
				mol = new Molecule();
				jc = JAXBContext.newInstance(mol.getClass().getPackage().getName());
		           Marshaller marshaller = jc.createMarshaller();
		            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		            marshaller.marshal(mol, System.out);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
			
			
			
			byte[] bytes = cmlmol.restore();
			System.out.println("restore-----------------------------------------------");
			String moleculeAsCML = new String(bytes);
			System.out.println("restore-----------------------------------------------");
			System.out.println("Molecule as cml: '" + moleculeAsCML + "'");
			moleculesAsCML.add(moleculeAsCML);
			System.out.println("StringAsCML-----------------------------------------------");
			StructureAsCML ascml = new StructureAsCML(key, moleculeAsCML);
			AtomContainer container = ascml.getMolecule();
			Molecule moleculeCML = cmlmol.toCML();
			AtomCounts counts = new AtomCounts(container);
			String isomername = counts.isomerName();
			System.out.println("Isomer: " + isomername);
	}
	}
}
