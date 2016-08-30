package info.esblurock.reaction.data.rdf.graph;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.rdf.SetOfKeywordRDF;
import info.esblurock.reaction.server.ReactionSearchServiceImpl;

public class TreeNodeFactory {
	
	ReactionSearchServiceImpl searchservice = new ReactionSearchServiceImpl();
	
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
	void addRDF(RDFTreeNode treenode, KeywordRDF rdf) {
		System.out.println("addRDF: " + rdf.toString());
		String rdfsubject = rdf.getSubject();
		String rdfpredicate = rdf.getPredicate();
		String rdfobject = rdf.getObject();
		
		int pos = rdfpredicate.indexOf("#");
		String typeS = rdfpredicate.substring(pos+1);
		String predicateS = rdfpredicate.substring(0, pos);

		boolean objectB = typeS.endsWith("Object");
		DatabaseObject object = null;
		if(objectB) {
			try {
				object = searchservice.getObjectFromKey(predicateS, rdfobject);
			} catch (Exception e) {
			}
		}
		treenode.addRDF(rdfsubject, predicateS, rdfobject, objectB, object);
	}
}
