/*
 * CMLMechanismGenerationStep.java
 *
 * Created on February 26, 2004, 2:55 PM
 */

package info.esblurock.react.mechanisms.CML;
import info.esblurock.react.common.IRestorableElement;
import info.esblurock.react.common.ReactionLog;
import info.esblurock.react.common.SProperties;
import info.esblurock.react.mechanisms.*;
import info.esblurock.react.reactions.CML.ICMLReactionConstants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.xmlcml.cml.base.CMLElement;
import org.xmlcml.cml.element.CMLMechanismComponent;
import org.xmlcml.cml.element.CMLMolecule;
import org.xmlcml.cml.element.CMLMoleculeList;
import org.xmlcml.cml.element.CMLReaction;
import org.xmlcml.cml.element.CMLReactionList;

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
                JAXBContext jc = JAXBContext.newInstance(CMLMechanismComponent.class);
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                CMLMechanismComponent reaction = (CMLMechanismComponent) unmarshaller.unmarshal(bis);
                fromCML(reaction);

        } 
        catch (Exception e) { 
            ReactionLog.logError(e.toString());
              return;
        }  
    }
    
    public byte[] restore()  {       
                CMLMechanismComponent reaction = toCML();   
                return reaction.toXML().getBytes();
    }
    
    public CMLMechanismComponent toCML() {
            CMLMechanismComponent mce = new CMLMechanismComponent();
            
            //re.setRole(CONST_ROLE_STEP);
            mce.setId("r"+index);
            mce.setTitle("Step " + index);

            CMLReactionList rle = new CMLReactionList();
            rle.setDictRef("ReactionList");
            for (int i = 0; i < Patterns.length; i++) {
                CMLReaction re = new CMLReaction();
                re.setId("s"+i);
                re.setRole("Step");
                re.setTitle(Patterns[i]);
                rle.appendChild(re);
            }
            mce.appendChild(rle);
            CMLMoleculeList mle = new CMLMoleculeList();
            mle.setDictRef("MoleculeList");
            for (int i = 0; i < stepMolecules.length; i++) {
                CMLMolecule me = new CMLMolecule();
                me.setId("m"+i);
                me.setTitle(stepMolecules[i]);
                mle.appendChild(me);
            }
            mce.appendChild(mle);
            
            return mce; 

    }
    
    public void fromCML(CMLMechanismComponent mce) {
        index = Integer.parseInt( mce.getId().substring(1) ); 
        for(CMLElement element : mce.getChildCMLElements()) {
            if(element.getId().matches("ReactionList")) {
                CMLReactionList rlist = (CMLReactionList) element;
                int n = rlist.getChildCMLElements().size();
                Patterns = new String[n];
                int ir = 0;
                for(CMLElement relement : rlist.getChildCMLElements()) {
                	CMLReaction reaction = (CMLReaction) relement;
                    Patterns[ir++] = reaction.getTitle();
                }
            } if(element.getId().matches("MoleculeList")) {
                CMLMoleculeList mlist = (CMLMoleculeList) element;
                int n = mlist.getChildCMLElements().size();
                stepMolecules = new String[n];
                int im = 0;
                for(CMLElement melement : mlist.getChildCMLElements()) {
                	CMLMolecule molecule = (CMLMolecule) melement;
                    stepMolecules[im++] = molecule.getTitle();
                }
            }
        }
    }
    
}
