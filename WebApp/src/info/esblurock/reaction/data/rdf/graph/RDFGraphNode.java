package info.esblurock.reaction.data.rdf.graph;

import java.io.Serializable;

public class RDFGraphNode implements Serializable {

	private static final long serialVersionUID = 1L;
	
	boolean subjectNode;
	boolean predicateNode;
	boolean objectNode;
	
	
	public RDFGraphNode(boolean subjectNode, boolean predicateNode, boolean objectNode) {
		super();
		this.subjectNode = subjectNode;
		this.predicateNode = predicateNode;
		this.objectNode = objectNode;
	}
	public boolean matchesNode(RDFGraphNode obj) {
		boolean ans = true;
		/*
		RDFGraphNode graphnode = (RDFGraphNode) obj;
		if(!(subjectNode && graphnode.isSubjectNode())) {
			if(!(predicateNode && graphnode.isPredicateNode())) {
				if(!(objectNode && graphnode.isObjectNode())) {
					ans = false;
				}
			}
		}
		*/
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
