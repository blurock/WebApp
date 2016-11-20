/*
 * CMLAtomCorrespondance.java
 *
 * Created on February 4, 2004, 1:33 PM
 */

package info.esblurock.react.reactions.CML;

import info.esblurock.react.common.IRestorableElement;
import info.esblurock.react.common.Parser;
import info.esblurock.react.common.SProperties;
import info.esblurock.react.reactions.ReactAtomCorrespondence;

import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.xmlcml.cml.base.CMLElement;
import org.xmlcml.cml.element.CMLMap;

import javax.xml.bind.Marshaller;

import java.io.*;
import java.text.ParseException;

/**
 *
 * @author moliate
 */
public class CMLAtomCorrespondence extends ReactAtomCorrespondence
		implements IRestorableElement, ICMLReactionConstants {

	/** Creates a new instance of CMLAtomCorrespondance */
	public CMLAtomCorrespondence() {
	}

	public byte[] restore() {
		CMLElement link = toCML();
		return link.toXML().getBytes();
	}

	public void parse(byte[] data) throws java.text.ParseException {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		try {
			JAXBContext jc = JAXBContext.newInstance(CMLElement.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			CMLElement link = (CMLElement) unmarshaller.unmarshal(bis);
			fromCML(link);
		} catch (Exception e) {
			throw new ParseException(e.toString(), 0);
		}
	}

	public CMLElement toCML() {
    	CMLElement element = new CMLElement("AtomCorrespondence");
    	CMLMap link = new CMLMap();
    	element.appendChild(link);
        link.setDictRef(CONST_DICTREF_2ATOMLINK);
        link.setFromContext("m"+Molecule1_id + "_r" + Molecule1 + ":a"+ Atom1);
        link.setToContext("m"+ Molecule2_id + "_p" + Molecule2 + ":a" + Atom2);               
		return element;
	}
                
    

	public void fromCML(CMLElement element) {
		CMLMap link = (CMLMap) element.getChild(0);
        if (link.getDictRef().equals(CONST_DICTREF_2ATOMLINK)) {
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
