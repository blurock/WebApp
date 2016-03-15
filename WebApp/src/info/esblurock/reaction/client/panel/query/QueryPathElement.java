package info.esblurock.reaction.client.panel.query;

import java.io.Serializable;

public class QueryPathElement implements Serializable{
	private static final long serialVersionUID = 1L;
	String keyword;
	String predicate;
	
	public QueryPathElement(String keyword, String predicate) {
		this.keyword = keyword;
		this.predicate = predicate;
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append(keyword);
		build.append(":");
		build.append(predicate);
		
		return build.toString();
	}
}
