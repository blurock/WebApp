package info.esblurock.reaction.client.panel.query;

import java.io.Serializable;
import java.util.ArrayList;

public class QueryPath  implements Serializable {
	private static final long serialVersionUID = 1L;
	ArrayList<QueryPathElement> path;
	
	public QueryPath(String type, String keyword) {
		path = new ArrayList<QueryPathElement>();
	}
	public QueryPath(QueryPath original,String type, String keyword) {
		path = new ArrayList<QueryPathElement>();
		for(QueryPathElement element : original.getPath()) {
			path.add(element);
		}
		QueryPathElement element = new QueryPathElement(keyword,type);
		path.add(element);
	}
	public QueryPath addToNewPath(String type, String keyword) {
		QueryPath newpath = new QueryPath(this,type,keyword);
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
		return build.toString();
	}
	protected void add(QueryPathElement element) {
		path.add(element);
	}
	protected ArrayList<QueryPathElement> getPath() {
		return path;
	}
}
