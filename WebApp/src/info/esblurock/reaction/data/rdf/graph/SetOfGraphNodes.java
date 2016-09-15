package info.esblurock.reaction.data.rdf.graph;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class SetOfGraphNodes extends HashSet<RDFTreeNode> implements Serializable {
	private static final long serialVersionUID = 1L;

	RDFTreeNode findMatchingNode(RDFGraphNode node) {
		RDFTreeNode match = null;
		Iterator<RDFTreeNode> iter = this.iterator();
		while(match == null && iter.hasNext()) {
			RDFTreeNode sub = iter.next();
			if(sub.matchesNode(node)) {
				match = sub;
				add(match);
			}
		}
		if(match == null) {
			match = new RDFTreeNode(node);
			add(match);
		}
		return match;
	}
}
