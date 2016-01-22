/*
 * CMLMechanism.java
 *
 * Created on February 22, 2004, 5:40 PM
 */

package info.esblurock.react.mechanisms.CML;

import info.esblurock.react.common.IRestorableElement;
import info.esblurock.react.common.ReactionLog;
import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.info.react.data.molecules.CML.CMLMolecule;
import info.esblurock.CML.generated.Mechanism;
import info.esblurock.CML.generated.MechanismComponent;
import info.esblurock.CML.generated.Molecule;
import info.esblurock.CML.generated.MoleculeList;
import info.esblurock.CML.generated.ObjectFactory;
import info.esblurock.CML.generated.Reaction;
import info.esblurock.CML.generated.ReactionList;
import info.esblurock.react.common.SProperties;
import info.esblurock.react.reactions.CML.ICMLReactionConstants;
import info.esblurock.react.mechanisms.ReactMechanismRxnClass;
import info.esblurock.react.mechanisms.ReactionMechanism;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;

import java.util.*;
import java.io.*;
/**
 *
 * @author  moliate
 */
public class CMLMechanism extends ReactionMechanism implements IRestorableElement, ICMLReactionConstants {    
    /** Creates a new instance of CMLMechanism */
    public CMLMechanism() {
    }
    
    public void parse(byte[] data) throws java.text.ParseException 
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try
        {           
                JAXBContext jc = JAXBContext.newInstance(SProperties.getProperty("reaction.cml.root"));
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                //unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                MechanismComponent mechanism = (MechanismComponent) unmarshaller.unmarshal(bis);
                fromCML(mechanism);

        } 
        catch (Exception e) 
        { 
                ReactionLog.logError("Could not parse mechanism. CML may be corrupted."); 
                return; 
        }  
    }
    
    public byte[] restore() 
    {       
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try
        {               
                MechanismComponent mechanism = toCML();   
                JAXBContext jc = JAXBContext.newInstance(mechanism.getClass().getPackage().getName());
                Marshaller marshaller = jc.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                marshaller.marshal(mechanism, new PrintStream(bos) );
        } 
        catch (Exception e) 
        {                 
                ReactionLog.logError("Could not create cml from mechanism."); 
                return e.toString().getBytes(); 
        }  
        
        return bos.toString().getBytes();
    }
    
    public MechanismComponent toCML() {
        try {               
                ObjectFactory factory = new ObjectFactory();
                MechanismComponent comp = new MechanismComponent();
                ReactionLog.logInfo("Mechanism toCML: number of molecules" + Molecules.size());
                Enumeration _moleculeEnum = Molecules.elements();
                MoleculeList mollist = new MoleculeList();
                while ( _moleculeEnum.hasMoreElements() ) {
                    ReactMolecule molecule = (ReactMolecule)_moleculeEnum.nextElement();
                    CMLMolecule cmlMol = new CMLMolecule();
                    cmlMol.setData(molecule); 
                    ReactionLog.logInfo("Mechanism Molecule: " + molecule.getMoleculeName());
                    Molecule m = cmlMol.toCML();
                    if (seedMolecule.equals(cmlMol.getMoleculeName()))
                        m.setRole("mechanismSeed");
                    
                    if (null != m)
                        mollist.getAnyCmlOrAnyOrAny().add(m);
                }
                comp.getAnyCmlOrAnyOrAny().add(mollist);
                
                ReactionLog.logInfo("Mechanism toCML: Number of reactions" + this.rxnClasses.size());
                MechanismComponent rxnlist = new MechanismComponent();
                for (int i = 0; i < rxnClasses.size(); i++) {
                    ReactMechanismRxnClass rxnClass = (ReactMechanismRxnClass) rxnClasses.get(i);
                    CMLMechanismRxnClass cmlRxnClass = new CMLMechanismRxnClass(Molecules); 
                    cmlRxnClass.setData(rxnClass);
                    MechanismComponent mce = cmlRxnClass.toCML();
                    rxnlist.getAnyCmlOrAnyOrAny().add(mce);
                }
                comp.getAnyCmlOrAnyOrAny().add(rxnlist);
                return comp; 
        }  
        catch (Exception e) 
            { ReactionLog.logError(e.toString()); 
              return null; } 
    } 
    
    public void fromCML(MechanismComponent mechanism)
    {
        try {
            
            for (int i = 0; i < mechanism.getAnyCmlOrAnyOrAny().size(); i++) {
                Object o = mechanism.getAnyCmlOrAnyOrAny().get(i);
                if(o instanceof MoleculeList) {
                    MoleculeList mlist = (MoleculeList) o;
                    for(int im=0;im<mlist.getAnyCmlOrAnyOrAny().size();im++) {
                        Molecule mol = (Molecule) mlist.getAnyCmlOrAnyOrAny().get(im);
                        CMLMolecule cmlMol = new CMLMolecule(); 
                        cmlMol.fromCML(mol);
                        Molecules.put(cmlMol.getMoleculeName(), cmlMol); 
                        String role = mol.getRole();
                        ReactionLog.logError( ((null==role)?"molecule":"seed molecule") + " = " + cmlMol.getMoleculeName());
                        if (null != role && role.equals("mechanismSeed"))
                            seedMolecule = cmlMol.getMoleculeName();
                    }
                } if(o instanceof MechanismComponent) {
                    MechanismComponent mcomp = (MechanismComponent) o;
                    for(int ic=0; ic< mcomp.getAnyCmlOrAnyOrAny().size(); ic++) {
                        MechanismComponent mce = (MechanismComponent) mcomp.getAnyCmlOrAnyOrAny().get(i);
                        CMLMechanismRxnClass cmlRxnClass = new CMLMechanismRxnClass(Molecules); 
                        cmlRxnClass.fromCML(mce);
                        fromCML(mce);
                        rxnClasses.add(cmlRxnClass);
                    }
                }
            }
       } catch (Exception e) { return; } 
    }

    
}
