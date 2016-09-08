package info.esblurock.reaction.data.rdf.graph;

public class RDFGraphStringObject extends RDFGraphNode {
	
	String object;

	public RDFGraphStringObject() {
		object = null;
	}
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
		if(super.matchesNode(obj)) {
			RDFGraphStringObject stringobj = (RDFGraphStringObject) obj;
			String objS = stringobj.getObject();
			if(objS != null) {
				if(object != null) {
					ans = object.compareTo(objS) == 0;
				} else {
					ans = false;
				}
			} else {
				if(object != null) {
					ans = false;
				} else {
					ans = true;
				}
			}
		}
		return ans;
	}
}
