package info.esblurock.reaction.client.panel.query;

import java.io.Serializable;
import java.util.ArrayList;

public class QueryPath  implements Serializable{
	private static final long serialVersionUID = 1L;
	ArrayList<QueryPathElement> path;
	String nextKeyword;
	boolean objectAsKey;
	
	public QueryPath(String keyword) {
		path = new ArrayList<QueryPathElement>();
		nextKeyword = keyword;
		objectAsKey = false;
	}
	public QueryPath addToNewPath(String predicate, String object, boolean objectaskey) {
		QueryPathElement element = new QueryPathElement(nextKeyword,predicate);
		QueryPath newpath = new QueryPath(this);
		newpath.setNextKeyword(object,objectaskey);
		newpath.add(element);
		return newpath;
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		boolean nexttime = false;
		for(QueryPathElement element : path) {
			if(nexttime) {
				build.append(" -> ");
			} else {
				nexttime = true;
			}
			build.append(element.toString());
		}
		build.append(" -> ");
		if(objectAsKey)
			build.append("(key)");
		else
			build.append(nextKeyword);
		return build.toString();
	}
	protected QueryPath(QueryPath original) {
		path = new ArrayList<QueryPathElement>();
		for(QueryPathElement element : original.getPath()) {
			path.add(element);
		}
	}
	protected void add(QueryPathElement element) {
		path.add(element);
	}
	protected ArrayList<QueryPathElement> getPath() {
		return path;
	}
	public String getNextKeyword() {
		return nextKeyword;
	}
	public void setNextKeyword(String nextKeyword, boolean objectaskey) {
		this.nextKeyword = nextKeyword;
		this.objectAsKey = objectaskey;
	}
	
}
