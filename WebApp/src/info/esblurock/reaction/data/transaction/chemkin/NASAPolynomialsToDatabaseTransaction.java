package info.esblurock.reaction.data.transaction.chemkin;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class NASAPolynomialsToDatabaseTransaction  extends DatabaseObject {

    @Persistent
    Integer polynomialCount;

    @Persistent
    String user;
  
    @Persistent
    String fileCode;
    
    @Persistent
    String keyWord;

    
	public NASAPolynomialsToDatabaseTransaction() {
		super();
	}

	public NASAPolynomialsToDatabaseTransaction(String user, String fileCode, String keyWord, Integer polynomialCount) {
		super();
		this.polynomialCount = polynomialCount;
		this.user = user;
		this.fileCode = fileCode;
		this.keyWord = keyWord;
	}

	public Integer getPolynomialCount() {
		return polynomialCount;
	}

	public void setPolynomialCount(Integer polynomialCount) {
		this.polynomialCount = polynomialCount;
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
