//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.10 at 09:11:44 AM CET 
//


package blurock.reaction.generated.core;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for measurementType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="measurementType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="transmittance"/>
 *     &lt;enumeration value="absorbance"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "measurementType", namespace = "http://www.xml-cml.org/schema")
@XmlEnum
public enum MeasurementType {


    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="summary"&gt;Data are transmittance, so "peaks" are usually troughs.&lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("transmittance")
    TRANSMITTANCE("transmittance"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="summary"&gt;Data are absorbanc.&lt;/h:div&gt;
     * </pre>
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;so "peaks" are normally peaks.&lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("absorbance")
    ABSORBANCE("absorbance"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="summary"&gt;A value not in the controlled vocabulary - use dictRef to add further information.
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("other")
    OTHER("other");
    private final String value;

    MeasurementType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MeasurementType fromValue(String v) {
        for (MeasurementType c: MeasurementType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
