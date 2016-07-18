package info.esblurock.reaction.data.chemical.mechanism;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

/**
 * The Class ChemicalMechanismData which stores all the information from a CHEMKIN mech file
 */
@PersistenceCapable
public class ChemicalMechanismData extends DatabaseObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
	@Persistent
	String mechansmName;
	
	@Persistent
	Integer numberOfElements;
	
	@Persistent
	Integer numberOfSpecies;
	
	@Persistent
	Integer numberOfReactions;


	
	public ChemicalMechanismData() {
		super();
	}



	public ChemicalMechanismData(String mechansmName, Integer numberOfElements,
			Integer numberOfSpecies, Integer numberOfReactions) {
		super();
		this.mechansmName = mechansmName;
		this.numberOfElements = numberOfElements;
		this.numberOfSpecies = numberOfSpecies;
		this.numberOfReactions = numberOfReactions;
	}

	public Integer getNumberOfElements() {
		return numberOfElements;
	}



	public void setNumberOfElements(Integer numberOfElements) {
		this.numberOfElements = numberOfElements;
	}



	public Integer getNumberOfSpecies() {
		return numberOfSpecies;
	}



	public void setNumberOfSpecies(Integer numberOfSpecies) {
		this.numberOfSpecies = numberOfSpecies;
	}



	public Integer getNumberOfReactions() {
		return numberOfReactions;
	}



	public void setNumberOfReactions(Integer numberOfReactions) {
		this.numberOfReactions = numberOfReactions;
	}

	public String getMechansmName() {
		return mechansmName;
	}

	
}
