package info.esblurock.reaction.client.panel.inputs;

import java.io.Serializable;

public class QueryPathElement implements Serializable{
	private static final long serialVersionUID = 1L;
	String keyword;
	String predicate;
	String elementType;
	
	public QueryPathElement(String keyword, String predicate, String elementtype) {
		this.keyword = keyword;
		this.predicate = predicate;
		this.elementType = elementtype;
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append(keyword);
		build.append(":");
		build.append(predicate);
		
		return build.toString();
	}
}
