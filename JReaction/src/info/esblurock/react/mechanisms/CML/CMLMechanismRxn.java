/*
 * CMLMechanismRxn.java
 *
 * Created on February 22, 2004, 2:47 PM
 */

package info.esblurock.react.mechanisms.CML;
import info.esblurock.react.common.IRestorableElement;
import info.esblurock.react.common.ReactionLog;
import info.esblurock.CML.generated.ObjectFactory;
import info.esblurock.CML.generated.ObjectFactory;
import info.esblurock.CML.generated.Product;
import info.esblurock.CML.generated.ProductList;
import info.esblurock.CML.generated.Property;
import info.esblurock.CML.generated.PropertyList;
import info.esblurock.CML.generated.Reactant;
import info.esblurock.CML.generated.ReactantList;
import info.esblurock.CML.generated.Reaction;
import info.esblurock.CML.generated.ReactionList;
import info.esblurock.CML.generated.Scalar;
import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.react.common.SProperties;
import info.esblurock.react.mechanisms.ReactMechanismRxn;
import info.esblurock.react.reactions.CML.ICMLReactionConstants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;

import java.util.*;
import java.io.*;
/**
 *
 * @author  moliate
 */
public class CMLMechanismRxn extends ReactMechanismRxn implements IRestorableElement, ICMLReactionConstants 
{
    Hashtable molecules;
    /** Creates a new instance of CMLMechanismRxn */
    public CMLMechanismRxn(Hashtable molecules) 
    {
        this.molecules = molecules;
    }
    
    public void parse(byte[] data) throws java.text.ParseException 
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try
        {           
                JAXBContext jc = JAXBContext.newInstance(SProperties.getProperty("reaction.cml.root"));
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                //unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                Reaction reaction = (Reaction) unmarshaller.unmarshal(bis);
                fromCML(reaction);

        } 
        catch (Exception e) 
            { ReactionLog.logError(e.toString()); return; }  
    }
    
    public byte[] restore() 
    {       
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try
        {               
                Reaction reaction = toCML();   
                JAXBContext jc = JAXBContext.newInstance(reaction.getClass().getPackage().getName());
                Marshaller marshaller = jc.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                marshaller.marshal(reaction, new PrintStream(bos) );
        } 
        catch (Exception e) 
            { return e.toString().getBytes(); }  
        
        return bos.toString().getBytes();
    }
    
    public Reaction toCML() 
    {
        try {               
                ObjectFactory factory = new ObjectFactory();
                Reaction reaction = factory.createReaction(); 
                
                // set constants
                PropertyList p = factory.createPropertyList();
                // Multiplicity for reactant
                Property pe = factory.createProperty();
                pe.setDictRef(CONST_DICTREF_COMBUSTIONCONSTANTS);
                pe.setRole(CONST_ROLE_REACTANT);
                Scalar scalar = factory.createScalar();
                scalar.setDataType("xsd:integer");
                scalar.setTitle(CONST_MULTIPLICITY);
                scalar.setValue(""+reactantMultiplicity);
                pe.getAnyCmlOrAnyOrAny().add(scalar);
                p.getAnyCmlOrAnyOrAny().add(pe);
                // Multiplicity for products
                pe = factory.createProperty();
                pe.setDictRef(CONST_DICTREF_COMBUSTIONCONSTANTS);
                pe.setRole(CONST_ROLE_PRODUCT);
                scalar = factory.createScalar();
                scalar.setDataType("xsd:integer");
                scalar.setTitle(CONST_MULTIPLICITY);
                scalar.setValue(""+productMultiplicity);
                pe.getAnyCmlOrAnyOrAny().add(scalar);
                p.getAnyCmlOrAnyOrAny().add(pe);
                reaction.getAnyCmlOrAnyOrAny().add(p);
                
                // add reactants
                ReactantList reactants = factory.createReactantList();                  
                for (int i = 0; i < reactantMolecules.length; i++)
                {
                    Reactant _r = factory.createReactant();
                    _r.setCount( new Double(1.0) );
                    _r.setRef( "m" + idFromName(reactantMolecules[i]) );
                    /*
                    //MoleculeElement m = factory.createMoleculeElement();
                    //m.setId("m"+i);
                    NameElement name = factory.createNameElement();
                    name.setConvention("IUPAC");
                    name.setValue(reactantMolecules[i]);
                    //m.getName().add(name);
                    //_r.setMolecule(m);
                    _r.getName().add(name);
                     */
                    reactants.getAnyCmlOrAnyOrAny().add(_r);
                }
                reaction.getAnyCmlOrAnyOrAny().add(reactants);
  
                // add products
                ProductList products = factory.createProductList();  
                for (int i = 0; i < productMolecules.length; i++)
                {
                    Product _p = factory.createProduct(); 
                    _p.setCount( new Double(1.0) );
                    _p.setRef( "m" + idFromName(productMolecules[i]) );
                    /*
                    //MoleculeElement m = factory.createMoleculeElement();
                    //m.setId("m"+i);
                    NameElement name = factory.createNameElement();
                    name.setConvention("IUPAC");
                    name.setValue(productMolecules[i]);
                    //m.getName().add(name);
                    //_p.setMolecule(m);
                    _p.getName().add(name);
                     */
                    products.getAnyCmlOrAnyOrAny().add(_p); 
                }
                reaction.getAnyCmlOrAnyOrAny().add(products);
                
                return reaction;
        } 
        catch (Exception e) 
            { return null; } 
    }
      
    public void fromCML(Reaction reaction) {
        // get constants
        java.util.List propertyList = reaction.getAnyCmlOrAnyOrAny();
        for (int i = 0; i < propertyList.size(); i++) {
            Object o = propertyList.get(i);
            if(o instanceof PropertyList) {
                PropertyList props = (PropertyList) o;
                java.util.List properties = props.getAnyCmlOrAnyOrAny();
                for(int ii=0;ii < properties.size();i++) {
                    Property p = (Property)properties.get(ii);
                    if ( CONST_DICTREF_COMBUSTIONCONSTANTS == p.getDictRef() ) {
                        List scalar = p.getAnyCmlOrAnyOrAny();
                        for (int iii=0; iii < scalar.size(); iii++) {
                            if ( (scalar.get(iii) instanceof Scalar) ) {
                                Scalar s = (Scalar)scalar.get(iii);
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
                    
                }
                
            } else if(o instanceof ReactantList) {
                Vector r = new Vector();
                ReactantList rlist = (ReactantList) o;
                java.util.List reactants = rlist.getAnyCmlOrAnyOrAny();
                for (int jj = 0; jj < reactants.size(); jj++) {
                    Reactant reactant = (Reactant) reactants.get(jj);
                    String id = reactant.getRef().substring(1);
                    r.add( nameFromID(Integer.parseInt(id)) );
                 }
                numReactants = r.size();
                reactantMolecules = new String[numReactants];
                r.copyInto(reactantMolecules);
           } else if(o instanceof ProductList) {
                Vector p = new Vector();
                ProductList plist = (ProductList) o;
                java.util.List products = plist.getAnyCmlOrAnyOrAny();
                for (int jj = 0; jj < products.size(); jj++) {
                    Product product = (Product) products.get(jj);
                    String id = product.getRef().substring(1);
                    p.add( nameFromID(Integer.parseInt(id)) );
                }
                numProducts = p.size();
                productMolecules = new String[numProducts];
                p.copyInto(productMolecules);
            }
       }

        
        // product molecules
                
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
