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
   
	   /** The lower bound temperature. */
   	@Persistent
	   Double lowerT;
	   
   	/** The middle (common) temperature between low and high temperature sets. */
   	@Persistent
	   Double middleT;
	   
   	/** The upper temperature of range. */
   	@Persistent
	   Double upperT;
	
   	/** The computed standard enthaply. */
   	@Persistent
	   Double standardEnthalpy;
	
   	/** The computed standard entropy. */
   	@Persistent
	   Double standardEntropy;
	
	   /** The upper. */
   	@Persistent
	   ArrayList<Double> upper;

	   /** The lower. */
   	@Persistent
	   ArrayList<Double> lower;

   	
   	public NASAPolynomialData() {
   	}
	/**
	 * Instantiates a new NASA polynomial data.
	 *
	 * @param moleculeName the molecule name (CHEMKIN name)
	 * @param isomer The composition information of the molecule
	 * @param lowerT the lower temperature
	 * @param middleT the middle temperature
	 * @param upperT the upper temperature
	 * @param enthalpy the computed standard enthalpy (298 C)
	 * @param entropy the computed standard entropy (298 C)
	 * @param upper the set of coefficients for the upper range
	 * @param lower the set of coefficients for the lower range
	 * @param phase the phase (as listed on the NASA polynomial
	 */
	public NASAPolynomialData(String moleculeName, IsomerData isomer, 
			Double lowerT, Double middleT, Double upperT,
			Double enthalpy, double entropy,
			ArrayList<Double> upper, ArrayList<Double> lower, String phase) {
		super();
		System.out.println("NASAPolynomialData: " + isomer.getAtomCounts().toString());
		this.moleculeComposition = isomer;
		
		this.moleculeName = moleculeName;
		this.lowerT = lowerT;
		this.middleT = middleT;
		this.upperT = upperT;
		this.standardEnthalpy = enthalpy;
		this.standardEntropy = entropy;
		this.upper = upper;
		this.lower = lower;
		this.phase = phase;
		System.out.println("NASAPolynomialData: " + moleculeComposition.getAtomCounts().toString());
		System.out.println("NASAPolynomialData: " + isomer.getAtomCounts().toString());
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
	public Double getStandardEnthalpy() {
		return standardEnthalpy;
	}
	public Double getStandardEntropy() {
		return standardEntropy;
	}
}
