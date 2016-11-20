/*
 * CMLMechanismGeneration.java
 *
 * Created on February 26, 2004, 2:54 PM
 */
package info.esblurock.react.mechanisms.CML;

import info.esblurock.react.common.IRestorableElement;
import info.esblurock.react.common.ReactionLog;
import info.esblurock.react.common.SProperties;
import info.esblurock.react.mechanisms.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.xmlcml.cml.base.CMLElement;
import org.xmlcml.cml.element.CMLMechanismComponent;
import org.xmlcml.cml.element.CMLMolecule;
import org.xmlcml.cml.element.CMLMoleculeList;

import javax.xml.bind.Marshaller;

import java.io.*;

/**
 *
 * @author  moliate
 */
public class CMLMechanismGeneration extends ReactMechanismGeneration implements IRestorableElement {

    /** Creates a new instance of CMLMechanismGeneration */
    public CMLMechanismGeneration() {
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
            ReactionLog.logError(e.toString());
            return;
        }
    }

    public byte[] restore() {
            CMLMechanismComponent reaction = toCML();
            return reaction.toXML().getBytes();
    }

    public CMLMechanismComponent toCML() {
            CMLMechanismComponent me = new CMLMechanismComponent();
            me.setTitle(mechanismName);
            CMLMoleculeList mlist = new CMLMoleculeList();
            mlist.setId("MoleculeList");
            for (int i = 0; i < initialMolecules.length; i++) {
                CMLMolecule mol = new CMLMolecule();
                mol.setTitle(initialMolecules[i]);
                mol.setId("m" + i);
                mlist.appendChild(mol);
            }
            me.appendChild(mlist);

            CMLMechanismGenerationStep step = new CMLMechanismGenerationStep();
            for (int i = 0; i < Steps.length; i++) {
                step.setData(Steps[i]);
                CMLMechanismComponent reaction = step.toCML();
                reaction.setId("CMLMechanismComponent");
                me.appendChild(reaction);
            }
            return me;
    }

    public void fromCML(CMLMechanismComponent el) {
        mechanismName = el.getTitle();
        for(CMLElement element : el.getChildCMLElements()) {
            if (element.getId().matches("MoleculeList")) {
                CMLMoleculeList mlist = (CMLMoleculeList) element;
                int n = mlist.getChildCMLElements().size();
                initialMolecules = new String[n];
                int im = 0;
                for (CMLElement melement : mlist.getChildCMLElements()) {
                    CMLMolecule mol = (CMLMolecule) element;
                    initialMolecules[im++] = mol.getTitle();
                }
            }
            if (element.getId().matches("MechanismComponent")) {
                CMLMechanismComponent c = (CMLMechanismComponent) element;
                int n = c.getChildCMLElements().size();
                Steps = new CMLMechanismGenerationStep[n];
                int i = 0;
                for (CMLElement melement : c.getChildCMLElements()) {
                    CMLMechanismComponent mc = (CMLMechanismComponent) element;
                    ((CMLMechanismGenerationStep) Steps[i++]).fromCML(mc);
                }
            }
        }
    }
}
