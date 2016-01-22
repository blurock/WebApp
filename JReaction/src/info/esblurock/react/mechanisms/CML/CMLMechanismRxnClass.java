/*
 * CMLMechanismRxnClass.java
 *
 * Created on February 22, 2004, 4:38 PM
 */
package info.esblurock.react.mechanisms.CML;

import info.esblurock.react.common.IRestorableElement;
import info.esblurock.CML.generated.MechanismComponent;
import info.esblurock.CML.generated.Name;
import info.esblurock.CML.generated.ObjectFactory;
import info.esblurock.CML.generated.Property;
import info.esblurock.CML.generated.PropertyList;
import info.esblurock.CML.generated.Reaction;
import info.esblurock.CML.generated.ReactionList;
import info.esblurock.react.common.SProperties;
import info.esblurock.react.mechanisms.*;
import info.esblurock.react.reactions.CML.CMLReactionConstants;
import info.esblurock.react.reactions.CML.ICMLReactionConstants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;

/**
 *
 * @author  moliate
 */
public class CMLMechanismRxnClass extends ReactMechanismRxnClass implements IRestorableElement, ICMLReactionConstants {

    protected Hashtable molecules;

    /** Creates a new instance of CMLMechanismRxnClass */
    public CMLMechanismRxnClass(Hashtable molecules) {
        this.molecules = molecules;
    }

    public void parse(byte[] data) throws java.text.ParseException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try {
            JAXBContext jc = JAXBContext.newInstance(SProperties.getProperty("reaction.cml.root"));
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            //unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            MechanismComponent reaction = (MechanismComponent) unmarshaller.unmarshal(bis);
            fromCML(reaction);

        } catch (Exception e) {
            return;
        }
    }

    public byte[] restore() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            MechanismComponent reaction = toCML();
            JAXBContext jc = JAXBContext.newInstance(reaction.getClass().getPackage().getName());
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(reaction, new PrintStream(bos));
        } catch (Exception e) {
            return e.toString().getBytes();
        }

        return bos.toString().getBytes();
    }

    public MechanismComponent toCML() {
           ObjectFactory factory = new ObjectFactory();
            MechanismComponent mechanismComponent = factory.createMechanismComponent();


            Name name = factory.createName();
            name.setConvention("IUPAC");
            name.setValue(className);
            mechanismComponent.getAnyCmlOrAnyOrAny().add(name);



            PropertyList info = factory.createPropertyList();
            info.setTitle("Reaction constants");
            //PropertyList constants = factory.createPropertyList(); 
            CMLReactionConstants c = new CMLReactionConstants();
            c.setData(forwardConstants);
            info.getAnyCmlOrAnyOrAny().add(c.toCML());
            c.setData(reverseConstants);
            info.getAnyCmlOrAnyOrAny().add(c.toCML());
            mechanismComponent.getAnyCmlOrAnyOrAny().add(info);

            ReactionList reactionList = factory.createReactionList();
            for (int i = 0; i < reactions.size(); i++) {
                ReactMechanismRxn rxn = (ReactMechanismRxn) reactions.get(i);
                CMLMechanismRxn cmlRxn = new CMLMechanismRxn(molecules);
                cmlRxn.setData(rxn);
                Reaction reaction = cmlRxn.toCML();
                if (null != reaction) {
                    reactionList.getAnyCmlOrAnyOrAny().add(reaction);
                }
            }
            mechanismComponent.getAnyCmlOrAnyOrAny().add(reactionList);

            return mechanismComponent;
    }
    public void fromCML(MechanismComponent mechanismComponent) {
        ReactionList reactionList = null;
        System.out.println("CMLMechanismRxnClass.fromCML " + mechanismComponent.getAnyCmlOrAnyOrAny().size());
        for (int i = 0; i < mechanismComponent.getAnyCmlOrAnyOrAny().size(); i++) {
            Object o = mechanismComponent.getAnyCmlOrAnyOrAny().get(i);
            if (o instanceof ReactionList) {
                reactionList = (ReactionList) o;
                java.util.List Reactions = reactionList.getAnyCmlOrAnyOrAny();
                reactions = new Vector();
                for (int j = 0; j < Reactions.size(); j++) {
                    Reaction reaction = (Reaction) Reactions.get(j);
                    CMLMechanismRxn rxn = new CMLMechanismRxn(molecules);
                    rxn.fromCML(reaction);
                    reactions.add(rxn);
                }
            } else if (o instanceof PropertyList) {
                PropertyList pl = (PropertyList) o;
                java.util.List PoO = pl.getAnyCmlOrAnyOrAny();
                for (int iii = 0; iii < PoO.size(); iii++) {
                    if (PoO.get(iii) instanceof Property) {
                        Property el = (Property) PoO.get(iii);
                        if (el.getDictRef().equals(CONST_DICTREF_COMBUSTIONCONSTANTS) &&
                                el.getRole().equals(CONST_ROLE_FORWARD)) {
                            CMLReactionConstants c = new CMLReactionConstants();
                            c.fromCML(el);
                            forwardConstants = c;
                        } else if (el.getDictRef().equals(CONST_DICTREF_COMBUSTIONCONSTANTS) &&
                                el.getRole().equals(CONST_ROLE_REVERSE)) {
                            CMLReactionConstants c = new CMLReactionConstants();
                            c.fromCML(el);
                            reverseConstants = c;
                        }
                    }
                }
            } else if (o instanceof Name) {
                Name name = (Name) o;
                className = name.getValue();

            }
        }
    }
}
