/*
 * CMLMechanismRxnClass.java
 *
 * Created on February 22, 2004, 4:38 PM
 */
package info.esblurock.react.mechanisms.CML;

import info.esblurock.react.common.IRestorableElement;
import info.esblurock.react.common.SProperties;
import info.esblurock.react.mechanisms.*;
import info.esblurock.react.reactions.CML.CMLReactionConstants;
import info.esblurock.react.reactions.CML.ICMLReactionConstants;

import java.io.ByteArrayInputStream;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.xmlcml.cml.base.CMLElement;
import org.xmlcml.cml.element.CMLMechanismComponent;
import org.xmlcml.cml.element.CMLName;
import org.xmlcml.cml.element.CMLProperty;
import org.xmlcml.cml.element.CMLPropertyList;
import org.xmlcml.cml.element.CMLReaction;
import org.xmlcml.cml.element.CMLReactionList;

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
            JAXBContext jc = JAXBContext.newInstance(CMLMechanismComponent.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            //unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            CMLMechanismComponent reaction = (CMLMechanismComponent) unmarshaller.unmarshal(bis);
            fromCML(reaction);

        } catch (Exception e) {
            return;
        }
    }

    public byte[] restore() {
    	CMLMechanismComponent reaction = toCML();
    	return reaction.toXML().getBytes();
   }

    public CMLMechanismComponent toCML() {
            CMLMechanismComponent mechanismComponent = new CMLMechanismComponent();


            CMLName name = new CMLName();
            name.setId("ChemicalMechanism");
            name.setConvention("IUPAC");
            name.setAttribute("IUPAC", className);
            mechanismComponent.appendChild(name);

            CMLPropertyList info = new CMLPropertyList();
            info.setId("ReactionConstants");
            info.setTitle("Reaction constants");
            CMLReactionConstants c = new CMLReactionConstants();
            c.setData(forwardConstants);
            CMLElement felement = c.toCML();
            felement.setId("ForwardConstants");
            info.appendChild(felement);
            c.setData(reverseConstants);
            CMLElement relement = c.toCML();
            relement.setId("ForwardConstants");
            info.appendChild(relement);
            mechanismComponent.appendChild(info);

            CMLReactionList reactionList = new CMLReactionList();
            reactionList.setId("ReactionList");
            for (int i = 0; i < reactions.size(); i++) {
                ReactMechanismRxn rxn = (ReactMechanismRxn) reactions.get(i);
                CMLMechanismRxn cmlRxn = new CMLMechanismRxn(molecules);
                cmlRxn.setData(rxn);
                CMLReaction reaction = cmlRxn.toCML();
                if (null != reaction) {
                    reactionList.appendChild(reaction);
                }
            }
            mechanismComponent.appendChild(reactionList);
            return mechanismComponent;
    }
    public void fromCML(CMLMechanismComponent mechanismComponent) {
        CMLReactionList reactionList = null;
        for(CMLElement element : mechanismComponent.getChildCMLElements()) {       	
            if (element.getId().matches("ReactionList")) {
                reactionList = (CMLReactionList) element;
                reactions = new Vector();
                for(CMLElement relement: reactionList.getChildCMLElements()) {
                    CMLReaction reaction = (CMLReaction) relement;
                    CMLMechanismRxn rxn = new CMLMechanismRxn(molecules);
                    rxn.fromCML(reaction);
                    reactions.add(rxn);
                }
            } else if (element.getId().matches("ReactionConstants")) {
                CMLPropertyList pl = (CMLPropertyList) element;
                for(CMLElement celement : pl.getChildCMLElements()) {
                        CMLProperty el = (CMLProperty) celement;
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
            } else if(element.getId().matches("ChemicalMechanism")) {
                CMLName name = (CMLName) element;
                className = name.getValue();

            }
        }
    }
}
