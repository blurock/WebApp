package info.esblurock.reaction.rdf;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.rdf.SetOfKeywordRDF;
import info.esblurock.reaction.data.rdf.graph.RDFTreeNode;
import info.esblurock.reaction.data.rdf.graph.TreeNodeFactory;

public class AddRDFToTree {

	@Test
	public void test() {		
		
		KeywordRDF rdf1a  = new KeywordRDF("sub1","pred1#String","obj1","Admin","source");
		KeywordRDF rdf2a  = new KeywordRDF("sub1","pred1#String","obj1","Admin","source");
		KeywordRDF rdf3a  = new KeywordRDF("sub1","pred1#String","obj1","Admin","source");
		KeywordRDF rdf4a  = new KeywordRDF("sub1","pred1#String","obj1","Admin","source");
		
		KeywordRDF rdf11 = new KeywordRDF("sub1","pred1#String","obj1","Admin","source");
		KeywordRDF rdf12 = new KeywordRDF("sub1","pred1#String","obj1","Admin","source");
		KeywordRDF rdf2 = new KeywordRDF("sub1","pred1#String",null,"Admin","source");
		KeywordRDF rdf21 = new KeywordRDF("sub1","pred1#String",null,"Admin","source");
		KeywordRDF rdf22 = new KeywordRDF("sub1","pred1#String",null,"Admin","source");
		KeywordRDF rdf23 = new KeywordRDF("sub1","pred1#String",null,"Admin","source");
		KeywordRDF rdf3 = new KeywordRDF("sub2","pred1#String","obj1","Admin","source");
		KeywordRDF rdf4 = new KeywordRDF("sub1","pred2#String","obj1","Admin","source");
		KeywordRDF rdf5 = new KeywordRDF("sub1","pred1#String","obj2","Admin","source");
		KeywordRDF rdf6 = new KeywordRDF("sub2","pred2#String","obj3","Admin","source");
		KeywordRDF rdf7 = new KeywordRDF("sub2","pred2#String","obj3","Admin","source");
		
		SetOfKeywordRDF set = new SetOfKeywordRDF();
		set.add(rdf1a);
		
		set.add(rdf11);
		set.add(rdf12);
		set.add(rdf2);
		set.add(rdf23);
		set.add(rdf21);
		set.add(rdf22);
		set.add(rdf3);
		set.add(rdf4);
		set.add(rdf5);
		set.add(rdf6);
		set.add(rdf7);
		
		set.add(rdf2a);
		set.add(rdf3a);
		set.add(rdf4a);
		
		TreeNodeFactory factory = new TreeNodeFactory();
		String nodeS = "TopRDF";
		RDFTreeNode topnode = factory.addAllRDF(nodeS, set);
		System.out.println("The tree");
		System.out.println(topnode.toString());
	}

}
