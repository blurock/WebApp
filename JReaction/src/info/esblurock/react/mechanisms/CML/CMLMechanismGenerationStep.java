/*
 * CMLMechanismGenerationStep.java
 *
 * Created on February 26, 2004, 2:55 PM
 */

package info.esblurock.react.mechanisms.CML;
import info.esblurock.react.common.IRestorableElement;
import info.esblurock.react.common.ReactionLog;
import info.esblurock.CML.generated.Mechanism;
import info.esblurock.CML.generated.MechanismComponent;
import info.esblurock.CML.generated.Molecule;
import info.esblurock.CML.generated.MoleculeList;
import info.esblurock.CML.generated.ObjectFactory;
import info.esblurock.CML.generated.Reaction;
import info.esblurock.CML.generated.ReactionList;
import info.esblurock.CML.generated.ReactionRoleType;
import info.esblurock.react.common.SProperties;
import info.esblurock.react.mechanisms.*;
import info.esblurock.react.reactions.CML.ICMLReactionConstants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;

import java.io.*;

/**
 *
 * @author  moliate
 */
public class CMLMechanismGenerationStep extends ReactMechanismGenerationStep implements IRestorableElement, ICMLReactionConstants
{
    

    
    public void parse(byte[] data) throws java.text.ParseException 
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try
        {           
                JAXBContext jc = JAXBContext.newInstance(SProperties.getProperty("reaction.cml.root"));
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                //unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                MechanismComponent reaction = (MechanismComponent) unmarshaller.unmarshal(bis);
                fromCML(reaction);

        } 
        catch (Exception e) { 
            ReactionLog.logError(e.toString());
              return;
        }  
    }
    
    public byte[] restore() 
    {       
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try
        {               
                MechanismComponent reaction = toCML();   
                JAXBContext jc = JAXBContext.newInstance(reaction.getClass().getPackage().getName());
                Marshaller marshaller = jc.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                marshaller.marshal(reaction, new PrintStream(bos) );
        } 
        catch (Exception e) 
            { return e.toString().getBytes(); }  
        
        return bos.toString().getBytes();
    }
    
    public MechanismComponent toCML()
    {
        try
        {
            ObjectFactory factory = new ObjectFactory();
            MechanismComponent mce = factory.createMechanismComponent();
            
            //re.setRole(CONST_ROLE_STEP);
            mce.setId("r"+index);
            mce.setTitle("Step " + index);

            ReactionList rle = new ReactionList();
            for (int i = 0; i < Patterns.length; i++) {
                Reaction re = factory.createReaction();
                re.setId("s"+i);
                ReactionRoleType type;
                re.setRole(ReactionRoleType.STEP);
                re.setTitle(Patterns[i]);
                rle.getAnyCmlOrAnyOrAny().add(re);
            }
            mce.getAnyCmlOrAnyOrAny().add(rle);
            MoleculeList mle = factory.createMoleculeList();
            for (int i = 0; i < stepMolecules.length; i++) {
                Molecule me = factory.createMolecule();
                me.setId("m"+i);
                me.setTitle(stepMolecules[i]);
                mle.getAnyCmlOrAnyOrAny().add(me);
            }
            mce.getAnyCmlOrAnyOrAny().add(mle);
            
            return mce; 
        }
        catch (Exception e)  { 
            ReactionLog.logError(e.toString());
            return null;
        } 

    }
    
    public void fromCML(MechanismComponent mce) {
        index = Integer.parseInt( mce.getId().substring(1) ); 
             
        for(int i=0; i< mce.getAnyCmlOrAnyOrAny().size(); i++) {
            Object o = mce.getAnyCmlOrAnyOrAny().get(i);
            if(o instanceof ReactionList) {
                ReactionList rlist = (ReactionList) o;
                int n = rlist.getAnyCmlOrAnyOrAny().size();
                Patterns = new String[n]; 
                for (int ir = 0; ir < rlist.getAnyCmlOrAnyOrAny().size(); ir++) {
                    Patterns[ir] = ((Reaction) ( rlist.getAnyCmlOrAnyOrAny().get(i) )).getTitle();
                }
            } if(o instanceof MoleculeList) {
                MoleculeList mlist = (MoleculeList) o;
                int n = mlist.getAnyCmlOrAnyOrAny().size();
                stepMolecules = new String[n];
                for (int im = 0; im < mlist.getAnyCmlOrAnyOrAny().size(); im++) {
                    stepMolecules[im] = ((Molecule) (mlist.getAnyCmlOrAnyOrAny().get(i))).getTitle();
                }
            }
        }
    }
    
}
