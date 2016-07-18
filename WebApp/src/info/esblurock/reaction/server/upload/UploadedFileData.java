package info.esblurock.reaction.server.upload;

import java.util.ArrayList;
import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class UploadedFileData extends DatabaseObject {

	@Persistent
    String user;
    
    @Persistent
    String fileLinesEntityName;
    
    @Persistent
	private String type;
	
    @Persistent
	private String dataKey;
	
    @Persistent
	private String sourceKey;
	
	@Persistent
	private String source;
	
	@Persistent
	private String filename;
	
	@Persistent(serialized="true")
	private ArrayList<Key> lineKeys;

    @Persistent
	private Date entryDate;
	
    @Persistent
	private Date sourceDate;

	public UploadedFileData() {
	}

	public UploadedFileData(String user,
			String fileLinesEntityName,
			String dataKey, String sourceKey,
			Date entryDate, Date sourceDate,
			String type, String source, String filename, ArrayList<Key> linekeys) {
		super();
		this.user = user;
		this.fileLinesEntityName = fileLinesEntityName;
		this.dataKey = dataKey;
		this.sourceKey = sourceKey;
		this.type = type;
		this.source = source;
		this.filename = filename;
		this.lineKeys = linekeys;
		this.sourceDate = sourceDate;
		this.entryDate = entryDate;
		this.lineKeys = linekeys;
	}

	public String getUser() {
		return user;
	}
	public String getFileLinesEntityName() {
		return fileLinesEntityName;
	}
	public String getDataKey() {
		return dataKey;
	}
	
	public String getSourceKey() {
		return sourceKey;
	}
	
	public String getType() {
		return type;
	}

	public String getSource() {
		return source;
	}

	public String getFilename() {
		return filename;
	}

	public ArrayList<Key> getLineKeys() {
		return lineKeys;
	}
	public Date getSourceDate() {
		return sourceDate;
	}
	public Date getEntryDate() {
		return entryDate;
	}
}
