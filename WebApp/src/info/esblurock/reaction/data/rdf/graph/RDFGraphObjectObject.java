package info.esblurock.reaction.data.rdf.graph;

import info.esblurock.reaction.data.DatabaseObject;

public class RDFGraphObjectObject extends RDFGraphNode {
	DatabaseObject object;
	
	public RDFGraphObjectObject() {
		object = null;
	}
	public RDFGraphObjectObject(DatabaseObject object) {
		super(false,false,true);
		this.object = object;
	}

	public DatabaseObject getObject() {
		return object;
	}
	
	public String toString() {
		return object.toString();
	}
	public boolean matchesNode(RDFGraphNode obj) {
		return super.equals(obj);
	}

}
