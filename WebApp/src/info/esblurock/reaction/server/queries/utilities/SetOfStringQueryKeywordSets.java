package info.esblurock.reaction.server.queries.utilities;

import java.util.ArrayList;

public class SetOfStringQueryKeywordSets extends ArrayList<StringQueryKeywordSet> {

	
	public SetOfStringQueryKeywordSets() {
	}
	public SetOfStringQueryKeywordSets(SetOfStringQueryKeywordSets set) {
		this.addAll(set);
	}
	public SetOfStringQueryKeywordSets(SetOfStringQueryKeywordSets set, String keyword) {
		for(StringQueryKeywordSet single: set) {
			StringQueryKeywordSet newsingle = new StringQueryKeywordSet(single,keyword);
			this.add(newsingle);
		}
	}
	public SetOfStringQueryKeywordSets(SetOfStringQueryKeywordSets set, ArrayList<String> keywords) {
		for(StringQueryKeywordSet single: set) {
			StringQueryKeywordSet newsingle = new StringQueryKeywordSet(single);
			ArrayList<StringQueryKeywordSet> newset = newsingle.mergeSingleKeywords(keywords);
			for(StringQueryKeywordSet merged: newset) {
				this.add(merged);
			}
		}
	}
	public void union(SetOfStringQueryKeywordSets sets) {
		for(StringQueryKeywordSet set : sets) {
			this.add(set);
		}
	}
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for(StringQueryKeywordSet keywords : this) {
			buffer.append(keywords.toString());
			buffer.append("\n");
		}
		return buffer.toString();
	}
	
}
