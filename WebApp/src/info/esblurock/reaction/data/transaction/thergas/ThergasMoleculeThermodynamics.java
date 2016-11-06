package info.esblurock.reaction.data.transaction.thergas;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unindexed;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class ThergasMoleculeThermodynamics extends DatabaseObject {

   	@Persistent
	   String datasetKeyword;
  	@Persistent
	   String fileCode;
  	@Persistent
  		String tableName;
  	@Persistent
  		String moleculeName;
  	@Persistent
  	@Unindexed
  		Double standardEnthalpy;
  	@Persistent
  	@Unindexed
  		Double standardEntropy;
  	@Persistent
  	@Unindexed
  		ArrayList<Double> cpValues;
  	
  	public ThergasMoleculeThermodynamics() {
  		
  	}
  	
	public ThergasMoleculeThermodynamics(String datasetKeyword, String fileCode, String tableName, String moleculeName,
			Double standardEnthalpy, Double standardEntropy, ArrayList<Double> cpValues) {
		super();
		this.datasetKeyword = datasetKeyword;
		this.fileCode = fileCode;
		this.tableName = tableName;
		this.moleculeName = moleculeName;
		this.standardEnthalpy = standardEnthalpy;
		this.standardEntropy = standardEntropy;
		this.cpValues = cpValues;
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
	public Double getStandardEnthalpy() {
		return standardEnthalpy;
	}
	public Double getStandardEntropy() {
		return standardEntropy;
	}
	public ArrayList<Double> getCpValues() {
		return cpValues;
	}
	

}
