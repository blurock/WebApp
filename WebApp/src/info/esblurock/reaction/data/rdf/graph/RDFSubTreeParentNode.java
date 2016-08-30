package info.esblurock.reaction.data.rdf.graph;

public class RDFSubTreeParentNode extends RDFGraphNode {
	
	String object;

	public RDFSubTreeParentNode(String object) {
		super(false,false,false);
		this.object = object;
	}
	public String getObject() {
		return object;
	}
	
	public String toString() {
		return object;
	}
	/**
	 * 
	 */
	public boolean matchesNode(RDFGraphNode obj) {
		boolean ans = false;
		if(super.equals(obj)) {
			RDFSubTreeParentNode stringobj = (RDFSubTreeParentNode) obj;
			ans = object.matches(stringobj.getObject());
		}
		return ans;
	}

}
