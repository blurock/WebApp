package info.esblurock.reaction.data.upload;

import java.util.ArrayList;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.description.DescriptionDataData;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
@PersistenceCapable
public class TextSetUploadData extends DatabaseObject {

    @Persistent(dependent = "true")
    DescriptionDataData description;

    @Persistent(serialized="true")
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
