/*
 * CMLMechanism.java
 *
 * Created on February 22, 2004, 5:40 PM
 */

package info.esblurock.react.mechanisms.CML;

import info.esblurock.react.common.IRestorableElement;
import info.esblurock.react.common.ReactionLog;
import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.info.react.data.molecules.CML.CMLReactMolecule;
import info.esblurock.react.reactions.CML.ICMLReactionConstants;
import thermo.data.structure.structure.StructureAsCML;
import info.esblurock.react.mechanisms.ReactMechanismRxnClass;
import info.esblurock.react.mechanisms.ReactionMechanism;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.libio.cml.Convertor;
import org.xmlcml.cml.base.CMLElement;
import org.xmlcml.cml.element.CMLMechanismComponent;
import org.xmlcml.cml.element.CMLMolecule;
import org.xmlcml.cml.element.CMLMoleculeList;

import java.util.*;
import java.io.*;

/**
 *
 * @author moliate
 */
public class CMLMechanism extends ReactionMechanism implements IRestorableElement, ICMLReactionConstants {
	/** Creates a new instance of CMLMechanism */
	public CMLMechanism() {
	}

	public void parse(byte[] data) throws java.text.ParseException {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		try {
			JAXBContext jc = JAXBContext.newInstance(CMLMechanismComponent.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			// unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
			// Boolean.TRUE );
			CMLMechanismComponent mechanism = (CMLMechanismComponent) unmarshaller.unmarshal(bis);
			fromCML(mechanism);

		} catch (Exception e) {
			ReactionLog.logError("Could not parse mechanism. CML may be corrupted.");
			return;
		}
	}

	public byte[] restore() {
		CMLMechanismComponent mechanism = toCML();
		return mechanism.toXML().getBytes();
	}

	public CMLMechanismComponent toCML() {
		Convertor convert = new Convertor(true, "");
		CMLMechanismComponent comp = new CMLMechanismComponent();
		ReactionLog.logInfo("Mechanism toCML: number of molecules" + Molecules.size());
		Enumeration _moleculeEnum = Molecules.elements();
		CMLMoleculeList mollist = new CMLMoleculeList();
		mollist.setId("Molecule");
		while (_moleculeEnum.hasMoreElements()) {
			ReactMolecule molecule = (ReactMolecule) _moleculeEnum.nextElement();
			CMLReactMolecule cmlMol = new CMLReactMolecule();
			cmlMol.setData(molecule);
			ReactionLog.logInfo("Mechanism Molecule: " + molecule.getMoleculeName());
			AtomContainer m = cmlMol.toCML();
			CMLMolecule mol = convert.cdkAtomContainerToCMLMolecule(m);
			/*
			 * if (seedMolecule.equals(cmlMol.getMoleculeName()))
			 * m.setRole("mechanismSeed");
			 */
			if (null != m)
				mollist.appendChild(mol);
		}
		comp.appendChild(mollist);

		ReactionLog.logInfo("Mechanism toCML: Number of reactions" + this.rxnClasses.size());
		CMLMechanismComponent rxnlist = new CMLMechanismComponent();
		for (int i = 0; i < rxnClasses.size(); i++) {
			ReactMechanismRxnClass rxnClass = (ReactMechanismRxnClass) rxnClasses.get(i);
			CMLMechanismRxnClass cmlRxnClass = new CMLMechanismRxnClass(Molecules);
			cmlRxnClass.setData(rxnClass);
			CMLMechanismComponent mce = cmlRxnClass.toCML();
			rxnlist.appendChild(mce);
		}
		comp.appendChild(rxnlist);
		return comp;
	}

	public void fromCML(CMLMechanismComponent mechanism) {
		Convertor convert = new Convertor(true, "");
		for(CMLElement element : mechanism.getChildCMLElements()) {
				if (element.getId().matches("MoleculeList")) {
					CMLMoleculeList mlist = (CMLMoleculeList) element;
					for(CMLElement melement : mlist.getChildCMLElements()) {
						CMLMolecule mol = (CMLMolecule) melement;
						StructureAsCML cmlstruct = new StructureAsCML(mol);
						CMLReactMolecule cmlMol = new CMLReactMolecule();
						try {
							cmlMol.fromCML(cmlstruct.getMolecule());
						} catch (CDKException e) {
							e.printStackTrace();
						}
						Molecules.put(cmlMol.getMoleculeName(), cmlMol);
						String role = mol.getRole();
						ReactionLog.logError(
								((null == role) ? "molecule" : "seed molecule") + " = " + cmlMol.getMoleculeName());
						if (null != role && role.equals("mechanismSeed"))
							seedMolecule = cmlMol.getMoleculeName();
					}
				}
				if (element.getId().matches("MechanismComponent")) {
					CMLMechanismComponent mcomp = (CMLMechanismComponent) element;
					for(CMLElement celement : mcomp.getChildCMLElements()) {
						CMLMechanismComponent mce = (CMLMechanismComponent) celement;
						CMLMechanismRxnClass cmlRxnClass = new CMLMechanismRxnClass(Molecules);
						cmlRxnClass.fromCML(mce);
						fromCML(mce);
						rxnClasses.add(cmlRxnClass);
					}
				}
			}
	}

}
