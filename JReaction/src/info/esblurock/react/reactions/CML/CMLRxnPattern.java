/*
 * CMLRxnPattern.java
 *
 * Created on February 4, 2004, 2:23 PM
 */
package info.esblurock.react.reactions.CML;

import info.esblurock.react.common.IRestorableElement;
import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.info.react.data.molecules.CML.CMLReactMolecule;
import info.esblurock.react.reactions.*;
import thermo.data.structure.structure.StructureAsCML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.libio.cml.Convertor;
import org.xmlcml.cml.base.CMLElement;
import org.xmlcml.cml.element.CMLMolecule;
import org.xmlcml.cml.element.CMLProduct;
import org.xmlcml.cml.element.CMLProductList;
import org.xmlcml.cml.element.CMLProperty;
import org.xmlcml.cml.element.CMLPropertyList;
import org.xmlcml.cml.element.CMLReactant;
import org.xmlcml.cml.element.CMLReactantList;
import org.xmlcml.cml.element.CMLReaction;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
/**
 *
 * @author  moliate
 */
public class CMLRxnPattern extends ReactRxnPattern implements IRestorableElement, ICMLReactionConstants {

    /** Creates a new instance of CMLRxnPatterns */
    public CMLRxnPattern() {
    }

    public byte[] restore() {
            CMLReaction constant = toCML();
            return constant.toXML().getBytes();
    }

    public void parse(byte[] data) throws java.text.ParseException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try {
            JAXBContext jc = JAXBContext.newInstance(CMLReaction.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            CMLReaction cml = (CMLReaction) unmarshaller.unmarshal(bis);
            fromCML(cml);
        } catch (Exception e) {
            return;
        }
    }

    public CMLReaction toCML() {
    	Convertor convert = new Convertor(true, "");
        try {
        	// First element in CMLReaction
            CMLReaction reaction = new CMLReaction();
            // ---- set Name
            reaction.setTitle(Name);
            reaction.setId("" + Id);
            // ---- set constants
            CMLPropertyList plist = new CMLPropertyList();
            plist.setId("ReactionConstants");
            CMLReactionConstants ff = new CMLReactionConstants();
            ff.setData(Forward);
            CMLElement f = ff.toCML();
            plist.appendChild(f);
            CMLReactionConstants rr = new CMLReactionConstants();
            rr.setData(Reverse);
            CMLElement r = rr.toCML();
            plist.appendChild(r);
            reaction.appendChild(plist);

            // Second element in CMLReaction
            // ---- set correspondence array
            CMLPropertyList corrlist = new CMLPropertyList();
            corrlist.setId("Correspondences");
            Iterator iter = CorrSet.iterator();
            while (iter.hasNext()) {
                //System.out.println("cac "+ iter);
                CMLAtomCorrespondence cac = new CMLAtomCorrespondence();
                cac.setData((ReactAtomCorrespondence) iter.next());
                cac.Molecule1_id = ((ReactMolecule) Reactants.elementAt(cac.Molecule1)).getID();
                cac.Molecule2_id = ((ReactMolecule) Products.elementAt(cac.Molecule2)).getID();

                corrlist.appendChild(cac.toCML());
            }
            reaction.appendChild(corrlist);

            // ---- set reactants

            CMLReactantList cre_list = new CMLReactantList();
            cre_list.setId("ReactantList");
            Double cD = new Double(Reactants.size());
            cre_list.setCount(cD);
            //iter = Reactants.iterator();
            //while (iter.hasNext())
            for (int i = 0; i < Reactants.size(); i++) {
                //System.out.println("cre "+ iter);
                CMLReactMolecule cm = new CMLReactMolecule();
                cm.setData((ReactMolecule) Reactants.elementAt(i));
                cm.reaction_id = i;
                cm.is_reactant = true;
                CMLReactant cre = new CMLReactant();
                AtomContainer atms = cm.toCML();
                CMLMolecule cmlmolecule = convert.cdkAtomContainerToCMLMolecule(atms);
                cre.appendChild(cmlmolecule); 
                cre_list.appendChild(cre);
            }
            reaction.appendChild(cre_list);

            // ---- set products               
            CMLProductList cpe_list = new CMLProductList();
            cpe_list.setId("ProductList");
            Double pD = new Double(Products.size());
            cpe_list.setCount(pD);
            //iter = Products.iterator();
            //while (iter.hasNext())
            for (int i = 0; i < Products.size(); i++) {
                //System.out.println("cpe "+ iter);
                CMLReactMolecule cm = new CMLReactMolecule();
                cm.setData((ReactMolecule) Products.elementAt(i));
                cm.reaction_id = i;
                cm.is_reactant = false;
                CMLProduct cpe = new CMLProduct();
                AtomContainer atms = cm.toCML();
                
                CMLMolecule cmlmolecule = convert.cdkAtomContainerToCMLMolecule(atms);
                cpe.appendChild(cmlmolecule);
                cpe_list.appendChild(cpe);
            }
            reaction.appendChild(cpe_list);
            return reaction;
        } catch (Exception e) {
            return null;
        }
    }

    public void fromCML(CMLReaction el) {
    	Convertor convert = new Convertor(true, "");
       // ---- get reaction name
        Name = el.getTitle();
        Id = Integer.parseInt(el.getId().trim());

        for(CMLElement cmlinfo : el.getChildCMLElements()) {
        	if(cmlinfo.getId().matches("ReactionConstants")) {
        		// ---- get constants
        		CMLPropertyList properties = (CMLPropertyList) cmlinfo;
        		for(CMLElement element : properties.getChildCMLElements()) {
        			CMLProperty p = (CMLProperty) element;
                    if (p.getRole().equals(CONST_ROLE_FORWARD)) {
                        CMLReactionConstants constant = new CMLReactionConstants();
                        constant.fromCML(p);
                        Reverse.setData(constant);
                    } else if (p.getRole().equals(CONST_ROLE_REVERSE)) {
                        CMLReactionConstants constant = new CMLReactionConstants();
                        constant.fromCML(p);
                        Forward.setData(constant);
                    }
                }
            } else if(cmlinfo.getId().matches("Correspondences")) {
            	CMLPropertyList properties = (CMLPropertyList) cmlinfo;
            	for(CMLElement element : properties.getChildCMLElements()) {
                    CMLAtomCorrespondence cac = new CMLAtomCorrespondence();
                    cac.fromCML(element);
                    cac.Molecule1 = Reactants.getSubstructureIndex(cac.Molecule1_id);
                    cac.Molecule2 = Products.getSubstructureIndex(cac.Molecule2_id);
                    CorrSet.add(cac);           		
            	}
            } else if(cmlinfo.getId().matches("ReactantList")) {
                CMLReactantList ple = (CMLReactantList) cmlinfo;
                for(CMLElement element : ple.getChildCMLElements()) {
                	CMLReactant pe = (CMLReactant) element;
                	CMLMolecule me = (CMLMolecule) pe.getChildCMLElements().get(0);
                	StructureAsCML cmlstruct = new StructureAsCML(me);
                	AtomContainer atmcontainer;
					try {
						atmcontainer = cmlstruct.getMolecule();
	                	CMLReactMolecule cm = new CMLReactMolecule();
	                	cm.fromCML(atmcontainer);
	                    if (-1 == cm.reaction_id) {
	                        Reactants.add(cm);
	                    } else {
	                        Reactants.add(cm.reaction_id, cm);
	                    }                	
					} catch (CDKException e) {
						e.printStackTrace();
					}
                }
            } else if(cmlinfo.getId().matches("ProductList")) {
        		CMLProductList ple = (CMLProductList) cmlinfo;
        		for(CMLElement element : ple.getChildCMLElements()) {
        			CMLProduct pe = (CMLProduct) element;
        			CMLMolecule me = (CMLMolecule) pe.getChildCMLElements().get(0);
                    CMLReactMolecule cm = new CMLReactMolecule();
                   	StructureAsCML cmlstruct = new StructureAsCML(me);
                	AtomContainer atmcontainer;
					try {
						atmcontainer = cmlstruct.getMolecule();
	                    cm.fromCML(atmcontainer);
	                    if (-1 == cm.reaction_id) {
	                        Products.add(cm);
	                    } else {
	                        Products.add(cm.reaction_id, cm);
	                    }
					} catch (CDKException e) {
						e.printStackTrace();
					}
        		}
            }
        }

    }
}
 