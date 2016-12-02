package info.esblurock.reaction.data.upload;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public class ValidatedFileTransaction  extends DatabaseObject {
    @Persistent
    String user;
  
    @Persistent
    String filename;
    
    @Persistent
    String fileCode;
    
    @Persistent
    String sourceType;

	public ValidatedFileTransaction() {
		super();
	}

	public ValidatedFileTransaction(String user, String filename, String fileCode, String sourceType) {
		super();
		this.user = user;
		this.filename = filename;
		this.fileCode = fileCode;
		this.sourceType = sourceType;
	}

	public String getUser() {
		return user;
	}

	public String getFilename() {
		return filename;
	}

	public String getFileCode() {
		return fileCode;
	}

	public String getSourceType() {
		return sourceType;
	}

}
