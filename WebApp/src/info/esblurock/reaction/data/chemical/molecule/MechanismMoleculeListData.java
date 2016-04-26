package info.esblurock.reaction.data.chemical.molecule;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class MechanismMoleculeListData extends DatabaseObject {

	/** The nasa set. */
	@Persistent
	String mechanismKeyword;

	@Persistent
	Integer numberOfMolecules;
	

	public MechanismMoleculeListData() {
		super();
	}

	public MechanismMoleculeListData(String mechanismKeyword, Integer numberOfMolecules) {
		super();
		this.mechanismKeyword = mechanismKeyword;
		this.numberOfMolecules = numberOfMolecules;
	}

	public String getMechanismKeyword() {
		return mechanismKeyword;
	}

	public Integer getNumberOfMolecules() {
		return numberOfMolecules;
	}	
}
