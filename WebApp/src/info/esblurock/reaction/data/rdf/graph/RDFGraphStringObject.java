package info.esblurock.reaction.data.rdf.graph;

public class RDFGraphStringObject extends RDFGraphNode {
	
	String object;

	public RDFGraphStringObject(String object) {
		super(false,false,true);
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
			RDFGraphStringObject stringobj = (RDFGraphStringObject) obj;
			ans = object.matches(stringobj.getObject());
		}
		return ans;
	}
}
