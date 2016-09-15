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
	@Override
	public int compareTo(RDFGraphNode o) {
		int ans = 0;
		if (o == null) {
			ans = -1;
		} else {
			String objS = o.getClass().getName();
			String thisS = this.getClass().getName();
			System.out.println("RDFGraphPredicateNode: '" + objS + "'  '" + thisS + "'");
			ans = thisS.compareTo(objS);
			if (ans == 0) {
				RDFGraphPredicateNode obj = (RDFGraphPredicateNode) o;
				if (predicate == null) {
					if (obj.getPredicate() == null) {
						ans = 0;
					} else {
						ans = 1;
					}
				} else if (obj.getPredicate() == null) {
					ans = -1;
				} else {
					ans = obj.getPredicate().compareTo(predicate);
				}
			}
		}
		return ans;
	}

}
