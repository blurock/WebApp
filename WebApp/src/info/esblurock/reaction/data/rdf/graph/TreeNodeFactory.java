package info.esblurock.reaction.data.rdf.graph;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.rdf.SetOfKeywordRDF;
import info.esblurock.reaction.server.ReactionSearchServiceImpl;

public class TreeNodeFactory {
	
	public RDFTreeNode  addAllRDF(String nodeS, SetOfKeywordRDF set) {
		RDFSubTreeParentNode node = new RDFSubTreeParentNode(nodeS);
		RDFTreeNode treenode = new RDFTreeNode(node);
		addAllRDF(treenode,set);
		return treenode;
	}
	
	public void addAllRDF(RDFTreeNode treenode, SetOfKeywordRDF set) {
		for(KeywordRDF rdf : set) {
			addRDF(treenode,rdf);
		}
	}
/** This adds the RDF to the tree
 * 
 * @param treenode
 * @param rdf
 */
	public boolean addRDF(RDFTreeNode treenode, KeywordRDF rdf) {
		String rdfsubject = rdf.getSubject();
		String rdfpredicate = rdf.getPredicate();
		String rdfobject = rdf.getObject();
		
		int pos = rdfpredicate.indexOf("#");
		String typeS = rdfpredicate.substring(pos+1);
		String predicateS = rdfpredicate.substring(0, pos);
		
		DatabaseObject object = null;
		boolean objectB = typeS.endsWith("Object");
		if(!objectB) {
			treenode.addRDF(rdfsubject, predicateS, rdfobject, objectB, object);
			typeS = null;
		}
		return objectB;
	}
}
