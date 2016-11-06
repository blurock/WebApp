package info.esblurock.reaction.data.chemical.thermo;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unindexed;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class ThergasMoleculeData extends DatabaseObject {

   	@Persistent
	   String datasetKeyword;
  	@Persistent
	   String fileCode;
  	@Persistent
  		String tableName;
  	@Persistent
  		String moleculeName;
  	@Persistent
  		String nancyStructureString;
  	@Persistent
	@Unindexed
  		Double opticSymmetry;
  	@Persistent
	@Unindexed
  		Double internalSymmetry;
  	@Persistent
	@Unindexed
  		Double externalSymmetry;
  	@Persistent
	@Unindexed
  		ArrayList<String> atomNames;
  	@Persistent
	@Unindexed
		ArrayList<Integer> atomCounts;
  	@Persistent
	@Unindexed
  		String dateString;
  	
  	
  	public ThergasMoleculeData() {
  	}
  	
	public ThergasMoleculeData(String datasetKeyword, String fileCode, String tableName, 
			String moleculeName,String nancyStructureString, 
			double opticSymmetry, double internalSymmetry, double externalSymmetry,
			ArrayList<String> atomNames, ArrayList<Integer> atomCounts, 
			String dateString) {
		super();
		this.datasetKeyword = datasetKeyword;
		this.fileCode = fileCode;
		this.tableName = tableName;
		this.moleculeName = moleculeName;
		this.nancyStructureString = nancyStructureString;
		this.opticSymmetry = new Double(opticSymmetry);
		this.internalSymmetry = new Double(internalSymmetry);
		this.externalSymmetry = new Double(externalSymmetry);
		this.atomNames = atomNames;
		this.atomCounts = atomCounts;
		this.dateString = dateString;
	}
	public String getDatasetKeyword() {
		return datasetKeyword;
	}
	public String getFileCode() {
		return fileCode;
	}
	public String getTableName() {
		return tableName;
	}
	public String getMoleculeName() {
		return moleculeName;
	}
	public String getNancyStructureString() {
		return nancyStructureString;
	}
	public Double getOpticSymmetry() {
		return opticSymmetry;
	}
	public Double getInternalSymmetry() {
		return internalSymmetry;
	}
	public Double getExternalSymmetry() {
		return externalSymmetry;
	}
	public ArrayList<String> getAtomNames() {
		return atomNames;
	}
	public ArrayList<Integer> getAtomCounts() {
		return atomCounts;
	}
	public String getDateString() {
		return dateString;
	}
  	
   	

}
