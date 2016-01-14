package info.esblurock.reaction.data.description;

import info.esblurock.reaction.client.data.DatabaseObject;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class DescriptionDataData extends DatabaseObject {

	@Persistent
	String parentKey;
	
	@Persistent
	String keyword;

	@Persistent
	String onlinedescription;

	@Persistent
	String fulldescription;

	@Persistent
	String sourcekey;
	
	@Persistent
	Date sourceDate;

	@Persistent
	String inputkey;

	public DescriptionDataData() {
	}

	public DescriptionDataData(
			String keyword, String 
			onlinedescription,
			String fulldescription,
			Date sourcedate,
			String sourcekey,
			String inputkey) {
		this.keyword = keyword;
		this.onlinedescription = onlinedescription;
		this.fulldescription = fulldescription;
		this.sourceDate = sourcedate;
		this.sourcekey = sourcekey;
		this.inputkey = inputkey;
	}
/*
	public DescriptionDataData(String key, DescriptionData data) {
		SetOfAttributeValuePairs set = data.getPropertySet(key);
		for (AttributeValuePair pair : set) {
			if (pair.getAttributeValue().equals(DescriptionData.keyWordKey)) {
				this.keyword = pair.getPropertyValue();
			} else if (pair.getAttributeValue().equals(
					DescriptionData.oneLineDescriptionKey)) {
				this.onlinedescription = pair.getPropertyValue();
			} else if (pair.getAttributeValue().equals(
					DescriptionData.fullDescriptionKey)) {
				this.fulldescription = pair.getPropertyValue();
			}
		}
	}
*/
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
	
}
