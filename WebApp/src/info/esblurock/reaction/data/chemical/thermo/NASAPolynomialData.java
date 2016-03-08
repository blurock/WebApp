package info.esblurock.reaction.data.chemical.thermo;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.chemical.molecule.isomer.IsomerData;

/**
 * The Class NASAPolynomialData
 * The information from a standard NASA polynomial form of thermodynamic data
 * with 7 coefficients in two ranges.
 * 
 */
@PersistenceCapable
public class NASAPolynomialData extends DatabaseObject {

	   /** The molecule name. */
   	@Persistent
	   String moleculeName;

   	@Persistent(dependent="true")
	   IsomerData moleculeComposition;

   	/** The phase. */
   	@Persistent
	   String phase;
   
	   /** The lower t. */
   	@Persistent
	   Double lowerT;
	   
   	/** The middle t. */
   	@Persistent
	   Double middleT;
	   
   	/** The upper t. */
   	@Persistent
	   Double upperT;
	
	   /** The upper. */
   	@Persistent
	   ArrayList<Double> upper;

	   /** The lower. */
   	@Persistent
	   ArrayList<Double> lower;

	/**
	 * Instantiates a new NASA polynomial data.
	 *
	 * @param moleculeName the molecule name (CHEMKIN name)
	 * @param isomer The composition information of the molecule
	 * @param lowerT the lower temperature
	 * @param middleT the middle temperature
	 * @param upperT the upper temperature
	 * @param upper the set of coefficients for the upper range
	 * @param lower the set of coefficients for the lower range
	 * @param phase the phase (as listed on the NASA polynomial
	 */
	public NASAPolynomialData(String moleculeName, IsomerData isomer, Double lowerT, Double middleT, Double upperT,
			ArrayList<Double> upper, ArrayList<Double> lower, String phase) {
		super();
		this.moleculeComposition = isomer;
		this.moleculeName = moleculeName;
		this.lowerT = lowerT;
		this.middleT = middleT;
		this.upperT = upperT;
		this.upper = upper;
		this.lower = lower;
		this.phase = phase;
	}

	/**
	 * Gets the molecule name. CHEMKIN name as appears in the mechanism
	 *
	 * @return the molecule name
	 */
	public String getMoleculeName() {
		return moleculeName;
	}

	/**
	 * Gets the phase. Usually G for gas phase
	 *
	 * @return the phase
	 */
	public String getPhase() {
		return phase;
	}

	/**
	 * Gets the lower t.
	 *
	 * @return the lower t
	 */
	public Double getLowerT() {
		return lowerT;
	}

	/**
	 * Gets the middle t.
	 *
	 * @return the middle t
	 */
	public Double getMiddleT() {
		return middleT;
	}

	/**
	 * Gets the upper t.
	 *
	 * @return the upper t
	 */
	public Double getUpperT() {
		return upperT;
	}

	/**
	 * Gets the set of coefficients for the upper range
	 *
	 * @return the upper
	 */
	public ArrayList<Double> getUpper() {
		return upper;
	}

	/**
	 * Gets the lower.
	 *
	 * @return the set of coefficients for the lower range
	 */
	public ArrayList<Double> getLower() {
		return lower;
	}

	public IsomerData getMoleculeComposition() {
		return moleculeComposition;
	}

	   

}
