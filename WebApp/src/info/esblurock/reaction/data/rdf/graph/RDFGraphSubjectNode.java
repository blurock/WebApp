package info.esblurock.reaction.data.rdf.graph;

public class RDFGraphSubjectNode extends RDFGraphNode {

	String subject;
	
	public RDFGraphSubjectNode() {
		subject = null;
	}
	public RDFGraphSubjectNode(String subject) {
		super(true,false,false);
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}
	
	public String toString() {
		return subject;
	}
	public boolean matchesNode(RDFGraphNode obj) {
		boolean ans = false;
		String nodeS = null;
		if(super.matchesNode(obj)) {
			RDFGraphSubjectNode node = (RDFGraphSubjectNode) obj;
			nodeS = node.getSubject();
			if(nodeS == null) {
				if(subject == null) {
					ans = true;
				}
			} else if(subject != null) {
				if(nodeS.compareTo(subject) == 0) {
					ans = true;
				}
			}
		}
		System.out.println("Subject: " + subject + "," + nodeS + "=" + ans);
		return ans;
	}
	
}
