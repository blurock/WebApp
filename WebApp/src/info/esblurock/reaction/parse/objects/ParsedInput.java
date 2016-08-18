package info.esblurock.reaction.parse.objects;

import java.util.ArrayList;
/** This holds the parsed set of keywords.
 * 
 * This is the base class of specialized input parsing.
 * What is caught in the specialized parsing, is a general keyword.
 * 
 * @author edwardblurock
 * @
 */
public class ParsedInput  {
	ArrayList<String> keyList;
	protected int tokenCount;

	public ParsedInput() {
		keyList = new ArrayList<String>();
	}
	
	public void add(String key)  {
		keyList.add(key);
		tokenCount++;
	}

	public int getTokenCount() {
		return tokenCount;
	}

	public String tokensAsString() {
		StringBuilder build = new StringBuilder();
		for(String key : keyList) {
			build.append(key);
			build.append(" ");
		}
		return build.toString();
	}
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		String keys = null;
		if(tokenCount > 0) {
			build.append("Number of tokens: " + tokenCount + "\n");
		} else {
			build.append("No tokens\n");
		}
		if(keyList.size() > 0) {
			build.append("Keys:");
			for (String rxn : keyList) {
				build.append("\n" + rxn);
			}
			keys = build.toString();
		}
		return keys;
	}
}
