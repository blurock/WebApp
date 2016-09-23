package info.esblurock.reaction.data.description;

import java.util.HashSet;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unindexed;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class DataSetReference  extends DatabaseObject {

	@Persistent
	String DatasetKeyword;

	@Persistent
	String DOI;
	
	@Persistent
	@Unindexed
	String Title;
	
	@Persistent
	@Unindexed
	String ReferenceString;
		
	@Persistent
	@Unindexed
	HashSet<String> authors;

	@Persistent
	@Unindexed
	HashSet<String> authorLastNames;

	public DataSetReference() {
		
	}
	public DataSetReference(String datasetKeyword, String dOI, String title, String referenceString, 
			HashSet<String> authors, HashSet<String> lastnames) {
		super();
		this.DatasetKeyword = datasetKeyword;
		this.DOI = dOI;
		this.Title = title;
		this.ReferenceString = referenceString;
		this.authors = authors;
		this.authorLastNames = lastnames;
	}
	public String getDatasetKeyword() {
		return DatasetKeyword;
	}
	public String getDOI() {
		return DOI;
	}
	public String getReferenceTitle() {
		return Title;
	}
	public String getReferenceString() {
		return ReferenceString;
	}
	public HashSet<String> getAuthors() {
		return authors;
	}
	public HashSet<String> getAuthorLastNames() {
		return authorLastNames;
	}

	
	
}
