package info.esblurock.reaction.data.transaction.chemkin.rdf;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;
@PersistenceCapable
public class MechanismMoleculeRDFTransaction extends DatabaseObject{
    @Persistent
    String user;
  
    @Persistent
    String fileCode;
    
    @Persistent
    String keyWord;

    @Persistent
   Integer rdfCount;

	public MechanismMoleculeRDFTransaction(String user, String fileCode, String keyWord) {
		super();
		this.user = user;
		this.fileCode = fileCode;
		this.keyWord = keyWord;
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

	public Integer getRdfCount() {
		return rdfCount;
	}

	public void setRdfCount(Integer rdfCount) {
		this.rdfCount = rdfCount;
	}

}
