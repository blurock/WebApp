package info.esblurock.reaction.server.queries.utilities;

import java.util.ArrayList;
import java.util.StringTokenizer;

import info.esblurock.reaction.server.parse.interpretation.QueryParameters;
import info.esblurock.reaction.server.queries.QueryBase;

public class CombinatonSets {
	
	public CombinatonSets() {
	}

	public ArrayList<QueryParameters> generateQueryParameters(String query) {
		StringTokenizer tok = new StringTokenizer(query," ");
		ArrayList<String> lst = new ArrayList<String>();
		while(tok.hasMoreTokens()) {
			lst.add(tok.nextToken());
		}
		SetOfStringQueryKeywordSets sets = generate(lst);
		ArrayList<QueryParameters> parameters = new ArrayList<QueryParameters>();
		for(StringQueryKeywordSet keywordset : sets) {
			QueryParameters params = keywordset.generateStandardQueryParameter();
			parameters.add(params);
		}
		return parameters;
	}
	
	private ArrayList<ArrayList<String>> generateSynonyms(ArrayList<String> lst) {
		ArrayList<ArrayList<String>> totalset = new ArrayList<ArrayList<String>>();
		for(String keyword : lst) {
			ArrayList<String> set = generateSynonyms(keyword);
			totalset.add(set);
		}
		return totalset;
	}
	private ArrayList<String> generateSynonyms(String keyword) {
		ArrayList<String> lst = QueryBase.generateSynonyms(keyword);
		if(lst.size() == 0) {
			lst.add(keyword);
		}
		return lst;
	}
	private SetOfStringQueryKeywordSets generate(ArrayList<String> lst) {
		SetOfStringQueryKeywordSets keywordsets = new SetOfStringQueryKeywordSets();
		ArrayList<ArrayList<String>> synonymsets = generateSynonyms(lst);
		for(ArrayList<String> synonyms : synonymsets) {
			keywordsets = new SetOfStringQueryKeywordSets(keywordsets, synonyms);
		}
		return keywordsets;
	}
	
}
