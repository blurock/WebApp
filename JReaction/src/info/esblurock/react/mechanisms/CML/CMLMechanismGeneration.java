/*
 * CMLMechanismGeneration.java
 *
 * Created on February 26, 2004, 2:54 PM
 */
package info.esblurock.react.mechanisms.CML;

import info.esblurock.react.common.IRestorableElement;
import info.esblurock.react.common.ReactionLog;
import info.esblurock.CML.generated.MechanismComponent;
import info.esblurock.CML.generated.Molecule;
import info.esblurock.CML.generated.MoleculeList;
import info.esblurock.CML.generated.ObjectFactory;
import info.esblurock.react.common.SProperties;
import info.esblurock.react.mechanisms.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
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
            JAXBContext jc = JAXBContext.newInstance(SProperties.getProperty("reaction.cml.root"));
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            //unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            MechanismComponent reaction = (MechanismComponent) unmarshaller.unmarshal(bis);
            fromCML(reaction);

        } catch (Exception e) {
            ReactionLog.logError(e.toString());
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
        try {
            ObjectFactory factory = new ObjectFactory();
            MechanismComponent me = factory.createMechanismComponent();
            me.setTitle(mechanismName);
            MoleculeList mlist = new MoleculeList();
            for (int i = 0; i < initialMolecules.length; i++) {
                Molecule mol = factory.createMolecule();
                mol.setTitle(initialMolecules[i]);
                mol.setId("m" + i);
                mlist.getAnyCmlOrAnyOrAny().add(mol);
            }
            me.getAnyCmlOrAnyOrAny().add(mlist);

            CMLMechanismGenerationStep step = new CMLMechanismGenerationStep();
            for (int i = 0; i < Steps.length; i++) {
                step.setData(Steps[i]);
                MechanismComponent reaction = step.toCML();
                me.getAnyCmlOrAnyOrAny().add(reaction);
            }

            return me;
        } catch (Exception e) {
            ReactionLog.logError(e.toString());
            return null;
        }


    }

    public void fromCML(MechanismComponent el) {
        mechanismName = el.getTitle();


        for (int i = 0; i < el.getAnyCmlOrAnyOrAny().size(); i++) {
            Object o = el.getAnyCmlOrAnyOrAny().get(i);
            if (o instanceof MoleculeList) {
                MoleculeList mlist = (MoleculeList) o;
                int n = mlist.getAnyCmlOrAnyOrAny().size();
                initialMolecules = new String[n];
                for (int im = 0; im < n; im++) {
                    Molecule mol = (Molecule) mlist.getAnyCmlOrAnyOrAny().get(im);
                    initialMolecules[im] = mol.getTitle();
                }
            }
            if (o instanceof MechanismComponent) {
                MechanismComponent c = (MechanismComponent) o;
                int n = c.getAnyCmlOrAnyOrAny().size();
                Steps = new CMLMechanismGenerationStep[n];
                for (int in = 0; in < n; in++) {
                    MechanismComponent mc = (MechanismComponent) c.getAnyCmlOrAnyOrAny().get(in);
                    ((CMLMechanismGenerationStep) Steps[i]).fromCML(mc);
                }
            }
        }
    }
}
