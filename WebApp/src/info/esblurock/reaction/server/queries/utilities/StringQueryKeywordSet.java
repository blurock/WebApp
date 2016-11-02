package info.esblurock.reaction.server.queries.utilities;

import java.util.ArrayList;

import org.apache.xalan.lib.sql.QueryParameter;

import info.esblurock.reaction.server.parse.interpretation.QueryParameters;

public class StringQueryKeywordSet extends ArrayList<String> {

	public StringQueryKeywordSet() {
		
	}
	public StringQueryKeywordSet(String keyword) {
		this.add(keyword);
	}
	public StringQueryKeywordSet(ArrayList<String> keywordset) {
		this.addAll(keywordset);
	}
	public StringQueryKeywordSet(StringQueryKeywordSet keywordset) {
		this.addAll(keywordset);
	}
	public StringQueryKeywordSet(StringQueryKeywordSet keywordset, String keyword) {
		this.addAll(keywordset);
		this.add(keyword);
	}
	
	public StringQueryKeywordSet merge(String keyword) {
		StringQueryKeywordSet set = new StringQueryKeywordSet(this);
		set.add(keyword);
		return set;
	}
	public ArrayList<StringQueryKeywordSet> mergeSingleKeywords(ArrayList<String> keywords) {
		ArrayList<StringQueryKeywordSet> set = new ArrayList<StringQueryKeywordSet>();
		for(String keyword: keywords) {
			StringQueryKeywordSet queryset = new StringQueryKeywordSet(this,keyword);
			set.add(queryset);
		}
		return set;
	}
	
	public QueryParameters generateStandardQueryParameter() {
		QueryParameters param = new QueryParameters(this.toString());
		return param;
	}
	
	public QueryParameters generateStandardQueryParameter(int limit) {
		QueryParameters param = new QueryParameters(this.toString(),limit);
		return param;
	}
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for(String keyword : this) {
			buffer.append(keyword);
			buffer.append(" ");
		}
		return buffer.toString();
	}
}
