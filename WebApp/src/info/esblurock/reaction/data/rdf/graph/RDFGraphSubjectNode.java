package info.esblurock.reaction.data.rdf.graph;

public class RDFGraphSubjectNode extends RDFGraphNode {

	String subject;

	public RDFGraphSubjectNode() {
		subject = null;
	}

	public RDFGraphSubjectNode(String subject) {
		super(true, false, false);
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}
	public String getFormattedSubject() {
		return formatAnswer(subject);
	}

	public String toString() {
		return subject;
	}

	public boolean matchesNode(RDFGraphNode obj) {
		boolean ans = false;
		String nodeS = null;
		if (super.matchesNode(obj)) {
			RDFGraphSubjectNode node = (RDFGraphSubjectNode) obj;
			nodeS = node.getSubject();
			if (nodeS == null) {
				if (subject == null) {
					ans = true;
				}
			} else if (subject != null) {
				if (nodeS.compareTo(subject) == 0) {
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
			System.out.println("RDFGraphSubjectNode: '" + objS + "'  '" + thisS + "'");
			ans = thisS.compareTo(objS);
			if (ans == 0) {
				RDFGraphSubjectNode obj = (RDFGraphSubjectNode) o;
				if (subject == null) {
					if (obj.getSubject() == null) {
						ans = 0;
					} else {
						ans = 1;
					}
				} else if (obj.getSubject() == null) {
					ans = -1;
				} else {
					ans = obj.getSubject().compareTo(subject);
				}
			}
		}
		return ans;
	}

}
