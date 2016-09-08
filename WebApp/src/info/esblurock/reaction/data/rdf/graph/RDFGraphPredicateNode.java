package info.esblurock.reaction.data.rdf.graph;

public class RDFGraphPredicateNode extends RDFGraphNode {
	String predicate;
	
	public RDFGraphPredicateNode() {
		predicate = null;
	}
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
		boolean ans = false;
		if(super.matchesNode(obj)) {
			RDFGraphPredicateNode node = (RDFGraphPredicateNode) obj;
			ans = node.getPredicate().compareTo(predicate) == 0;
		}
		return ans;
	}
}
