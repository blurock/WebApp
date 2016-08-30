package info.esblurock.reaction.data.rdf.graph;

public class RDFGraphPredicateNode extends RDFGraphNode {
	String predicate;
	
	public RDFGraphPredicateNode(String predicate) {
		super(false,true,false);
		this.predicate = predicate;
	}

	public String getPredicate() {
		return predicate;
	}
	
	public String toString() {
		return predicate;
	}
	public boolean matchesNode(RDFGraphNode obj) {
		RDFGraphPredicateNode node = (RDFGraphPredicateNode) obj;
		return node.getPredicate().matches(predicate);
	}
}
