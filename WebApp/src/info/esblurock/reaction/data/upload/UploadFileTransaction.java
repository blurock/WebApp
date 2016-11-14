package info.esblurock.reaction.data.upload;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class UploadFileTransaction extends DatabaseObject {
    @Persistent
    String user;
  
    @Persistent
    String filename;
    
    @Persistent
    String fileCode;
    
    @Persistent
    String sourceType;
    
    @Persistent
    Integer lineCount;
    
    public UploadFileTransaction() {
	}

	public UploadFileTransaction(String user, String filename, String fileCode,String sourceType, Integer lineCount) {
		super();
		this.user = user;
		this.filename = filename;
		this.sourceType = sourceType;
		this.fileCode = fileCode;
		this.lineCount = lineCount;
	}

	public void fillInParameters(String user, String filename, String fileCode,String sourceType, Integer lineCount) {
		this.user = user;
		this.filename = filename;
		this.sourceType = sourceType;
		this.fileCode = fileCode;
		this.lineCount = lineCount;		
	}
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("user: " + user + ", ");
		build.append("fileCode: " + fileCode);
		build.append("sourceType: " + sourceType + ", ");
		build.append("filename: " + filename + ", ");
		return build.toString();
	}
	public String getUser() {
		return user;
	}

	public String getFilename() {
		return filename;
	}

	public String getSourceType() {
		return sourceType;
	}

    public String getFileCode() {
    	return fileCode;
    }
    public int getLineCount() {
    	return lineCount.intValue();
    }
    public void setLineCount(int count) {
    	lineCount = count;
    }

	public void setUser(String user) {
		this.user = user;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public void setLineCount(Integer lineCount) {
		this.lineCount = lineCount;
	}

}
