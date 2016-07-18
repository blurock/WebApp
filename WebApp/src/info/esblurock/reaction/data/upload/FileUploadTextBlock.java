package info.esblurock.reaction.data.upload;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import com.google.appengine.api.datastore.Text;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class FileUploadTextBlock  extends DatabaseObject {
   @Persistent
    Integer beginLineCount;

    @Persistent
    Integer endLineCount;

    @Persistent
    String fileCode;
    
    @Persistent
    Text textBlock;

	public FileUploadTextBlock() {
		super();
	}

	public FileUploadTextBlock(Integer beginLineCount, Integer endLineCount, String fileCode, Text textBlock) {
		super();
		this.beginLineCount = beginLineCount;
		this.endLineCount = endLineCount;
		this.fileCode = fileCode;
		this.textBlock = textBlock;
	}

	public Integer getBeginLineCount() {
		return beginLineCount;
	}

	public Integer getEndLineCount() {
		return endLineCount;
	}

	public String getFileCode() {
		return fileCode;
	}

	public Text getTextBlock() {
		return textBlock;
	}    

}
