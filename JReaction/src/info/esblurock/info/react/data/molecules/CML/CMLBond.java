/*
 * CMLBond.java
 *
 * Created on February 1, 2004, 8:21 PM
 */

package info.esblurock.info.react.data.molecules.CML;
import info.esblurock.react.common.IRestorableElement;
import info.esblurock.CML.generated.Bond;
import info.esblurock.CML.generated.ObjectFactory;
import info.esblurock.info.react.data.molecules.ReactBond;
import info.esblurock.react.common.SProperties;

import java.io.*;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;

/**
 *
 * @author  moliate
 */
public class CMLBond extends ReactBond implements IRestorableElement
{
    
    public void parse(byte[] data) throws java.text.ParseException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try
        {           
                JAXBContext jc = JAXBContext.newInstance(SProperties.getProperty("reaction.cml.root"));
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                //unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                Bond bond = (Bond)unmarshaller.unmarshal(bis);
                fromCML(bond);
        } 
        catch (Exception e) 
            { return; }  
    }
    
    public byte[] restore() {
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try
        {               
                Bond bond = toCML();   
                JAXBContext jc = JAXBContext.newInstance(bond.getClass().getPackage().getName());
                Marshaller marshaller = jc.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                marshaller.marshal(bond, new PrintStream(bos) );
        } 
        catch (Exception e) 
            { return e.toString().getBytes(); }  
        
        return bos.toString().getBytes();
    }
    
    public Bond toCML()
    {
        try
        {               
                ObjectFactory factory = new ObjectFactory();
                Bond bond = factory.createBond();
                bond.setOrder("" + bondOrder());
                bond.setRef("a"+I + " a" + J);
                return bond;
        } 
        catch (Exception e) 
            { return null; }      
    }
    
    public void fromCML(Bond bond)
    {
        try
        {
            java.util.List<String> refs = bond.getAtomRefs2();
                String ref = bond.getRef();
                StringTokenizer st = new StringTokenizer(ref);
                String a1 = st.nextToken();
                String a2 = st.nextToken();
                I = new Integer(a1.substring(1));
                J = new Integer(a2.substring(1));
                int order = Integer.parseInt(bond.getOrder());
                setBondOrder(order); 
        } 
        catch (Exception e) 
            { return; }      
    }
    
}
