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
 * <p>Java class for stateType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="stateType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="aqueous"/>
 *     &lt;enumeration value="gas"/>
 *     &lt;enumeration value="glass"/>
 *     &lt;enumeration value="liquid"/>
 *     &lt;enumeration value="nematic"/>
 *     &lt;enumeration value="smectic"/>
 *     &lt;enumeration value="solid"/>
 *     &lt;enumeration value="solidSolution"/>
 *     &lt;enumeration value="solution"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "stateType", namespace = "http://www.xml-cml.org/schema")
@XmlEnum
public enum StateType {


    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;An aqueous solution.&lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("aqueous")
    AQUEOUS("aqueous"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;Gas or vapor. The default state for computation on isolated molecule.&lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("gas")
    GAS("gas"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;A glassy stat.&lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("glass")
    GLASS("glass"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;Normally pure liquid (use solution where appropriate.&lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("liquid")
    LIQUID("liquid"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;The nematic phase.&lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("nematic")
    NEMATIC("nematic"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;The smectic phase.&lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("smectic")
    SMECTIC("smectic"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;A solid.&lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("solid")
    SOLID("solid"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;A solid solution.&lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("solidSolution")
    SOLID_SOLUTION("solidSolution"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;A (liquid) solution.&lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("solution")
    SOLUTION("solution"),

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

    StateType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StateType fromValue(String v) {
        for (StateType c: StateType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
