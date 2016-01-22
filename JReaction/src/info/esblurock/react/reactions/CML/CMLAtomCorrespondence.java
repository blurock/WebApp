/*
 * CMLAtomCorrespondance.java
 *
 * Created on February 4, 2004, 1:33 PM
 */

package info.esblurock.react.reactions.CML;
import info.esblurock.react.common.IRestorableElement;
import info.esblurock.react.common.Parser;
import info.esblurock.CML.generated.Map;
import info.esblurock.CML.generated.ObjectFactory;
import info.esblurock.react.common.SProperties;
import info.esblurock.react.reactions.ReactAtomCorrespondence;

import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;

import java.io.*;
/**
 *
 * @author  moliate
 */
public class CMLAtomCorrespondence extends ReactAtomCorrespondence implements IRestorableElement, ICMLReactionConstants
{
	
    /** Creates a new instance of CMLAtomCorrespondance */
    public CMLAtomCorrespondence() 
    {}
    
    public byte[] restore() 
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try
        {               
                Map link = toCML();    
                JAXBContext jc = JAXBContext.newInstance(link.getClass().getPackage().getName());
                Marshaller marshaller = jc.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                marshaller.marshal(link, new PrintStream(bos) );
        } 
        catch (Exception e) 
            { return e.toString().getBytes(); }  
        
        return bos.toString().getBytes();
    }
    
    public void parse(byte[] data) throws java.text.ParseException 
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try
        {           
                JAXBContext jc = JAXBContext.newInstance(SProperties.getProperty("reaction.cml.root"));
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                //unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                Map link = (Map) unmarshaller.unmarshal(bis);
                fromCML(link);
        } 
        catch (Exception e) 
            {
                return; 
            }  
    }
    
    public Map toCML() 
    {
        try
        {               
                ObjectFactory factory = new ObjectFactory();
                Map link = factory.createMap(); 
                link.setDictRef(CONST_DICTREF_2ATOMLINK);
                link.setFromContext("m"+Molecule1_id + "_r" + Molecule1 + ":a"+ Atom1);
                link.setToContext("m"+ Molecule2_id + "_p" + Molecule2 + ":a" + Atom2);                
                return link;
        } 
        catch (Exception e) 
            { return null; } 
    }
      
    public void fromCML(Map link) 
    {
        if (link.getDictRef().equals(CONST_DICTREF_2ATOMLINK))
        {
            String from     = link.getFromContext();
            StringTokenizer st = new StringTokenizer(from , ":");
            Parser parser = new Parser(st.nextToken());
            Molecule1_id       = parser.nextInt();
            Molecule1          = parser.nextInt(); 
            parser = new Parser(st.nextToken());
            Atom1           = parser.nextInt();       
            String to       = link.getToContext();
            st = new StringTokenizer(to, ":");
            parser = new Parser(st.nextToken());
            Molecule2_id       = parser.nextInt();
            Molecule2          = parser.nextInt(); 
            parser = new Parser(st.nextToken());
            Atom2           = parser.nextInt();  
        }
    }
    
}
