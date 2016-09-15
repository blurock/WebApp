package info.esblurock.reaction.data.rdf.graph;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.server.ReactionSearchServiceImpl;

public class TreeNodeFactoryWithObjectNode extends TreeNodeFactory {

	ReactionSearchServiceImpl searchservice = new ReactionSearchServiceImpl();

	public boolean addRDF(RDFTreeNode treenode, KeywordRDF rdf) {
		boolean objectB = super.addRDF(treenode, rdf);
		if (objectB) {
			String rdfsubject = rdf.getSubject();
			String rdfpredicate = rdf.getPredicate();
			String rdfobject = rdf.getObject();

			int pos = rdfpredicate.indexOf("#");
			String typeS = rdfpredicate.substring(pos + 1);
			String predicateS = rdfpredicate.substring(0, pos);
			DatabaseObject object = null;
			try {
				pos = predicateS.lastIndexOf('.');
				object = searchservice.getObjectFromKey(predicateS, rdfobject);
				String shortPredicateS = predicateS.substring(pos + 1);
				treenode.addRDF(rdfsubject, shortPredicateS, rdfobject, objectB, object);
			} catch (Exception e) {
				System.out.println("TreeNodeFactoryWithObjectNode: Exception\n" + e.toString());
			}
		}
		return objectB;
	}

}
