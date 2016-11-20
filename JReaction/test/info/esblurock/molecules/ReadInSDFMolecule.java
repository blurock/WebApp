package info.esblurock.molecules;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.junit.Test;
import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.exception.CDKException;
import org.xmlcml.cml.element.CMLMolecule;
import org.xmlcml.cml.element.CMLName;

import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.info.react.data.molecules.CML.CMLReactMolecule;
import info.esblurock.info.react.data.molecules.isomers.Isomer;
import info.esblurock.react.mechanisms.BRS.BRSMechanism;
import info.esblurock.react.parse.nlp.utilities.ReadTextFileToString;
import thermo.data.structure.structure.AtomCounts;
import thermo.data.structure.structure.StructureAsCML;

public class ReadInSDFMolecule {

	@Test
	public void test() {
		File sampleFile = new File("test/resource/moleculesdf.sdf");
		ReadTextFileToString read = new ReadTextFileToString();
		read.read(sampleFile);
		// System.out.println(read.outputString);

		BRSMechanism mech = new BRSMechanism();
		try {
			mech.readMolecules(sampleFile);
			Set<String> keys = mech.Molecules.keySet();
			System.out.println(keys);
			for (String key : keys) {
				ReactMolecule molecule = (ReactMolecule) mech.Molecules.get(key);
				System.out.println("'" + key + "' -------------------------------------");
				System.out.println(molecule.toString());
				System.out.println("cml begin-------------------------------------");
				CMLReactMolecule cmlmol = new CMLReactMolecule(molecule);
				AtomContainer atm = cmlmol.toCML();
				StructureAsCML cmlstruct = new StructureAsCML(atm);
				CMLMolecule mol = cmlstruct.getCMLMolecule();
				CMLName name = new CMLName();
				name.setConvention("IUPAC");
				name.setAttribute("IUPAC", "butame");
				mol.appendChild(name);
				System.out.println("toCML(): " + mol.toXML());

				System.out.println("cml end1  -------------------------------------");
				byte[] bytes = cmlmol.restore();
				System.out.println(new String());
				System.out.println("cml end2  -------------------------------------");
				AtomCounts counts = new AtomCounts(atm);
				Isomer isomer = new Isomer(counts);
				System.out.println("Isomer: " + isomer.getName());
				System.out.println("cml end2  -------------------------------------");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
