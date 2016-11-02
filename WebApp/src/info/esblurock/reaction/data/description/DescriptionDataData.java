package info.esblurock.reaction.data.description;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unindexed;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class DescriptionDataData extends DatabaseObject {

	@Persistent
	String parentKey;
	
	@Persistent
	String keyword;

	@Persistent
	@Unindexed
	String onlinedescription;

	@Persistent
	@Unindexed
	String fulldescription;

	@Persistent
	String sourcekey;
	
	@Persistent
	Date sourceDate;

	@Persistent
	String inputkey;

	@Persistent
	String dataType;
	
	@Persistent
	@Unindexed
	HashSet<String> keywords;

	@Persistent
	@Unindexed
	ArrayList<String> dataSetPath;

	public DescriptionDataData() {
	}

	public DescriptionDataData(
			String keyword, String 
			onlinedescription,
			String fulldescription,
			Date sourcedate,
			String sourcekey,
			String inputkey,
			String datatype,
			HashSet<String> keywords) {
		this.keyword = keyword;
		this.onlinedescription = onlinedescription;
		this.fulldescription = fulldescription;
		this.sourceDate = sourcedate;
		this.sourcekey = sourcekey;
		this.inputkey = inputkey;
		this.dataType = datatype;
		this.keywords = keywords;
	}
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getOnlinedescription() {
		return onlinedescription;
	}

	public void setOnlinedescription(String onlinedescription) {
		this.onlinedescription = onlinedescription;
	}

	public String getFulldescription() {
		return fulldescription;
	}

	public void setFulldescription(String fulldescription) {
		this.fulldescription = fulldescription;
	}

	public String getParentKey() {
		return parentKey;
	}
	public void setParentKey(String parentkey) {
		this.parentKey = parentkey;
	}

	public String getSourcekey() {
		return sourcekey;
	}

	public String getInputkey() {
		return inputkey;
	}
	public Date getSourceDate() {
		return sourceDate;
	}
	public String getDataType() {
		return dataType;
	}
	public HashSet<String> getKeywords() {
		return keywords;
	}
}
