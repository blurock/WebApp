package info.esblurock.reaction.client.panel.query;

import java.util.ArrayList;
import java.util.HashSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.rdf.RDFBySubjectSet;
import info.esblurock.reaction.data.rdf.SetOfKeywordQueryAnswers;

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
	   RDFBySubjectSet mergeSearch(HashSet<String> keyset);
	   RDFBySubjectSet singleKeyQuery(String key);
	   
	   DatabaseObject getObjectFromKey(String classname, String key) throws Exception;
	   
}
