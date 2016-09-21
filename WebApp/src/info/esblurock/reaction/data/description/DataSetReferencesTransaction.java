package info.esblurock.reaction.data.description;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class DataSetReferencesTransaction extends DatabaseObject {
    @Persistent
    String user;
  
    @Persistent
    String fileCode;
    
    @Persistent
    String keyWord;

	public DataSetReferencesTransaction(String user, String fileCode, String keyWord) {
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
 
}
