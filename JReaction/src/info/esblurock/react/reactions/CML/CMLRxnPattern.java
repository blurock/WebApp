/*
 * CMLRxnPattern.java
 *
 * Created on February 4, 2004, 2:23 PM
 */
package info.esblurock.react.reactions.CML;

import info.esblurock.react.common.IRestorableElement;
import info.esblurock.CML.generated.Cml;
import info.esblurock.CML.generated.Map;
import info.esblurock.CML.generated.Molecule;
import info.esblurock.CML.generated.ObjectFactory;
import info.esblurock.CML.generated.Product;
import info.esblurock.CML.generated.ProductList;
import info.esblurock.CML.generated.Property;
import info.esblurock.CML.generated.PropertyList;
import info.esblurock.CML.generated.Reactant;
import info.esblurock.CML.generated.ReactantList;
import info.esblurock.CML.generated.Reaction;
import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.info.react.data.molecules.CML.CMLMolecule;
import info.esblurock.react.common.SProperties;
import info.esblurock.react.reactions.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author  moliate
 */
public class CMLRxnPattern extends ReactRxnPattern implements IRestorableElement, ICMLReactionConstants {

    /** Creates a new instance of CMLRxnPatterns */
    public CMLRxnPattern() {
    }

    public byte[] restore() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            Reaction constant = toCML();
            JAXBContext jc = JAXBContext.newInstance(constant.getClass().getPackage().getName());
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(constant, new PrintStream(bos));
        } catch (Exception e) {
            return e.toString().getBytes();
        }

        return bos.toString().getBytes();
    }

    public void parse(byte[] data) throws java.text.ParseException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try {
            JAXBContext jc = JAXBContext.newInstance(SProperties.getProperty("reaction.cml.root"));
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            Cml cml = (Cml) unmarshaller.unmarshal(bis);
            List list = cml.getAnyCmlOrAnyOrAny();
            Reaction constant = (Reaction) list.get(0);
            fromCML(constant);
        } catch (Exception e) {
            return;
        }
    }

    public Reaction toCML() {
        try {
            ObjectFactory factory = new ObjectFactory();
            Reaction reaction = factory.createReaction();

            // ---- set Name
            reaction.setTitle(Name);
            reaction.setId("" + Id);
            // ---- set constants
            PropertyList plist = factory.createPropertyList();

            CMLReactionConstants ff = new CMLReactionConstants();
            ff.setData(Forward);
            Property f = ff.toCML();

            plist.getAnyCmlOrAnyOrAny().add(f);
            CMLReactionConstants rr = new CMLReactionConstants();
            rr.setData(Reverse);
            Property r = rr.toCML();
            plist.getAnyCmlOrAnyOrAny().add(r);
            reaction.getAnyCmlOrAnyOrAny().add(plist);

            // ---- set correspondence array

            Iterator iter = CorrSet.iterator();
            while (iter.hasNext()) {
                //System.out.println("cac "+ iter);
                CMLAtomCorrespondence cac = new CMLAtomCorrespondence();
                cac.setData((ReactAtomCorrespondence) iter.next());
                cac.Molecule1_id = ((ReactMolecule) Reactants.elementAt(cac.Molecule1)).getID();
                cac.Molecule2_id = ((ReactMolecule) Products.elementAt(cac.Molecule2)).getID();

                reaction.getAnyCmlOrAnyOrAny().add(cac.toCML());
            }


            // ---- set reactants

            ReactantList cre_list = factory.createReactantList();
            Double cD = new Double(Reactants.size());
            cre_list.setCount(cD);
            //iter = Reactants.iterator();
            //while (iter.hasNext())
            for (int i = 0; i < Reactants.size(); i++) {
                //System.out.println("cre "+ iter);
                CMLMolecule cm = new CMLMolecule();
                cm.setData((ReactMolecule) Reactants.elementAt(i));
                cm.reaction_id = i;
                cm.is_reactant = true;
                Reactant cre = factory.createReactant();
                cre.getAnyCmlOrAnyOrAny().add(cm.toCML());
                cre_list.getAnyCmlOrAnyOrAny().add(cre);
            }
            reaction.getAnyCmlOrAnyOrAny().add(cre_list);

            // ---- set products               
            ProductList cpe_list = factory.createProductList();
            Double pD = new Double(Products.size());
            cpe_list.setCount(pD);
            //iter = Products.iterator();
            //while (iter.hasNext())
            for (int i = 0; i < Products.size(); i++) {
                //System.out.println("cpe "+ iter);
                CMLMolecule cm = new CMLMolecule();
                cm.setData((ReactMolecule) Products.elementAt(i));
                cm.reaction_id = i;
                cm.is_reactant = false;
                Product cpe = factory.createProduct();
                cpe.getAnyCmlOrAnyOrAny().add(cm.toCML());
                cpe_list.getAnyCmlOrAnyOrAny().add(cpe);
            }
            reaction.getAnyCmlOrAnyOrAny().add(cpe_list);

            return reaction;
        } catch (Exception e) {
            return null;
        }
    }

    public void fromCML(Reaction el) {
        // ---- get reaction name
        Name = el.getTitle();
        Id = Integer.parseInt(el.getId().trim());

        // ---- get constants
        java.util.List propertyList = el.getAnyCmlOrAnyOrAny();
        for (int i = 0; i < propertyList.size(); i++) {
            if (propertyList.get(i) instanceof PropertyList) {
                PropertyList properties = (PropertyList) propertyList.get(i);
                for (int ii = 0; ii < properties.getAnyCmlOrAnyOrAny().size(); ii++) {
                    Property p = (Property) properties.getAnyCmlOrAnyOrAny().get(ii);
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
            } else if (propertyList.get(i) instanceof Map) {
                Map me = (Map) propertyList.get(i);
                CMLAtomCorrespondence cac = new CMLAtomCorrespondence();
                cac.fromCML(me);
                cac.Molecule1 = Reactants.getSubstructureIndex(cac.Molecule1_id);
                cac.Molecule2 = Products.getSubstructureIndex(cac.Molecule2_id);
                CorrSet.add(cac);
            } else if (propertyList.get(i) instanceof ProductList) {
                ProductList ple = (ProductList) propertyList.get(i);
                Iterator iter2 = ple.getAnyCmlOrAnyOrAny().iterator();
                while (iter2.hasNext()) {
                    Product pe = (Product) iter2.next();
                    Molecule me = (Molecule) pe.getAnyCmlOrAnyOrAny();
                    CMLMolecule cm = new CMLMolecule();
                    cm.fromCML(me);
                    if (-1 == cm.reaction_id) {
                        Products.add(cm);
                    } else {
                        Products.add(cm.reaction_id, cm);
                    }
                }

            } else if (propertyList.get(i) instanceof ReactantList) {
                ReactantList ple = (ReactantList) propertyList.get(i);
                Iterator iter2 = ple.getAnyCmlOrAnyOrAny().iterator();
                while (iter2.hasNext()) {
                    Reactant pe = (Reactant) iter2.next();
                    Molecule me = (Molecule) pe.getAnyCmlOrAnyOrAny();
                    CMLMolecule cm = new CMLMolecule();
                    cm.fromCML(me);
                    if (-1 == cm.reaction_id) {
                        Reactants.add(cm);
                    } else {
                        Reactants.add(cm.reaction_id, cm);
                    }
                }
            }

        }

    }
}
 