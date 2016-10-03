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
	
	public String getFormattedObject() {
		return formatAnswer(object);
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
	@Override
	public int compareTo(RDFGraphNode o) {
		int ans = 0;
		if (o == null) {
			ans = -1;
		} else {
			String objS = o.getClass().getName();
			String thisS = this.getClass().getName();
			System.out.println("RDFGraphStringObject: '" + objS + "'  '" + thisS + "'");
			ans = thisS.compareTo(objS);
			if (ans == 0) {
				RDFGraphStringObject obj = (RDFGraphStringObject) o;
				if (object == null) {
					if (obj.getObject() == null) {
						ans = 0;
					} else {
						ans = 1;
					}
				} else if (obj.getObject() == null) {
					ans = -1;
				} else {
					ans = obj.getObject().compareTo(object);
				}
			}
		}
		return ans;
	}

}
