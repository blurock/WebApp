package info.esblurock.reaction.data.upload;

import java.util.ArrayList;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.description.DescriptionDataData;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class TextSetUploadData extends DatabaseObject {

	private static final long serialVersionUID = 1L;

	@Persistent(dependent = "true")
    DescriptionDataData description;

    @Persistent
    ArrayList<InputTextSource> inputTextSources;
    
    public TextSetUploadData() {
    	
    }
    public TextSetUploadData(DescriptionDataData description) {
    	this.description = description;
    	inputTextSources = new ArrayList<InputTextSource>();
    }
    
    public void addInputTextSource(InputTextSource source) {
    	inputTextSources.add(source);
    }
	public DescriptionDataData getDescription() {
		return description;
	}
	public ArrayList<InputTextSource> getInputTextSources() {
		return inputTextSources;
	}
    
    
}
