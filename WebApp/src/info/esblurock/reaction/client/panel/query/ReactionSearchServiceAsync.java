package info.esblurock.reaction.client.panel.query;

import info.esblurock.reaction.data.rdf.SetOfKeywordQueryAnswers;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ReactionSearchServiceAsync {

	void basicSearch(String search,
			AsyncCallback<SetOfKeywordQueryAnswers> callback);

}
