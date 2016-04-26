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
	String mechanismKeyword;

	@Persistent
	Integer numberOfPolynomials;
	
	/**
	 * Instantiates a new sets the of NASA polynomial data.
	 * Initially it is empty, one element is added at a time through addThermo
	 */
	public SetOfNASAPolynomialData(String mechanismKeyword, int numberOfPolynomials) {
		this.mechanismKeyword = mechanismKeyword;
		this.numberOfPolynomials = new Integer(numberOfPolynomials);
	}

	public String getMechanismKeyword() {
		return mechanismKeyword;
	}

	public Integer getNumberOfPolynomials() {
		return numberOfPolynomials;
	}	

}
