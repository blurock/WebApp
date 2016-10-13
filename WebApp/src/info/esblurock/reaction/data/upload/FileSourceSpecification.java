package info.esblurock.reaction.data.upload;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Text;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class FileSourceSpecification extends DatabaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Persistent
	protected Text textBody;
	@Persistent
	protected String textName;
	@Persistent
	protected String sourceType;
    @Persistent
    protected String fileCode;
    
	public FileSourceSpecification() {
		super();
	}
	public Text getTextBody() {
		return textBody;
	}
	public String getTextName() {
		return textName;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setTextBody(Text textBody) {
		this.textBody = textBody;
	}
	public void setTextName(String textName) {
		this.textName = textName;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getFileCode() {
		return fileCode;
	}
	

}
