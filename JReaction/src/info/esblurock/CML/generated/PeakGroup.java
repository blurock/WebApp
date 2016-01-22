//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.08 at 05:37:51 PM CEST 
//


package info.esblurock.CML.generated;

import java.util.HashMap;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
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
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}id"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}ref"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}peakUnits"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}xMin"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}bondRefs"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}xWidth"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}title"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}yWidth"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}atomRefs"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}xValue"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}integral"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}convention"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}xUnits"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}peakShape"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}xMax"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}yMin"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}yUnits"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}dictRef"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}peakHeight"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}yMax"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}yValue"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}peakMultiplicity"/>
 *       &lt;attGroup ref="{http://www.xml-cml.org/schema}moleculeRefs"/>
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
public class PeakGroup {

    @XmlElementRef(name = "anyCml", namespace = "http://www.xml-cml.org/schema", type = JAXBElement.class, required = false)
    @XmlAnyElement(lax = true)
    protected java.util.List<java.lang.Object> anyCmlOrAnyOrAny;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "ref")
    protected String ref;
    @XmlAttribute(name = "peakUnits")
    protected String peakUnits;
    @XmlAttribute(name = "xMin")
    protected Double xMin;
    @XmlAttribute(name = "bondRefs")
    protected java.util.List<String> bondRefs;
    @XmlAttribute(name = "xWidth")
    protected Double xWidth;
    @XmlAttribute(name = "title")
    protected String title;
    @XmlAttribute(name = "yWidth")
    protected Double yWidth;
    @XmlAttribute(name = "atomRefs")
    protected java.util.List<String> atomRefs;
    @XmlAttribute(name = "xValue")
    protected Double xValue;
    @XmlAttribute(name = "integral")
    protected String integral;
    @XmlAttribute(name = "convention")
    protected String convention;
    @XmlAttribute(name = "xUnits")
    protected String xUnits;
    @XmlAttribute(name = "peakShape")
    protected PeakShapeType peakShape;
    @XmlAttribute(name = "xMax")
    protected Double xMax;
    @XmlAttribute(name = "yMin")
    protected Double yMin;
    @XmlAttribute(name = "yUnits")
    protected String yUnits;
    @XmlAttribute(name = "dictRef")
    protected String dictRef;
    @XmlAttribute(name = "peakHeight")
    protected Double peakHeight;
    @XmlAttribute(name = "yMax")
    protected Double yMax;
    @XmlAttribute(name = "yValue")
    protected Double yValue;
    @XmlAttribute(name = "peakMultiplicity")
    protected PeakMultiplicityType peakMultiplicity;
    @XmlAttribute(name = "moleculeRefs")
    protected java.util.List<String> moleculeRefs;
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
     * {@link JAXBElement }{@code <}{@link AtomTypeList }{@code >}
     * {@link JAXBElement }{@code <}{@link Length }{@code >}
     * {@link JAXBElement }{@code <}{@link Definition }{@code >}
     * {@link JAXBElement }{@code <}{@link Cml }{@code >}
     * {@link JAXBElement }{@code <}{@link UnitList }{@code >}
     * {@link JAXBElement }{@code <}{@link MoleculeList }{@code >}
     * {@link JAXBElement }{@code <}{@link Stmml }{@code >}
     * {@link JAXBElement }{@code <}{@link Isotope }{@code >}
     * {@link JAXBElement }{@code <}{@link PropertyList }{@code >}
     * {@link JAXBElement }{@code <}{@link Spectator }{@code >}
     * {@link JAXBElement }{@code <}{@link TableHeaderCell }{@code >}
     * {@link JAXBElement }{@code <}{@link BondTypeList }{@code >}
     * {@link JAXBElement }{@code <}{@link TableCell }{@code >}
     * {@link JAXBElement }{@code <}{@link PotentialForm }{@code >}
     * {@link JAXBElement }{@code <}{@link Atom }{@code >}
     * {@link JAXBElement }{@code <}{@link Substance }{@code >}
     * {@link JAXBElement }{@code <}{@link Lattice }{@code >}
     * {@link JAXBElement }{@code <}{@link ParameterList }{@code >}
     * {@link JAXBElement }{@code <}{@link Region }{@code >}
     * {@link JAXBElement }{@code <}{@link Reaction }{@code >}
     * {@link JAXBElement }{@code <}{@link ReactiveCentre }{@code >}
     * {@link JAXBElement }{@code <}{@link Angle }{@code >}
     * {@link JAXBElement }{@code <}{@link Array }{@code >}
     * {@link JAXBElement }{@code <}{@link ZMatrix }{@code >}
     * {@link JAXBElement }{@code <}{@link KpointList }{@code >}
     * {@link JAXBElement }{@code <}{@link Table }{@code >}
     * {@link JAXBElement }{@code <}{@link CellParameter }{@code >}
     * {@link JAXBElement }{@code <}{@link Abundance }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomArray }{@code >}
     * {@link JAXBElement }{@code <}{@link Observation }{@code >}
     * {@link JAXBElement }{@code <}{@link SpectatorList }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomType }{@code >}
     * {@link JAXBElement }{@code <}{@link Crystal }{@code >}
     * {@link JAXBElement }{@code <}{@link TransitionState }{@code >}
     * {@link JAXBElement }{@code <}{@link Name }{@code >}
     * {@link JAXBElement }{@code <}{@link BondStereo }{@code >}
     * {@link JAXBElement }{@code <}{@link ReactionStepList }{@code >}
     * {@link JAXBElement }{@code <}{@link ConditionList }{@code >}
     * {@link JAXBElement }{@code <}{@link BondSet }{@code >}
     * {@link JAXBElement }{@code <}{@link Parameter }{@code >}
     * {@link JAXBElement }{@code <}{@link PeakStructure }{@code >}
     * {@link Element }
     * {@link JAXBElement }{@code <}{@link TableRow }{@code >}
     * {@link JAXBElement }{@code <}{@link Dimension }{@code >}
     * {@link JAXBElement }{@code <}{@link Torsion }{@code >}
     * {@link JAXBElement }{@code <}{@link FragmentList }{@code >}
     * {@link JAXBElement }{@code <}{@link Link }{@code >}
     * {@link JAXBElement }{@code <}{@link Dictionary }{@code >}
     * {@link JAXBElement }{@code <}{@link ReactionScheme }{@code >}
     * {@link JAXBElement }{@code <}{@link PeakList }{@code >}
     * {@link JAXBElement }{@code <}{@link Identifier }{@code >}
     * {@link JAXBElement }{@code <}{@link Line3 }{@code >}
     * {@link JAXBElement }{@code <}{@link TableHeader }{@code >}
     * {@link JAXBElement }{@code <}{@link Join }{@code >}
     * {@link JAXBElement }{@code <}{@link Action }{@code >}
     * {@link JAXBElement }{@code <}{@link Reactant }{@code >}
     * {@link JAXBElement }{@code <}{@link info.esblurock.CML.generated.Map }{@code >}
     * {@link JAXBElement }{@code <}{@link SpectrumList }{@code >}
     * {@link JAXBElement }{@code <}{@link Plane3 }{@code >}
     * {@link JAXBElement }{@code <}{@link PotentialList }{@code >}
     * {@link JAXBElement }{@code <}{@link ReactionList }{@code >}
     * {@link JAXBElement }{@code <}{@link Electron }{@code >}
     * {@link JAXBElement }{@code <}{@link Sphere3 }{@code >}
     * {@link JAXBElement }{@code <}{@link IsotopeList }{@code >}
     * {@link JAXBElement }{@code <}{@link Product }{@code >}
     * {@link JAXBElement }{@code <}{@link Module }{@code >}
     * {@link JAXBElement }{@code <}{@link UnitType }{@code >}
     * {@link JAXBElement }{@code <}{@link Label }{@code >}
     * {@link JAXBElement }{@code <}{@link SubstanceList }{@code >}
     * {@link JAXBElement }{@code <}{@link Yaxis }{@code >}
     * {@link JAXBElement }{@code <}{@link Molecule }{@code >}
     * {@link JAXBElement }{@code <}{@link Fragment }{@code >}
     * {@link JAXBElement }{@code <}{@link Band }{@code >}
     * {@link JAXBElement }{@code <}{@link Scalar }{@code >}
     * {@link JAXBElement }{@code <}{@link info.esblurock.CML.generated.ArrayList }{@code >}
     * {@link JAXBElement }{@code <}{@link Eigen }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomSet }{@code >}
     * {@link java.lang.Object }
     * {@link JAXBElement }{@code <}{@link System }{@code >}
     * {@link JAXBElement }{@code <}{@link Formula }{@code >}
     * {@link JAXBElement }{@code <}{@link Documentation }{@code >}
     * {@link JAXBElement }{@code <}{@link Symmetry }{@code >}
     * {@link JAXBElement }{@code <}{@link Amount }{@code >}
     * {@link JAXBElement }{@code <}{@link BandList }{@code >}
     * {@link JAXBElement }{@code <}{@link Metadata }{@code >}
     * {@link JAXBElement }{@code <}{@link Bond }{@code >}
     * {@link JAXBElement }{@code <}{@link MechanismComponent }{@code >}
     * {@link JAXBElement }{@code <}{@link TableContent }{@code >}
     * {@link JAXBElement }{@code <}{@link UnitTypeList }{@code >}
     * {@link JAXBElement }{@code <}{@link Kpoint }{@code >}
     * {@link JAXBElement }{@code <}{@link Point3 }{@code >}
     * {@link JAXBElement }{@code <}{@link BasisSet }{@code >}
     * {@link JAXBElement }{@code <}{@link java.lang.Object }{@code >}
     * {@link JAXBElement }{@code <}{@link Sample }{@code >}
     * {@link JAXBElement }{@code <}{@link Xaxis }{@code >}
     * {@link JAXBElement }{@code <}{@link BondType }{@code >}
     * {@link JAXBElement }{@code <}{@link SpectrumData }{@code >}
     * {@link JAXBElement }{@code <}{@link Gradient }{@code >}
     * {@link JAXBElement }{@code <}{@link Vector3 }{@code >}
     * {@link JAXBElement }{@code <}{@link Potential }{@code >}
     * {@link JAXBElement }{@code <}{@link Peak }{@code >}
     * {@link JAXBElement }{@code <}{@link ReactionStep }{@code >}
     * {@link JAXBElement }{@code <}{@link Transform3 }{@code >}
     * {@link JAXBElement }{@code <}{@link BondArray }{@code >}
     * {@link JAXBElement }{@code <}{@link info.esblurock.CML.generated.List }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomParity }{@code >}
     * {@link JAXBElement }{@code <}{@link Matrix }{@code >}
     * {@link JAXBElement }{@code <}{@link ReactantList }{@code >}
     * {@link JAXBElement }{@code <}{@link MetadataList }{@code >}
     * {@link JAXBElement }{@code <}{@link LatticeVector }{@code >}
     * {@link JAXBElement }{@code <}{@link PeakGroup }{@code >}
     * {@link JAXBElement }{@code <}{@link Particle }{@code >}
     * {@link JAXBElement }{@code <}{@link info.esblurock.CML.generated.Object }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomicBasisFunction }{@code >}
     * {@link JAXBElement }{@code <}{@link Unit }{@code >}
     * {@link JAXBElement }{@code <}{@link Mechanism }{@code >}
     * {@link JAXBElement }{@code <}{@link Property }{@code >}
     * {@link JAXBElement }{@code <}{@link TableRowList }{@code >}
     * {@link JAXBElement }{@code <}{@link Spectrum }{@code >}
     * {@link JAXBElement }{@code <}{@link ActionList }{@code >}
     * {@link JAXBElement }{@code <}{@link Entry }{@code >}
     * {@link JAXBElement }{@code <}{@link Description }{@code >}
     * {@link JAXBElement }{@code <}{@link ProductList }{@code >}
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
     * Gets the value of the peakUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPeakUnits() {
        return peakUnits;
    }

    /**
     * Sets the value of the peakUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPeakUnits(String value) {
        this.peakUnits = value;
    }

    /**
     * Gets the value of the xMin property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getXMin() {
        return xMin;
    }

    /**
     * Sets the value of the xMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setXMin(Double value) {
        this.xMin = value;
    }

    /**
     * Gets the value of the bondRefs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bondRefs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBondRefs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public java.util.List<String> getBondRefs() {
        if (bondRefs == null) {
            bondRefs = new java.util.ArrayList<String>();
        }
        return this.bondRefs;
    }

    /**
     * Gets the value of the xWidth property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getXWidth() {
        return xWidth;
    }

    /**
     * Sets the value of the xWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setXWidth(Double value) {
        this.xWidth = value;
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
     * Gets the value of the yWidth property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getYWidth() {
        return yWidth;
    }

    /**
     * Sets the value of the yWidth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setYWidth(Double value) {
        this.yWidth = value;
    }

    /**
     * Gets the value of the atomRefs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the atomRefs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAtomRefs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public java.util.List<String> getAtomRefs() {
        if (atomRefs == null) {
            atomRefs = new java.util.ArrayList<String>();
        }
        return this.atomRefs;
    }

    /**
     * Gets the value of the xValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getXValue() {
        return xValue;
    }

    /**
     * Sets the value of the xValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setXValue(Double value) {
        this.xValue = value;
    }

    /**
     * Gets the value of the integral property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntegral() {
        return integral;
    }

    /**
     * Sets the value of the integral property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntegral(String value) {
        this.integral = value;
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
     * Gets the value of the xUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXUnits() {
        return xUnits;
    }

    /**
     * Sets the value of the xUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXUnits(String value) {
        this.xUnits = value;
    }

    /**
     * Gets the value of the peakShape property.
     * 
     * @return
     *     possible object is
     *     {@link PeakShapeType }
     *     
     */
    public PeakShapeType getPeakShape() {
        return peakShape;
    }

    /**
     * Sets the value of the peakShape property.
     * 
     * @param value
     *     allowed object is
     *     {@link PeakShapeType }
     *     
     */
    public void setPeakShape(PeakShapeType value) {
        this.peakShape = value;
    }

    /**
     * Gets the value of the xMax property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getXMax() {
        return xMax;
    }

    /**
     * Sets the value of the xMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setXMax(Double value) {
        this.xMax = value;
    }

    /**
     * Gets the value of the yMin property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getYMin() {
        return yMin;
    }

    /**
     * Sets the value of the yMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setYMin(Double value) {
        this.yMin = value;
    }

    /**
     * Gets the value of the yUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYUnits() {
        return yUnits;
    }

    /**
     * Sets the value of the yUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYUnits(String value) {
        this.yUnits = value;
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
     * Gets the value of the peakHeight property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPeakHeight() {
        return peakHeight;
    }

    /**
     * Sets the value of the peakHeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPeakHeight(Double value) {
        this.peakHeight = value;
    }

    /**
     * Gets the value of the yMax property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getYMax() {
        return yMax;
    }

    /**
     * Sets the value of the yMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setYMax(Double value) {
        this.yMax = value;
    }

    /**
     * Gets the value of the yValue property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getYValue() {
        return yValue;
    }

    /**
     * Sets the value of the yValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setYValue(Double value) {
        this.yValue = value;
    }

    /**
     * Gets the value of the peakMultiplicity property.
     * 
     * @return
     *     possible object is
     *     {@link PeakMultiplicityType }
     *     
     */
    public PeakMultiplicityType getPeakMultiplicity() {
        return peakMultiplicity;
    }

    /**
     * Sets the value of the peakMultiplicity property.
     * 
     * @param value
     *     allowed object is
     *     {@link PeakMultiplicityType }
     *     
     */
    public void setPeakMultiplicity(PeakMultiplicityType value) {
        this.peakMultiplicity = value;
    }

    /**
     * Gets the value of the moleculeRefs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the moleculeRefs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMoleculeRefs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public java.util.List<String> getMoleculeRefs() {
        if (moleculeRefs == null) {
            moleculeRefs = new java.util.ArrayList<String>();
        }
        return this.moleculeRefs;
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
