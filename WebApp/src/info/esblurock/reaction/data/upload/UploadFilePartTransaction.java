package info.esblurock.reaction.data.upload;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class UploadFilePartTransaction  extends DatabaseObject {

    @Persistent
    String filecode;
    
    @Persistent
    Integer part;
    
    @Persistent(serialized="true")
    ArrayList<String> setOfLinesKeys;

	public UploadFilePartTransaction() {
	}

	public UploadFilePartTransaction(String filecode, Integer part, ArrayList<String> setOfLinesKeys) {
		this.filecode = filecode;
		this.setOfLinesKeys = setOfLinesKeys;
		this.part = part;
	}

	public String getFilecode() {
		return filecode;
	}

	public ArrayList<String> getSetOfLinesKeys() {
		return setOfLinesKeys;
	}
	public Integer getPart() {
		return part;
	}
}
