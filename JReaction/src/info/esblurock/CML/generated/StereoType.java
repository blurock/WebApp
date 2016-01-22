//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.08 at 05:37:51 PM CEST 
//


package info.esblurock.CML.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for stereoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="stereoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="T"/>
 *     &lt;enumeration value="W"/>
 *     &lt;enumeration value="H"/>
 *     &lt;enumeration value="undefined"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "stereoType", namespace = "http://www.xml-cml.org/schema")
@XmlEnum
public enum StereoType {


    /**
     * 
     *                         
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="summary"&gt;A cis bond.&lt;/h:div&gt;
     * </pre>
     * 
     *                     
     * 
     */
    C("C"),

    /**
     * 
     *                         
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="summary"&gt;A trans bond.&lt;/h:div&gt;
     * </pre>
     * 
     *                     
     * 
     */
    T("T"),

    /**
     * 
     *                         
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="summary"&gt;A wedge bond.&lt;/h:div&gt;
     * </pre>
     * 
     *                     
     * 
     */
    W("W"),

    /**
     * 
     *                         
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="summary"&gt;A hatch bond.&lt;/h:div&gt;
     * </pre>
     * 
     *                     
     * 
     */
    H("H"),

    /**
     * 
     *                         
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="summary"&gt;Undefined stereo chemistry.&lt;/h:div&gt;
     * </pre>
     * 
     *                     
     * 
     */
    @XmlEnumValue("undefined")
    UNDEFINED("undefined"),

    /**
     * 
     *                         
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="summary"&gt;Another type of bond stereo - use dictRef to add further information.
     *                         &lt;/h:div&gt;
     * </pre>
     * 
     *                     
     * 
     */
    @XmlEnumValue("other")
    OTHER("other");
    private final String value;

    StereoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StereoType fromValue(String v) {
        for (StereoType c: StereoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
