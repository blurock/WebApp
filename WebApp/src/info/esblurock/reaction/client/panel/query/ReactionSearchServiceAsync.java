package info.esblurock.reaction.client.panel.query;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.rdf.RDFBySubjectSet;
import info.esblurock.reaction.data.rdf.SetOfKeywordQueryAnswers;

import java.util.ArrayList;
import java.util.HashSet;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ReactionSearchServiceAsync {

	void basicSearch(String search, AsyncCallback<RDFBySubjectSet> callback);

	void objectSearch(String search, AsyncCallback<RDFBySubjectSet> callback);

	void mergeSearch(HashSet<String> keyset, AsyncCallback<RDFBySubjectSet> callback);

	void singleKeyQuery(String key, AsyncCallback<RDFBySubjectSet> callback);

	void getObjectFromKey(String classname, String key, AsyncCallback<DatabaseObject> callback);

}
