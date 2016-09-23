package info.esblurock.reaction.data.rdf.graph;

import info.esblurock.reaction.client.panel.data.DataPresentation;
import info.esblurock.reaction.data.DatabaseObject;

public class RDFGraphObjectObject extends RDFGraphNode {
	DatabaseObject object;
	
	public RDFGraphObjectObject() {
		object = null;
	}
	public RDFGraphObjectObject(DatabaseObject object) {
		super(false,false,true);
		this.object = object;
	}

	public DatabaseObject getObject() {
		return object;
	}
	
	public String toString() {
		String ans = "null";
		if(object != null) {
		String classnameS = object.getClass().getSimpleName();
		try {
			DataPresentation presentation = DataPresentation.valueOf(classnameS);
			ans = presentation.asOnLine(object);
		} catch(Exception ex) {
			System.out.println("DataPresentation object not found: " + classnameS);
		}
		}
		return ans;
	}
	public boolean matchesNode(RDFGraphNode obj) {
		return super.equals(obj);
	}
	@Override
	public int compareTo(RDFGraphNode o) {
		System.out.println("RDFGraphObjectObject: compareTo");
		int ans = 0;
		if (o == null) {
			ans = -1;
		} else {
			String objS = o.toString();
			String thisS = toString();
			System.out.println("RDFGraphObjectObject: '" + objS + "'  '" + thisS + "'");
			ans = thisS.compareTo(objS);
			if (ans == 0) {
				RDFGraphObjectObject obj = (RDFGraphObjectObject) o;
				if (object == null) {
					if (obj.getObject() == null) {
						ans = 0;
					} else {
						ans = 1;
					}
				} else if (obj.getObject() == null) {
					ans = -1;
				} else {
					String objobjS = obj.getObject().getClass().getName();
					String thisobjS = getObject().getClass().getName();
					ans = thisobjS.compareTo(objobjS);
				}
			}
		}
		return ans;
	}

}
