package info.esblurock.reaction.client.panel.query;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

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
	   SetOfKeywordQueryAnswers basicSearch(String search);
}
