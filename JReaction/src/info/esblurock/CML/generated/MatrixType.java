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
 * <p>Java class for matrixType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="matrixType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="rectangular"/>
 *     &lt;enumeration value="square"/>
 *     &lt;enumeration value="squareSymmetric"/>
 *     &lt;enumeration value="squareSymmetricLT"/>
 *     &lt;enumeration value="squareSymmetricUT"/>
 *     &lt;enumeration value="squareAntisymmetric"/>
 *     &lt;enumeration value="squareAntisymmetricLT"/>
 *     &lt;enumeration value="squareAntisymmetricUT"/>
 *     &lt;enumeration value="diagonal"/>
 *     &lt;enumeration value="upperTriangular"/>
 *     &lt;enumeration value="upperTriangularUT"/>
 *     &lt;enumeration value="lowerTriangular"/>
 *     &lt;enumeration value="lowerTriangularLT"/>
 *     &lt;enumeration value="unit"/>
 *     &lt;enumeration value="unitary"/>
 *     &lt;enumeration value="rowEigenvectors"/>
 *     &lt;enumeration value="rotation22"/>
 *     &lt;enumeration value="rotationTranslation32"/>
 *     &lt;enumeration value="homogeneous33"/>
 *     &lt;enumeration value="rotation33"/>
 *     &lt;enumeration value="rotationTranslation43"/>
 *     &lt;enumeration value="homogeneous44"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "matrixType", namespace = "http://www.xml-cml.org/schema")
@XmlEnum
public enum MatrixType {


    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Rectangular with no semantic constraints and ordered rowwise (i.e. the column index
     *                                     runs fastest).
     *                                     &lt;h:pre&gt;
     *                                         1 2 3 4
     *                                         0 3 5 6
     *                                     &lt;/h:pre&gt;
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("rectangular")
    RECTANGULAR("rectangular"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Square with no semantic constraints.
     *                                     &lt;h:pre&gt;
     *                                         1 2 78
     *                                         3 4 -1
     *                                         -34 2 7
     *                                     &lt;/h:pre&gt;
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("square")
    SQUARE("square"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Square symmetric with all elements explicit.
     *                                     &lt;h:pre&gt;
     *                                         1 2 3
     *                                         2 7 1
     *                                         3 1 9
     *                                     &lt;/h:pre&gt;
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("squareSymmetric")
    SQUARE_SYMMETRIC("squareSymmetric"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Square symmetric with the diagonal and lower triangle explicit and the upper
     *                                     triangle omitted. Rows are of length 1, 2, 3...
     *                                     &lt;h:pre&gt;
     *                                         1
     *                                         2 7
     *                                         3 1 9
     *                                     &lt;/h:pre&gt;
     *                                     is equivalent to
     *                                     &lt;h:pre&gt;
     *                                         1 2 3
     *                                         2 7 1
     *                                         3 1 9
     *                                     &lt;/h:pre&gt;
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("squareSymmetricLT")
    SQUARE_SYMMETRIC_LT("squareSymmetricLT"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Square symmetric with the diagonal and upper triangle explicit. Rows are of length
     *                                     n, n-1, ... 2, 1
     *                                     &lt;h:pre&gt;
     *                                         1 7 9
     *                                         2 -1
     *                                         34
     *                                     &lt;/h:pre&gt;
     *                                     is equivalent to
     *                                     &lt;h:pre&gt;
     *                                         1 7 9
     *                                         7 2 -1
     *                                         9 -1 34
     *                                     &lt;/h:pre&gt;
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("squareSymmetricUT")
    SQUARE_SYMMETRIC_UT("squareSymmetricUT"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Square antisymmetric with all elements explicit. The diagonal is necessarily zero.
     *                                     &lt;h:pre&gt;
     *                                         0 -2 3
     *                                         2 0 1
     *                                         -3 -1 0
     *                                     &lt;/h:pre&gt;
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("squareAntisymmetric")
    SQUARE_ANTISYMMETRIC("squareAntisymmetric"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Square symmetric with the lower triangle explicit and diagonal and upper triangle
     *                                     omitted. Rows are of length 1, 2,... n-1.
     *                                     &lt;h:pre&gt;
     *                                         -7
     *                                         -9 1
     *                                     &lt;/h:pre&gt;
     *                                     is equivalent to
     *                                     &lt;h:pre&gt;
     *                                         0 7 9
     *                                         -7 0 -1
     *                                         -9 1 0
     *                                     &lt;/h:pre&gt;
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("squareAntisymmetricLT")
    SQUARE_ANTISYMMETRIC_LT("squareAntisymmetricLT"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Square symmetric with the upper triangle explicit and diagonal and lower triangle
     *                                     omitted. Rows are of length n-1, n-2,... 2,1.
     *                                     &lt;h:pre&gt;
     *                                         7 9
     *                                         -1
     *                                     &lt;/h:pre&gt;
     *                                     is equivalent to
     *                                     &lt;h:pre&gt;
     *                                         0 7 9
     *                                         -7 0 -1
     *                                         -9 1 0
     *                                     &lt;/h:pre&gt;
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("squareAntisymmetricUT")
    SQUARE_ANTISYMMETRIC_UT("squareAntisymmetricUT"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Symmetric. Elements are zero except on the diagonal. No compressed representation
     *                                     available (use
     *                                     &lt;h:tt&gt;array&lt;/h:tt&gt;
     *                                     element).
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:pre xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&gt;
     *                                     1 0 0
     *                                     0 3 0
     *                                     0 0 4
     *                                 &lt;/h:pre&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("diagonal")
    DIAGONAL("diagonal"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Square. Elements are zero below the diagonal
     *                                     &lt;h:pre&gt;
     *                                         1 2 3 4
     *                                         0 3 5 6
     *                                         0 0 4 8
     *                                         0 0 0 2
     *                                     &lt;/h:pre&gt;
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("upperTriangular")
    UPPER_TRIANGULAR("upperTriangular"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Square. Elements below the diagonal are zero and omitted, and rows are of length n,
     *                                     n-1, ... , 2, 1.
     *                                     &lt;h:pre&gt;
     *                                         1 2 3 4
     *                                         3 5 6
     *                                         4 8
     *                                         2
     *                                     &lt;/h:pre&gt;
     *                                     is equivalent to
     *                                     &lt;h:pre&gt;
     *                                         1 2 3 4
     *                                         0 3 5 6
     *                                         0 0 4 8
     *                                         0 0 0 2
     *                                     &lt;/h:pre&gt;
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("upperTriangularUT")
    UPPER_TRIANGULAR_UT("upperTriangularUT"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Square. Elements are zero above the diagonal
     *                                     &lt;h:pre&gt;
     *                                         1 0 0
     *                                         7 3 0
     *                                         9 2 4
     *                                     &lt;/h:pre&gt;
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("lowerTriangular")
    LOWER_TRIANGULAR("lowerTriangular"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Square. Elements above the diagonal are zero and omitted, and rows are of length 1,
     *                                     2, ...n.
     *                                     &lt;h:pre&gt;
     *                                         1
     *                                         3 7
     *                                         9 2 3
     *                                     &lt;/h:pre&gt;
     *                                     is equivalent to
     *                                     &lt;h:pre&gt;
     *                                         1 0 0
     *                                         3 7 0
     *                                         9 2 3
     *                                     &lt;/h:pre&gt;
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("lowerTriangularLT")
    LOWER_TRIANGULAR_LT("lowerTriangularLT"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Square. Diagonal elements are 1 and off-diagonal are zero.
     *                                     &lt;h:pre&gt;
     *                                         1 0 0
     *                                         0 1 0
     *                                         0 0 1
     *                                     &lt;/h:pre&gt;
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("unit")
    UNIT("unit"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Square. When multiplied by its transpose gives the unit matrix.
     *                                     &lt;h:pre&gt;
     *                                         0 -1 0
     *                                         1 0 0
     *                                         0 0 1
     *                                     &lt;/h:pre&gt;
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("unitary")
    UNITARY("unitary"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     Square. Each row corresponds to an eigenvector of a square matrix. Elements are
     *                                     real. The length of the eigenvectors is undefined, i.e. they are not required to be
     *                                     normalised to 1.
     *                                     &lt;h:pre&gt;
     *                                         0 -1 0
     *                                         1 0 0
     *                                         0 0 1
     *                                     &lt;/h:pre&gt;
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("rowEigenvectors")
    ROW_EIGENVECTORS("rowEigenvectors"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     The rotation is defined by the matrix premultiplyin a column vector (x, y) .
     *                                     &lt;h:pre&gt;
     *                                         0 -1
     *                                         1 0
     *                                     &lt;/h:pre&gt;
     *                                     produces (-y, x), i.e. a rotation of -90 degrees.
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("rotation22")
    ROTATION_22("rotation22"),

    /**
     * 
     *                                 
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;
     *                                     A third column defining the translation is added to a rotation22.
     *                                     &lt;h:pre&gt;
     *                                         0 -1 22
     *                                         1 0 33
     *                                     &lt;/h:pre&gt;
     *                                     produces (-y + 22, x + 33), i.e. a rotation of -90 degrees followed by a translation
     *                                     of (22, 33).
     *                                 &lt;/h:div&gt;
     * </pre>
     * 
     *                             
     * 
     */
    @XmlEnumValue("rotationTranslation32")
    ROTATION_TRANSLATION_32("rotationTranslation32"),
    @XmlEnumValue("homogeneous33")
    HOMOGENEOUS_33("homogeneous33"),
    @XmlEnumValue("rotation33")
    ROTATION_33("rotation33"),
    @XmlEnumValue("rotationTranslation43")
    ROTATION_TRANSLATION_43("rotationTranslation43"),
    @XmlEnumValue("homogeneous44")
    HOMOGENEOUS_44("homogeneous44"),

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

    MatrixType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MatrixType fromValue(String v) {
        for (MatrixType c: MatrixType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
