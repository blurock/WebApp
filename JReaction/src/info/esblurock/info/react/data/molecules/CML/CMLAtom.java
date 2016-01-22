
/*
 * CMLAtom.java
 *
 *
 * Created on February 1, 2004, 8:20 PM
 */

package info.esblurock.info.react.data.molecules.CML;

import info.esblurock.react.common.IRestorableElement;
import info.esblurock.CML.generated.Atom;
import info.esblurock.CML.generated.ObjectFactory;
import info.esblurock.info.react.data.molecules.ReactAtom;
import info.esblurock.info.react.data.molecules.ReactPeriodicTable;
import info.esblurock.react.common.SProperties;

import java.io.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
/**
 *
 * @author  moliate
 */
public class CMLAtom extends ReactAtom implements IRestorableElement
{   
    
    public void parse(byte[] data) throws java.text.ParseException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try
        {           
                JAXBContext jc = JAXBContext.newInstance(SProperties.getProperty("reaction.cml.root"));
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                //unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                Atom atom = (Atom) unmarshaller.unmarshal(bis);
                fromCML(atom);

        } 
        catch (Exception e) 
            { return; }  
    }
    
    public byte[] restore() {
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try
        {               
                Atom atom = toCML();   
                JAXBContext jc = JAXBContext.newInstance(atom.getClass().getPackage().getName());
                Marshaller marshaller = jc.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                marshaller.marshal(atom, new PrintStream(bos) );
        } 
        catch (Exception e) 
            { return e.toString().getBytes(); }  
        
        return bos.toString().getBytes();
    }
    
    public Atom toCML()
    {
        try
        {               
                ObjectFactory factory = new ObjectFactory();
                Atom atom = factory.createAtom();
                atom.setId("a"+ID);
                atom.setElementType(ReactPeriodicTable.AtomName(AtomicNumber));
                if (ReactPeriodicTable.AtomName(AtomicNumber).equals("R"))
                	switch(AtomicNumber)
                	{
	                	case 221 :	
	                	case 300 :	atom.setRole("Q"); break;
	                	case 320 :	atom.setRole("R"); break;
	                	case 400:	atom.setRole("X"); break; 
	               	}
                atom.setX2((double) X);
                atom.setY2((double) Y);    
                return atom;
        } 
        catch (Exception e) 
            { return null; }      
    }
  
    public void fromCML(Atom atom)
    {
        try
        {               
	            if ( atom.getElementType().equals("R") )
                	{
	                	if (atom.getRole().equals("Q"))
	                		AtomicNumber = 300;
	                	if (atom.getRole().equals("R"))
	                		AtomicNumber = 320;
	                	if (atom.getRole().equals("X"))
	                		AtomicNumber = 400;	                			                		
	               	}
	            else
                	AtomicNumber = ReactPeriodicTable.AtomNumber(atom.getElementType());
                X = (float) atom.getX2().doubleValue();
                Y = (float) atom.getY2().doubleValue();
                ID = atom.getId();     
                if (ID.startsWith("a"))
                    ID = ID.substring(1);
        } 
        catch (Exception e) 
            { return; }      
    }
    
}
