package info.esblurock.reaction.client.panel.transaction;

import java.io.Serializable;
import java.util.Date;

public class SourceTransaction implements Serializable, Comparable<SourceTransaction> {
	private static final long serialVersionUID = 1L;

	private String user;
	private String keyword;
	private String dataKey;
	private String sourceKey;
	private String type;
	private Date dateEntered;
	private Date sourceDate;

	public SourceTransaction() {
		
	}
	public SourceTransaction(String user, String keyword, String datakey, String sourceKey, String type, 
			Date dateEntered, Date sourceDate) {
		super();
		this.user = user;
		this.keyword = keyword;
		this.sourceKey = sourceKey;
		this.dataKey = datakey;
		this.type = type;
		this.dateEntered = dateEntered;
		this.sourceDate = sourceDate;		
	}
	
	public String getUser() {
		return user;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String key) {
		this.keyword = key;
	}
	public String getSourceKey() {
		return sourceKey;
	}
	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDateEntered() {
		return dateEntered;
	}
	public void setDateEntered(Date dateEntered) {
		this.dateEntered = dateEntered;
	}
	public Date getSourceDate() {
		return sourceDate;
	}
	public void setSourceDate(Date sourceDate) {
		this.sourceDate = sourceDate;
	}

	public String getDataKey() {
		return dataKey;
	}
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}
	@Override
	public int compareTo(SourceTransaction o) {
		int compare = this.type.compareTo(o.getType());
		if(compare == 0) {
			compare = this.dateEntered.compareTo(o.getDateEntered());
			if(compare == 0) {
				compare = this.sourceDate.compareTo(o.getSourceDate());
				if(compare == 0) {
					compare = this.keyword.compareTo(o.getKeyword());
				}
			}
		}
		return compare;
	}
	
	
}
