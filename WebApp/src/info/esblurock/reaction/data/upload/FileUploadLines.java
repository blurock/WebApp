package info.esblurock.reaction.data.upload;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class FileUploadLines {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String key;
	
    @Persistent
    int count;
    
    @Persistent
    String line;

	public FileUploadLines() {
	}
	public FileUploadLines(int count, String line) {
		super();
		this.count = count;
		this.line = line;
	}

	public String getKey() {
		return key;
	}

	public String getLine() {
		return line;
	}
    


}
