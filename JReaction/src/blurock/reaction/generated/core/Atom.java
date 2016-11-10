//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.11.10 at 09:11:44 AM CET 
//


package blurock.reaction.generated.core;

import java.math.BigInteger;
import java.util.HashMap;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.xml-cml.org/schema}anyCml"/>
 *         &lt;any processContents='lax' namespace='##other'/>
 *         &lt;any processContents='lax' namespace=''/>
 *       &lt;/choice>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}yFract"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}isotopeNumber"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}pointGroupMultiplicity"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}convention"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}count"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}hydrogenCount"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}isotopeRef"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}spaceGroupMultiplicity"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}y2"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}occupancy"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}x3"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}z3"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}dictRef"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}elementType"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}x2"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}isotopeListRef"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}xFract"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}id"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}formalCharge"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}title"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}spinMultiplicity"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}role"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}ref"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}zFract"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}y3"/>
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "anyCmlOrAnyOrAny"
})
public class Atom {

    @XmlElementRef(name = "anyCml", namespace = "http://www.xml-cml.org/schema", type = JAXBElement.class, required = false)
    @XmlAnyElement(lax = true)
    protected java.util.List<java.lang.Object> anyCmlOrAnyOrAny;
    @XmlAttribute(name = "yFract")
    protected Double yFract;
    @XmlAttribute(name = "isotopeNumber")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger isotopeNumber;
    @XmlAttribute(name = "pointGroupMultiplicity")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger pointGroupMultiplicity;
    @XmlAttribute(name = "convention")
    protected String convention;
    @XmlAttribute(name = "count")
    protected Double count;
    @XmlAttribute(name = "hydrogenCount")
    protected BigInteger hydrogenCount;
    @XmlAttribute(name = "isotopeRef")
    protected String isotopeRef;
    @XmlAttribute(name = "spaceGroupMultiplicity")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger spaceGroupMultiplicity;
    @XmlAttribute(name = "y2")
    protected Double y2;
    @XmlAttribute(name = "occupancy")
    protected Double occupancy;
    @XmlAttribute(name = "x3")
    protected Double x3;
    @XmlAttribute(name = "z3")
    protected Double z3;
    @XmlAttribute(name = "dictRef")
    protected String dictRef;
    @XmlAttribute(name = "elementType")
    protected String elementType;
    @XmlAttribute(name = "x2")
    protected Double x2;
    @XmlAttribute(name = "isotopeListRef")
    protected String isotopeListRef;
    @XmlAttribute(name = "xFract")
    protected Double xFract;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "formalCharge")
    protected BigInteger formalCharge;
    @XmlAttribute(name = "title")
    protected String title;
    @XmlAttribute(name = "spinMultiplicity")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger spinMultiplicity;
    @XmlAttribute(name = "role")
    protected String role;
    @XmlAttribute(name = "ref")
    protected String ref;
    @XmlAttribute(name = "zFract")
    protected Double zFract;
    @XmlAttribute(name = "y3")
    protected Double y3;
    @XmlAnyAttribute
    private java.util.Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the anyCmlOrAnyOrAny property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the anyCmlOrAnyOrAny property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnyCmlOrAnyOrAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Spectator }{@code >}
     * {@link JAXBElement }{@code <}{@link PropertyList }{@code >}
     * {@link JAXBElement }{@code <}{@link Cml }{@code >}
     * {@link JAXBElement }{@code <}{@link blurock.reaction.generated.core.Map }{@code >}
     * {@link JAXBElement }{@code <}{@link BondSet }{@code >}
     * {@link JAXBElement }{@code <}{@link PeakStructure }{@code >}
     * {@link JAXBElement }{@code <}{@link PotentialForm }{@code >}
     * {@link JAXBElement }{@code <}{@link Gradient }{@code >}
     * {@link JAXBElement }{@code <}{@link Entry }{@code >}
     * {@link JAXBElement }{@code <}{@link MetadataList }{@code >}
     * {@link JAXBElement }{@code <}{@link Isotope }{@code >}
     * {@link JAXBElement }{@code <}{@link ReactiveCentre }{@code >}
     * {@link JAXBElement }{@code <}{@link System }{@code >}
     * {@link JAXBElement }{@code <}{@link SpectatorList }{@code >}
     * {@link JAXBElement }{@code <}{@link Unit }{@code >}
     * {@link JAXBElement }{@code <}{@link Property }{@code >}
     * {@link JAXBElement }{@code <}{@link Documentation }{@code >}
     * {@link JAXBElement }{@code <}{@link Transform3 }{@code >}
     * {@link JAXBElement }{@code <}{@link TableRow }{@code >}
     * {@link JAXBElement }{@code <}{@link BondStereo }{@code >}
     * {@link JAXBElement }{@code <}{@link TransitionState }{@code >}
     * {@link JAXBElement }{@code <}{@link BondArray }{@code >}
     * {@link JAXBElement }{@code <}{@link Mechanism }{@code >}
     * {@link JAXBElement }{@code <}{@link Torsion }{@code >}
     * {@link JAXBElement }{@code <}{@link Vector3 }{@code >}
     * {@link JAXBElement }{@code <}{@link SpectrumList }{@code >}
     * {@link JAXBElement }{@code <}{@link BasisSet }{@code >}
     * {@link JAXBElement }{@code <}{@link Matrix }{@code >}
     * {@link JAXBElement }{@code <}{@link UnitType }{@code >}
     * {@link JAXBElement }{@code <}{@link Electron }{@code >}
     * {@link JAXBElement }{@code <}{@link Reactant }{@code >}
     * {@link JAXBElement }{@code <}{@link Module }{@code >}
     * {@link JAXBElement }{@code <}{@link Name }{@code >}
     * {@link java.lang.Object }
     * {@link JAXBElement }{@code <}{@link BondType }{@code >}
     * {@link JAXBElement }{@code <}{@link Product }{@code >}
     * {@link JAXBElement }{@code <}{@link Label }{@code >}
     * {@link JAXBElement }{@code <}{@link Region }{@code >}
     * {@link JAXBElement }{@code <}{@link Amount }{@code >}
     * {@link JAXBElement }{@code <}{@link SubstanceList }{@code >}
     * {@link JAXBElement }{@code <}{@link Fragment }{@code >}
     * {@link JAXBElement }{@code <}{@link Atom }{@code >}
     * {@link JAXBElement }{@code <}{@link ReactionList }{@code >}
     * {@link JAXBElement }{@code <}{@link TableCell }{@code >}
     * {@link JAXBElement }{@code <}{@link ActionList }{@code >}
     * {@link JAXBElement }{@code <}{@link Substance }{@code >}
     * {@link JAXBElement }{@code <}{@link Formula }{@code >}
     * {@link JAXBElement }{@code <}{@link MoleculeList }{@code >}
     * {@link JAXBElement }{@code <}{@link Line3 }{@code >}
     * {@link JAXBElement }{@code <}{@link PeakGroup }{@code >}
     * {@link JAXBElement }{@code <}{@link LatticeVector }{@code >}
     * {@link JAXBElement }{@code <}{@link Scalar }{@code >}
     * {@link JAXBElement }{@code <}{@link Kpoint }{@code >}
     * {@link JAXBElement }{@code <}{@link Particle }{@code >}
     * {@link JAXBElement }{@code <}{@link ReactionStepList }{@code >}
     * {@link JAXBElement }{@code <}{@link Angle }{@code >}
     * {@link JAXBElement }{@code <}{@link Potential }{@code >}
     * {@link JAXBElement }{@code <}{@link Identifier }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomArray }{@code >}
     * {@link JAXBElement }{@code <}{@link UnitList }{@code >}
     * {@link JAXBElement }{@code <}{@link Definition }{@code >}
     * {@link JAXBElement }{@code <}{@link TableContent }{@code >}
     * {@link JAXBElement }{@code <}{@link Sample }{@code >}
     * {@link JAXBElement }{@code <}{@link Crystal }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomParity }{@code >}
     * {@link JAXBElement }{@code <}{@link ParameterList }{@code >}
     * {@link JAXBElement }{@code <}{@link Length }{@code >}
     * {@link JAXBElement }{@code <}{@link Molecule }{@code >}
     * {@link JAXBElement }{@code <}{@link Dimension }{@code >}
     * {@link JAXBElement }{@code <}{@link Join }{@code >}
     * {@link JAXBElement }{@code <}{@link Xaxis }{@code >}
     * {@link JAXBElement }{@code <}{@link SpectrumData }{@code >}
     * {@link JAXBElement }{@code <}{@link ReactantList }{@code >}
     * {@link JAXBElement }{@code <}{@link CellParameter }{@code >}
     * {@link JAXBElement }{@code <}{@link Dictionary }{@code >}
     * {@link JAXBElement }{@code <}{@link Symmetry }{@code >}
     * {@link JAXBElement }{@code <}{@link Lattice }{@code >}
     * {@link Element }
     * {@link JAXBElement }{@code <}{@link blurock.reaction.generated.core.List }{@code >}
     * {@link JAXBElement }{@code <}{@link ReactionScheme }{@code >}
     * {@link JAXBElement }{@code <}{@link Band }{@code >}
     * {@link JAXBElement }{@code <}{@link ProductList }{@code >}
     * {@link JAXBElement }{@code <}{@link Link }{@code >}
     * {@link JAXBElement }{@code <}{@link Sphere3 }{@code >}
     * {@link JAXBElement }{@code <}{@link ConditionList }{@code >}
     * {@link JAXBElement }{@code <}{@link Parameter }{@code >}
     * {@link JAXBElement }{@code <}{@link Bond }{@code >}
     * {@link JAXBElement }{@code <}{@link Spectrum }{@code >}
     * {@link JAXBElement }{@code <}{@link TableHeaderCell }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomSet }{@code >}
     * {@link JAXBElement }{@code <}{@link Observation }{@code >}
     * {@link JAXBElement }{@code <}{@link BondTypeList }{@code >}
     * {@link JAXBElement }{@code <}{@link TableRowList }{@code >}
     * {@link JAXBElement }{@code <}{@link MechanismComponent }{@code >}
     * {@link JAXBElement }{@code <}{@link ReactionStep }{@code >}
     * {@link JAXBElement }{@code <}{@link PeakList }{@code >}
     * {@link JAXBElement }{@code <}{@link Action }{@code >}
     * {@link JAXBElement }{@code <}{@link Point3 }{@code >}
     * {@link JAXBElement }{@code <}{@link Reaction }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomType }{@code >}
     * {@link JAXBElement }{@code <}{@link IsotopeList }{@code >}
     * {@link JAXBElement }{@code <}{@link java.lang.Object }{@code >}
     * {@link JAXBElement }{@code <}{@link Table }{@code >}
     * {@link JAXBElement }{@code <}{@link PotentialList }{@code >}
     * {@link JAXBElement }{@code <}{@link Metadata }{@code >}
     * {@link JAXBElement }{@code <}{@link ZMatrix }{@code >}
     * {@link JAXBElement }{@code <}{@link blurock.reaction.generated.core.Object }{@code >}
     * {@link JAXBElement }{@code <}{@link Yaxis }{@code >}
     * {@link JAXBElement }{@code <}{@link Eigen }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomicBasisFunction }{@code >}
     * {@link JAXBElement }{@code <}{@link Description }{@code >}
     * {@link JAXBElement }{@code <}{@link KpointList }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomTypeList }{@code >}
     * {@link JAXBElement }{@code <}{@link BandList }{@code >}
     * {@link JAXBElement }{@code <}{@link UnitTypeList }{@code >}
     * {@link JAXBElement }{@code <}{@link Peak }{@code >}
     * {@link JAXBElement }{@code <}{@link TableHeader }{@code >}
     * {@link JAXBElement }{@code <}{@link Plane3 }{@code >}
     * {@link JAXBElement }{@code <}{@link Array }{@code >}
     * {@link JAXBElement }{@code <}{@link Stmml }{@code >}
     * {@link JAXBElement }{@code <}{@link blurock.reaction.generated.core.ArrayList }{@code >}
     * {@link JAXBElement }{@code <}{@link Abundance }{@code >}
     * {@link JAXBElement }{@code <}{@link FragmentList }{@code >}
     * 
     * 
     */
    public java.util.List<java.lang.Object> getAnyCmlOrAnyOrAny() {
        if (anyCmlOrAnyOrAny == null) {
            anyCmlOrAnyOrAny = new java.util.ArrayList<java.lang.Object>();
        }
        return this.anyCmlOrAnyOrAny;
    }

    /**
     * Gets the value of the yFract property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getYFract() {
        return yFract;
    }

    /**
     * Sets the value of the yFract property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setYFract(Double value) {
        this.yFract = value;
    }

    /**
     * Gets the value of the isotopeNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIsotopeNumber() {
        return isotopeNumber;
    }

    /**
     * Sets the value of the isotopeNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIsotopeNumber(BigInteger value) {
        this.isotopeNumber = value;
    }

    /**
     * Gets the value of the pointGroupMultiplicity property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPointGroupMultiplicity() {
        return pointGroupMultiplicity;
    }

    /**
     * Sets the value of the pointGroupMultiplicity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPointGroupMultiplicity(BigInteger value) {
        this.pointGroupMultiplicity = value;
    }

    /**
     * Gets the value of the convention property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConvention() {
        return convention;
    }

    /**
     * Sets the value of the convention property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConvention(String value) {
        this.convention = value;
    }

    /**
     * Gets the value of the count property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setCount(Double value) {
        this.count = value;
    }

    /**
     * Gets the value of the hydrogenCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getHydrogenCount() {
        return hydrogenCount;
    }

    /**
     * Sets the value of the hydrogenCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setHydrogenCount(BigInteger value) {
        this.hydrogenCount = value;
    }

    /**
     * Gets the value of the isotopeRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsotopeRef() {
        return isotopeRef;
    }

    /**
     * Sets the value of the isotopeRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsotopeRef(String value) {
        this.isotopeRef = value;
    }

    /**
     * Gets the value of the spaceGroupMultiplicity property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSpaceGroupMultiplicity() {
        return spaceGroupMultiplicity;
    }

    /**
     * Sets the value of the spaceGroupMultiplicity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSpaceGroupMultiplicity(BigInteger value) {
        this.spaceGroupMultiplicity = value;
    }

    /**
     * Gets the value of the y2 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getY2() {
        return y2;
    }

    /**
     * Sets the value of the y2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setY2(Double value) {
        this.y2 = value;
    }

    /**
     * Gets the value of the occupancy property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getOccupancy() {
        return occupancy;
    }

    /**
     * Sets the value of the occupancy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setOccupancy(Double value) {
        this.occupancy = value;
    }

    /**
     * Gets the value of the x3 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getX3() {
        return x3;
    }

    /**
     * Sets the value of the x3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setX3(Double value) {
        this.x3 = value;
    }

    /**
     * Gets the value of the z3 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getZ3() {
        return z3;
    }

    /**
     * Sets the value of the z3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setZ3(Double value) {
        this.z3 = value;
    }

    /**
     * Gets the value of the dictRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDictRef() {
        return dictRef;
    }

    /**
     * Sets the value of the dictRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDictRef(String value) {
        this.dictRef = value;
    }

    /**
     * Gets the value of the elementType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElementType() {
        return elementType;
    }

    /**
     * Sets the value of the elementType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElementType(String value) {
        this.elementType = value;
    }

    /**
     * Gets the value of the x2 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getX2() {
        return x2;
    }

    /**
     * Sets the value of the x2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setX2(Double value) {
        this.x2 = value;
    }

    /**
     * Gets the value of the isotopeListRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsotopeListRef() {
        return isotopeListRef;
    }

    /**
     * Sets the value of the isotopeListRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsotopeListRef(String value) {
        this.isotopeListRef = value;
    }

    /**
     * Gets the value of the xFract property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getXFract() {
        return xFract;
    }

    /**
     * Sets the value of the xFract property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setXFract(Double value) {
        this.xFract = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the formalCharge property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFormalCharge() {
        return formalCharge;
    }

    /**
     * Sets the value of the formalCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFormalCharge(BigInteger value) {
        this.formalCharge = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the spinMultiplicity property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSpinMultiplicity() {
        return spinMultiplicity;
    }

    /**
     * Sets the value of the spinMultiplicity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSpinMultiplicity(BigInteger value) {
        this.spinMultiplicity = value;
    }

    /**
     * Gets the value of the role property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRole(String value) {
        this.role = value;
    }

    /**
     * Gets the value of the ref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRef() {
        return ref;
    }

    /**
     * Sets the value of the ref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRef(String value) {
        this.ref = value;
    }

    /**
     * Gets the value of the zFract property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getZFract() {
        return zFract;
    }

    /**
     * Sets the value of the zFract property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setZFract(Double value) {
        this.zFract = value;
    }

    /**
     * Gets the value of the y3 property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getY3() {
        return y3;
    }

    /**
     * Sets the value of the y3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setY3(Double value) {
        this.y3 = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public java.util.Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
