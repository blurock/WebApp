package info.esblurock.reaction.data.upload;

import info.esblurock.reaction.client.data.DatabaseObject;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

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
}
