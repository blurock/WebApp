package info.esblurock.reaction.data.transaction.reaction;

import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

public class ReactSDFMoleculesToDatabaseTransaction extends DatabaseObject {
 
	@Persistent
    Integer moleculeCount;

    @Persistent
    String user;
  
    @Persistent
    String fileCode;
    
    @Persistent
    String keyWord;

    public ReactSDFMoleculesToDatabaseTransaction() {
	}
	public ReactSDFMoleculesToDatabaseTransaction(String user, String fileCode, String keyWord, Integer moleculeCount) {
		super();
		this.user = user;
		this.fileCode = fileCode;
		this.keyWord = keyWord;
		this.moleculeCount = moleculeCount;
	}
	public Integer getMoleculeCount() {
		return moleculeCount;
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
	
}
