package info.esblurock.reaction.data.chemical.transport;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class SetOfSpeciesTransportProperties {
	/** The set. */
	@Persistent
	String mechanismKeyword;

	@Persistent
	Integer numberOfPolynomials;

	public SetOfSpeciesTransportProperties() {
	}
	
	public SetOfSpeciesTransportProperties(String mechanismKeyword, Integer numberOfPolynomials) {
		super();
		this.mechanismKeyword = mechanismKeyword;
		this.numberOfPolynomials = numberOfPolynomials;
	}

	public String getMechanismKeyword() {
		return mechanismKeyword;
	}

	public Integer getNumberOfPolynomials() {
		return numberOfPolynomials;
	}

}
