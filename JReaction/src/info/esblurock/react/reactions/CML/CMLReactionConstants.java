/*
 * CMLReactionConstants.java
 *
 * Created on February 4, 2004, 1:56 PM
 */

package info.esblurock.react.reactions.CML;
import info.esblurock.react.common.IRestorableElement;
import info.esblurock.react.reactions.*;

import java.io.ByteArrayInputStream;
import java.text.ParseException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.xmlcml.cml.base.CMLElement;
import org.xmlcml.cml.element.CMLMetadata;
import org.xmlcml.cml.element.CMLMetadataList;
import org.xmlcml.cml.element.CMLProperty;
import org.xmlcml.cml.element.CMLScalar;

import javax.xml.bind.Marshaller;
/**
 *
 * @author  moliate
 */
public class CMLReactionConstants extends ReactReactionConstants implements IRestorableElement, ICMLReactionConstants
{
  
    /** Creates a new instance of CMLAtomCorrespondance */
    public CMLReactionConstants() 
    {}
    
    public byte[] restore() {
        CMLElement constants = toCML();  
        return constants.toXML().getBytes();
    }
    
    public void parse(byte[] data) throws java.text.ParseException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try {           
                JAXBContext jc = JAXBContext.newInstance(CMLElement.class);
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                CMLElement constants = (CMLElement) unmarshaller.unmarshal(bis);
                fromCML(constants);
        } catch (Exception e) {
            throw new ParseException(e.toString(),0);
           }  
    }
    
    public CMLElement toCML() {
    	CMLElement element = new CMLElement(this.reference);
         CMLProperty property = new CMLProperty();
        property.setRole(direction?CONST_ROLE_FORWARD:CONST_ROLE_REVERSE); 
        property.setDictRef(CONST_DICTREF_COMBUSTIONCONSTANTS);

        CMLScalar n = new CMLScalar();
         n.setTitle(CONST_N);
                n.setValue(""+nFactor);
                n.setDataType("xsd:decimal");
                n.setUnits("units:1/K");
                property.appendChild(n);
                
                
                CMLScalar a = new CMLScalar();
                a.setTitle(CONST_A);
                a.setValue(""+aFactor);
                a.setDataType("xsd:decimal");
                a.setUnits("units:1/s");
                property.appendChild(a);
                
                CMLScalar e = new CMLScalar();
                e.setTitle(CONST_EA);
                e.setValue(""+eFactor);
                e.setDataType("xsd:decimal");
                e.setUnits("units:kJ");
                property.appendChild(e);
                
                CMLScalar m = new CMLScalar();
                m.setTitle(CONST_MULTIPLICITY);
                m.setValue(""+multiplicity);
                m.setDataType("xsd:integer");
                property.appendChild(m);
                
                element.appendChild(property);
                if (null != reference) {
                	CMLMetadataList mdl = new CMLMetadataList();
                	CMLMetadata md = new CMLMetadata();
                	md.setName("dc:source");
                	md.setAttribute("dc:source", reference);
                    mdl.appendChild(md);
                	property.appendChild(mdl);
            	}
                return element;
    }
    
    public void fromCML(CMLElement element) {
    	CMLProperty property = (CMLProperty) element.getProperty(CONST_DICTREF_COMBUSTIONCONSTANTS);
                if ( (property.getRole().equals(CONST_ROLE_FORWARD)) )
                    direction = true;
                else if ( (property.getRole().equals(CONST_ROLE_REVERSE)) )
                    direction = false;    
                else return;
                
                for(CMLScalar scalar : property.getScalarElements()) {
                    if (scalar.getTitle().equals(CONST_A))
                        aFactor = Double.parseDouble(scalar.getValue());
                    if (scalar.getTitle().equals(CONST_N))
                        nFactor = Double.parseDouble(scalar.getValue());
                    if (scalar.getTitle().equals(CONST_EA))
                        eFactor = Double.parseDouble(scalar.getValue());     
                    if (scalar.getTitle().equals(CONST_MULTIPLICITY))
                        multiplicity = Integer.parseInt(scalar.getValue());  
                }
                              
    }
    
}
