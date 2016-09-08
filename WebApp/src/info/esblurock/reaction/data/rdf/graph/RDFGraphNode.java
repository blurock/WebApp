package info.esblurock.reaction.data.rdf.graph;

import java.io.Serializable;

public class RDFGraphNode implements Serializable {

	private static final long serialVersionUID = 1L;
	
	boolean subjectNode;
	boolean predicateNode;
	boolean objectNode;
	
	public RDFGraphNode() {
		super();
		this.subjectNode = false;
		this.predicateNode = false;
		this.objectNode = false;
	}
	
	public RDFGraphNode(boolean subjectNode, boolean predicateNode, boolean objectNode) {
		super();
		this.subjectNode = subjectNode;
		this.predicateNode = predicateNode;
		this.objectNode = objectNode;
	}
	public boolean matchesNode(RDFGraphNode obj) {
		boolean ans = true;
		if(subjectNode) {
			ans = obj.isSubjectNode();
		} else if(objectNode) {
			ans = obj.isObjectNode();
		} else {
			ans = obj.isPredicateNode();
		}
		return ans;
		
	}
	public boolean isSubjectNode() {
		return subjectNode;
	}
	public boolean isPredicateNode() {
		return predicateNode;
	}
	public boolean isObjectNode() {
		return objectNode;
	}
	
}
