package info.esblurock.reaction.data.upload;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

/**
 * @author edwardblurock
 *
 */
@PersistenceCapable
public class InputTextSource extends DatabaseObject {

	@Persistent
	String textname;

	@Persistent
	String ID;

	@Persistent
	String sourceType;

	@Persistent
	String textType;

	public InputTextSource() {
		
	}

	public InputTextSource(String textname, String iD, String sourceType,
			String textType) {
		super();
		this.textname = textname;
		ID = iD;
		this.sourceType = sourceType;
		this.textType = textType;
	}

	public String getTextname() {
		return textname;
	}

	public String getID() {
		return ID;
	}

	public String getSourceType() {
		return sourceType;
	}

	public String getTextType() {
		return textType;
	}

}
