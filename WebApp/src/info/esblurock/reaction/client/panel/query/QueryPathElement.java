package info.esblurock.reaction.client.panel.query;

import java.io.Serializable;

public class QueryPathElement implements Serializable{
	private static final long serialVersionUID = 1L;
	public static String SUBJECT = "Subject";
	public static String PREDICATE = "Predicate";
	public static String OBJECTSTRING = "ObjectKey";
	public static String OBJECTOBJECT = "Object";
	public static String SEARCHSTRING = "Search";
	
	String keyword;
	String type;
	
	public QueryPathElement(String keyword, String type) {
		this.keyword = keyword;
		this.type = type;
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append(type);
		build.append(":");
		build.append(keyword);
		return build.toString();
	}
}
