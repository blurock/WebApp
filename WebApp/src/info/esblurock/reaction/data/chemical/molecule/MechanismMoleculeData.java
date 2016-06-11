package info.esblurock.reaction.data.chemical.molecule;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class MechanismMoleculeData extends DatabaseObject {

	private static final long serialVersionUID = 1L;

    @Persistent
    String user;

    @Persistent
	String moleculeName;
	
	@Persistent
	String mechanismKeyword;
	
	MechanismMoleculeData() {
		this.moleculeName = null;
		this.mechanismKeyword = null;
		this.user = null;
	}

	
	public MechanismMoleculeData(String user, String moleculeKeyword, String mechanismKeyword) {
		super();
		this.moleculeName = moleculeKeyword;
		this.mechanismKeyword = mechanismKeyword;
		this.user = user;
	}

	public String getMoleculeName() {
		return moleculeName;
	}

	public String getMechanismKeyword() {
		return mechanismKeyword;
	}
	public String getUser() {
		return user;
	}
	
}
