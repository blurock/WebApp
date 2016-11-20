/*
 * CMLMechanismRxn.java
 *
 * Created on February 22, 2004, 2:47 PM
 */

package info.esblurock.react.mechanisms.CML;
import info.esblurock.react.common.IRestorableElement;
import info.esblurock.react.common.ReactionLog;
import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.react.common.SProperties;
import info.esblurock.react.mechanisms.ReactMechanismRxn;
import info.esblurock.react.reactions.CML.ICMLReactionConstants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.openscience.cdk.ChemFile;
import org.openscience.cdk.ChemModel;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IChemFile;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.interfaces.IChemSequence;
import org.openscience.cdk.io.CMLReader;
import org.xmlcml.cml.base.CMLBuilder;
import org.xmlcml.cml.base.CMLElement;
import org.xmlcml.cml.element.CMLProduct;
import org.xmlcml.cml.element.CMLProductList;
import org.xmlcml.cml.element.CMLProperty;
import org.xmlcml.cml.element.CMLPropertyList;
import org.xmlcml.cml.element.CMLReactant;
import org.xmlcml.cml.element.CMLReactantList;
import org.xmlcml.cml.element.CMLReaction;
import org.xmlcml.cml.element.CMLScalar;

import javax.xml.bind.Marshaller;

import java.util.*;
import java.io.*;
import java.text.ParseException;
/**
 *
 * @author  moliate
 */
public class CMLMechanismRxn extends ReactMechanismRxn implements IRestorableElement, ICMLReactionConstants 
{
    Hashtable molecules;
    /** Creates a new instance of CMLMechanismRxn */
    public CMLMechanismRxn(Hashtable molecules) {
        this.molecules = molecules;
    }
    
    public void parse(byte[] data) throws java.text.ParseException {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		try {
			JAXBContext jc = JAXBContext.newInstance(CMLReaction.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			CMLReaction link = (CMLReaction) unmarshaller.unmarshal(bis);
			fromCML(link);
		} catch (Exception e) {
			throw new ParseException(e.toString(), 0);
		}
    }
    
    public byte[] restore() {
    	
		CMLReaction link = toCML();
		return link.toXML().getBytes();
    }
    
    public CMLReaction toCML() {
                CMLReaction reaction = new CMLReaction();
                
                // set constants
                CMLPropertyList p = new CMLPropertyList();
                p.setId("ReactantConstants");
                // Multiplicity for reactant
                CMLProperty pe = new CMLProperty();
                pe.setDictRef(CONST_DICTREF_COMBUSTIONCONSTANTS);
                pe.setRole(CONST_ROLE_REACTANT);
                CMLScalar scalar = new CMLScalar();
                scalar.setDataType("xsd:integer");
                scalar.setTitle(CONST_MULTIPLICITY);
                scalar.setValue(""+reactantMultiplicity);
                pe.appendChild(scalar);
                p.appendChild(pe);

                // Multiplicity for products
                pe = new CMLProperty();
                pe.setDictRef(CONST_DICTREF_COMBUSTIONCONSTANTS);
                pe.setRole(CONST_ROLE_PRODUCT);
                scalar =  new CMLScalar();
                scalar.setDataType("xsd:integer");
                scalar.setTitle(CONST_MULTIPLICITY);
                scalar.setValue(""+productMultiplicity);
                pe.appendChild(scalar);
                p.appendChild(pe);
                reaction.appendChild(p);
                
                // add reactants
                CMLReactantList reactants = new CMLReactantList();
                reactants.setId("ReactantList");
                for (int i = 0; i < reactantMolecules.length; i++) {
                    CMLReactant _r = new CMLReactant();
                    _r.setCount( new Double(1.0) );
                    _r.setRef( "m" + idFromName(reactantMolecules[i]) );
                    reactants.appendChild(_r);
                }
                reaction.appendChild(reactants);
  
                // add products
                CMLProductList products = new CMLProductList();
                for (int i = 0; i < productMolecules.length; i++)
                {
                    CMLProduct _p = new CMLProduct();
                    _p.setCount( new Double(1.0) );
                    _p.setRef( "m" + idFromName(productMolecules[i]) );
                    products.appendChild(_p); 
                }
                reaction.appendChild(products);
                
                return reaction;
    }
      
    public void fromCML(CMLReaction reaction) {
    	for(CMLElement element : reaction.getChildCMLElements()) {
    		if(element.getId().matches("ReactantConstants")) {
    			CMLPropertyList props = (CMLPropertyList) element;
    			for(CMLElement propelement: props.getChildCMLElements()) {
    				CMLProperty p = (CMLProperty) element;
                    if ( CONST_DICTREF_COMBUSTIONCONSTANTS == p.getDictRef() ) {
                    	for(CMLElement pelement : p.getChildCMLElements()) {
                    		CMLScalar s = (CMLScalar) pelement;
                            if (CONST_MULTIPLICITY == s.getTitle()) {
                                int mul = Integer.parseInt( s.getValue() );
                                if ( CONST_ROLE_PRODUCT == p.getRole() )
                                    productMultiplicity = mul;
                                if ( CONST_ROLE_REACTANT == p.getRole() )
                                    reactantMultiplicity = mul;                                
                            }
                    		
                    	}
                    }
    			}
    	} else if(element.getId().matches("ReactantList")) {
    		CMLReactantList rlist = (CMLReactantList) element;
                Vector r = new Vector();
                for(CMLElement relement : rlist.getChildCMLElements()) {
                    CMLReactant reactant = (CMLReactant) element;
                    String id = reactant.getRef().substring(1);
                    r.add( nameFromID(Integer.parseInt(id)) );
                 }
                numReactants = r.size();
                reactantMolecules = new String[numReactants];
                r.copyInto(reactantMolecules);
           } else if(element.getId().matches("ProductList")) {
                Vector p = new Vector();
                CMLProductList plist = (CMLProductList) element;
                for(CMLElement pelement : plist.getChildCMLElements()) {
                    CMLProduct product = (CMLProduct) pelement;
                    String id = product.getRef().substring(1);
                    p.add( nameFromID(Integer.parseInt(id)) );
                }
                numProducts = p.size();
                productMolecules = new String[numProducts];
                p.copyInto(productMolecules);
            }
       }
        maxMolecules = numProducts + numReactants;
    }  
    
    private int idFromName(String name) {
        ReactMolecule m = (ReactMolecule)molecules.get(name);
        return m.getID();
    }
    
    private String nameFromID(int id)
    {
        Enumeration _e = molecules.elements();
        while(_e.hasMoreElements())
        {
            ReactMolecule m = (ReactMolecule)_e.nextElement();
            if (m.getID() == id)
                return m.getMoleculeName();
        }
        
        return "<unknown>";
    }
    
    
};
