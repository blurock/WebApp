package info.esblurock.reaction.data.rdf.graph;

public class RDFSubTreeParentNode extends RDFGraphNode {
	
	String object;

	public RDFSubTreeParentNode() {
		super(false,false,false);
		object = null;
	}
	public RDFSubTreeParentNode(String object) {
		
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
