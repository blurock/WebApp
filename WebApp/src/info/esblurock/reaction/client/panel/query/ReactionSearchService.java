package info.esblurock.reaction.client.panel.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.rdf.RDFBySubjectSet;
import info.esblurock.reaction.data.rdf.graph.RDFTreeNode;
import info.esblurock.reaction.data.repository.ListOfRepositoryDataSources;

@RemoteServiceRelativePath("reactionqueryservice")
public interface ReactionSearchService  extends RemoteService {

	   public static class Util
	   {
	       private static ReactionSearchServiceAsync instance;

	       public static ReactionSearchServiceAsync getInstance()
	       {
	           if (instance == null)
	           {
	               instance = GWT.create(ReactionSearchService.class);
	           }
	           return instance;
	       }
	   }
	   RDFBySubjectSet basicSearch(String search);
	   RDFBySubjectSet objectSearch(String search);
	   RDFBySubjectSet mergeSearch(HashSet<String> keyset) throws IOException;
	   RDFBySubjectSet singleKeyQuery(String key) throws IOException;
	   RDFTreeNode searchedRegisteredQueries(String query) throws IOException;
	   DatabaseObject getObjectFromKey(String classname, String key) throws Exception;
	   String registerSynonyms(HashMap<String,ArrayList<String>> standardKeywordSynonyms) throws IOException;
	   HashMap<String,ArrayList<String>> getSynonymsForStandardKeywords() throws IOException;
	   ListOfRepositoryDataSources getRepositoryDataSources() throws IOException;
}
