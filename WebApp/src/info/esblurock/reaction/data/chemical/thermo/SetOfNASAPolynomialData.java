package info.esblurock.reaction.data.chemical.thermo;

import java.util.ArrayList;
import java.util.HashMap;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

// TODO: Auto-generated Javadoc
/**
 * The Class SetOfNASAPolynomialData.
 * This is a 'complete' set of NASA polynomials from a single source 
 * such as a mechanism or a thermodynamic data set.
 */
@PersistenceCapable
public class SetOfNASAPolynomialData extends DatabaseObject {
	
	/** The nasa set. */
	@Persistent
	@Element(dependent="true")
	ArrayList<NASAPolynomialData> nasaSet;

	/**
	 * Instantiates a new sets the of NASA polynomial data.
	 * Initially it is empty, one element is added at a time through addThermo
	 */
	public SetOfNASAPolynomialData() {
		nasaSet = new ArrayList<NASAPolynomialData>();
	}
	
	/**
	 * Adds the NASA polynomial to the set
	 * 
	 * The molecule name (within the consistent set) is used as the key to the HashMap.
	 *
	 * @param nasa the NASA polynomial to add
	 * @return the key name used
	 */
	public String addThermo(NASAPolynomialData nasa) {
		nasaSet.add(nasa);
		return nasa.getMoleculeName();
	}

	/**
	 * Get NASA polynomials for this set
	 *
	 * @return the set of {@link NASAPolynomialData} (as a {@link HashMap})
	 */
	public ArrayList<NASAPolynomialData> getNasaSet() {
		return nasaSet;
	}
	
}
