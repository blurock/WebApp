package info.esblurock.reaction.data.transaction.chemkin;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class TransportPropertiesToDatabaseTransaction extends DatabaseObject {

    @Persistent
    Integer transportCount;

    @Persistent
    String user;
  
    @Persistent
    String fileCode;
    
    @Persistent
    String keyWord;

	public TransportPropertiesToDatabaseTransaction() {
		super();
	}

	public TransportPropertiesToDatabaseTransaction(String user, String fileCode,
			String keyWord,Integer transportCount) {
		super();
		this.transportCount = transportCount;
		this.user = user;
		this.fileCode = fileCode;
		this.keyWord = keyWord;
	}

	public Integer getTransportCount() {
		return transportCount;
	}

	public void setTransportCount(Integer transportCount) {
		this.transportCount = transportCount;
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
