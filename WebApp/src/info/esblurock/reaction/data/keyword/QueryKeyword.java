package info.esblurock.reaction.data.keyword;

public class QueryKeyword {
	QueryKeywordAnswer answer;
	
	public QueryKeyword(String keyword) {
		answer = new QueryKeywordAnswer();
		dummy(keyword);
	}

	private void dummy(String keyword) {
		String ans1 = keyword + ".1";
		String ans2 = keyword + ".2";
		String ans3 = keyword + ".3";
		String ans4 = keyword + ".4";
		
		answer.addKey(ans1);
		answer.addKey(ans2);
		answer.addKey(ans3);
		answer.addKey(ans4);
	
	}
	public QueryKeywordAnswer getAnswer() {
		return answer;
	}
	
}
