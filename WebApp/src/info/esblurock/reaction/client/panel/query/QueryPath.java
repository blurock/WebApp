package info.esblurock.reaction.client.panel.query;

import java.io.Serializable;
import java.util.ArrayList;

import info.esblurock.reaction.client.panel.inputs.QueryPathElement;

public class QueryPath  implements Serializable{
	private static final long serialVersionUID = 1L;
	ArrayList<QueryPathElement> path;
	String nextKeyword;
	
	public QueryPath(String keyword) {
		path = new ArrayList<QueryPathElement>();
		nextKeyword = keyword;
	}
	public QueryPath addToNewPath(String predicate, String elementtype) {
		QueryPathElement element = new QueryPathElement(nextKeyword,predicate,elementtype);
		QueryPath newpath = new QueryPath(this);
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
	
}
