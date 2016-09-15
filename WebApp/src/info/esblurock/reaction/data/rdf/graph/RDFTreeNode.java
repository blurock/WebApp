package info.esblurock.reaction.data.rdf.graph;

import java.io.Serializable;

import info.esblurock.reaction.data.DatabaseObject;

public class RDFTreeNode implements Serializable, Comparable<RDFTreeNode> {
	private static final long serialVersionUID = 1L;
	
	RDFGraphNode parent;
	SetOfGraphNodes children;
	
	public RDFTreeNode() {
	}
	
	/** Constructor 
	 * 
	 * @param node
	 */
	public RDFTreeNode(RDFGraphNode node) {
		super();
		this.parent = node;
		children = new SetOfGraphNodes();
	}
	/**
	 * Add node to children
	 * @param node Add a node to the children
	 */
	public void addNode(RDFTreeNode treenode) {
		children.add(treenode);
	}
/** Get the parent node
 * 
 * @return The parent node
 */
	public RDFGraphNode getNode() {
		return parent;
	}
/** Add the RDF to the tree
 * 
 * @param rdf The RDF information
 */
	public void addRDF(String rdfsubject, String rdfpredicate, String rdfobject, 
			boolean objectB, DatabaseObject object) {
		RDFGraphSubjectNode subjectparent = new RDFGraphSubjectNode(rdfsubject);
		RDFTreeNode subjectsub = children.findMatchingNode(subjectparent);
		
		SetOfGraphNodes predicateNodes = subjectsub.getChildren();
		RDFGraphPredicateNode predicatenode = new RDFGraphPredicateNode(rdfpredicate);
		RDFTreeNode predicatesub = predicateNodes.findMatchingNode(predicatenode);
		SetOfGraphNodes objectNodes = predicatesub.getChildren();
		if(!objectB) {
			RDFGraphStringObject objectnode = new RDFGraphStringObject(rdfobject);
			objectNodes.findMatchingNode(objectnode);
		} else {
			RDFGraphObjectObject objectnode = new RDFGraphObjectObject(object);
			RDFTreeNode objectsub = new RDFTreeNode(objectnode);
			objectNodes.add(objectsub);
		}
	}
/** 
 * 
 * @param treenode
 * @return
 */
	boolean matchesNode(RDFGraphNode treenode) {
		return treenode.matchesNode(parent);
	}
/** Set the parent node
 * 
 * @param node
 */
	public void setNode(RDFGraphNode node) {
		this.parent = node;
	}

public RDFGraphNode getParent() {
	return parent;
}

/** The sub-nodes of the parent.
 * 
 * @return
 */
	public SetOfGraphNodes getChildren() {
		return children;
	}
/**
 * Convert object to a String
 */
	public String toString() {
		String prefix ="";
		return toString(prefix);
	}
/**
 * Convert object to String, with prefix used to define levels.
 * 
 * @param prefix
 * @return
 */
	public String toString(String prefix) {
		String nextlevel = prefix + "-->";
		StringBuilder build = new StringBuilder();
		build.append(prefix);
		build.append(parent.toString());
		build.append("\n");
		for(RDFTreeNode node : children) {
			build.append(node.toString(nextlevel));
		}
		return build.toString();
	}

@Override
public int compareTo(RDFTreeNode o) {
	int ans = 0;
	System.out.println("compareTo: " + this.getClass().getName());
	System.out.println("compareTo: " + o.getParent() + " -- " + getParent());
	if( (o.getParent() == null) || (getParent() == null)) { 
		ans = 0;
	} else {
		System.out.println("compare");
		ans = parent.compareTo(o.getParent());
		System.out.println("compare: " + ans);
	}
	return ans;
}
}
