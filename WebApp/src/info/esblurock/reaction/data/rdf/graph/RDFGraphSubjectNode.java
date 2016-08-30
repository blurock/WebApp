package info.esblurock.reaction.data.rdf.graph;

public class RDFGraphSubjectNode extends RDFGraphNode {

	String subject;
	
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
		RDFGraphSubjectNode node = (RDFGraphSubjectNode) obj;
		return node.getSubject().matches(subject);
	}
	
}
