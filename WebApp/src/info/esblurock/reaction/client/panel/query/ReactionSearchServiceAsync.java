package info.esblurock.reaction.client.panel.query;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.chemical.reaction.ChemkinCoefficientsData;
import info.esblurock.reaction.data.rdf.RDFBySubjectSet;
import info.esblurock.reaction.data.rdf.graph.RDFTreeNode;
import info.esblurock.reaction.data.repository.ListOfRepositoryDataSources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ReactionSearchServiceAsync {

	void basicSearch(String search, AsyncCallback<RDFBySubjectSet> callback);

	void objectSearch(String search, AsyncCallback<RDFBySubjectSet> callback);

	void mergeSearch(HashSet<String> keyset, AsyncCallback<RDFBySubjectSet> callback);

	void singleKeyQuery(String key, AsyncCallback<RDFBySubjectSet> callback);

	void getObjectFromKey(String classname, String key,
			AsyncCallback<DatabaseObject> callback);

	void searchedRegisteredQueries(String query, String path, AsyncCallback<RDFTreeNode> callback);

	void registerSynonyms(HashMap<String, ArrayList<String>> standardKeywordSynonyms, AsyncCallback<String> callback);

	void getSynonymsForStandardKeywords(AsyncCallback<HashMap<String, ArrayList<String>>> callback);

	void getRepositoryDataSources(AsyncCallback<ListOfRepositoryDataSources> callback);

	void coefficientsFromReactionName(String reactionname, AsyncCallback<ArrayList<ChemkinCoefficientsData>> callback);

}
