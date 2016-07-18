package info.esblurock.reaction.data.chemical.molecule;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class MechanismMoleculeData extends DatabaseObject {

	private static final long serialVersionUID = 1L;

    @Persistent
	String moleculeName;
	
	@Persistent
	String mechanismKeyword;
	
	public MechanismMoleculeData() {
		this.moleculeName = null;
		this.mechanismKeyword = null;
	}

	
	public MechanismMoleculeData(String user, String moleculeKeyword, String mechanismKeyword) {
		super();
		this.moleculeName = moleculeKeyword;
		this.mechanismKeyword = mechanismKeyword;
	}

	public String getMoleculeName() {
		return moleculeName;
	}

	public String getMechanismKeyword() {
		return mechanismKeyword;
	}
}
