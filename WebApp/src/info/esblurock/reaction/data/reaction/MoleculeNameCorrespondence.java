package info.esblurock.reaction.data.reaction;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class MoleculeNameCorrespondence extends DatabaseObject {

    @Persistent
    String moleculeStandardName;

    @Persistent
    String nameType;

    @Persistent
    String substituteName;

	public MoleculeNameCorrespondence(String moleculeStandardName, String nameType, String substituteName) {
		super();
		this.moleculeStandardName = moleculeStandardName;
		this.nameType = nameType;
		this.substituteName = substituteName;
	}

	public String getMoleculeStandardName() {
		return moleculeStandardName;
	}

	public String getNameType() {
		return nameType;
	}

	public String getSubstituteName() {
		return substituteName;
	}
}
