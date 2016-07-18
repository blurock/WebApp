package info.esblurock.reaction.data.transaction.chemkin;
        
import java.util.Map;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class MechanismMoleculesToDatabaseTransaction extends DatabaseObject {

    @Persistent
    Integer moleculeCount;

    @Persistent
    String user;
  
    @Persistent
    String fileCode;
    
    @Persistent
    String keyWord;
    
    @Persistent
    Map<String,String> moleculeMap;
    
	public MechanismMoleculesToDatabaseTransaction() {
		super();
	}
	public MechanismMoleculesToDatabaseTransaction(String user, String fileCode, String keyWord, Integer moleculeCount) {
		super();
		this.user = user;
		this.fileCode = fileCode;
		this.keyWord = keyWord;
		this.moleculeCount = moleculeCount;
	}

	public String getUser() {
		return user;
	}

	public String getFileCode() {
		return fileCode;
	}

	public String getKeyWord() {
		return keyWord;
	}
	public Integer getMoleculeCount() {
		return moleculeCount;
	}
	public void setMoleculeCount(Integer moleculeCount) {
		this.moleculeCount = moleculeCount;
	}
	public Map<String, String> getMoleculeMap() {
		return moleculeMap;
	}
	public void setMoleculeMap(Map<String, String> moleculeMap) {
		this.moleculeMap = moleculeMap;
	}

}
