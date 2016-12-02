package info.esblurock.reaction.data.chemical.molecule;

import java.util.ArrayList;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class SDFMoleculeStructure extends DatabaseObject {

    @Persistent
	String cmlStructure;
    @Persistent
	String fileCode;
    @Persistent
	String dataSetKeyword;
    @Persistent
    String moleculeName;
    @Persistent
	String inchi;
    @Persistent
	String SMILES;
    @Persistent
    String isomerName;
    @Persistent
    String carbonSet;

	public SDFMoleculeStructure(String dataSetKeyword, String moleculeName, String cmlStructure) {
    	this.dataSetKeyword = dataSetKeyword;
    	this.moleculeName = moleculeName;
       	this.inchi = null;
    	this.SMILES = null;
    	this.isomerName = null;
 		this.cmlStructure = cmlStructure;
	}

	public String getCmlStructure() {
		return cmlStructure;
	}
	public String getInchi() {
		return inchi;
	}

	public void setInchi(String inchi) {
		this.inchi = inchi;
	}

	public String getSMILES() {
		return SMILES;
	}

	public void setSMILES(String sMILES) {
		SMILES = sMILES;
	}

	public String getIsomerName() {
		return isomerName;
	}

	public void setIsomerName(String isomerName) {
		this.isomerName = isomerName;
	}

	public String getMoleculeName() {
		return moleculeName;
	}

	public String getCarbonSet() {
		return carbonSet;
	}

	public void setCarbonSet(String carbonSet) {
		this.carbonSet = carbonSet;
	}

}
