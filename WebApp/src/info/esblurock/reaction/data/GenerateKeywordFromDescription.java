package info.esblurock.reaction.data;

import info.esblurock.reaction.data.description.DescriptionDataData;

public class GenerateKeywordFromDescription {
	
	public static String keywordDelimitor = "#";
	
	public static String createKeyword(DescriptionDataData data) {
		return data.getSourcekey() + keywordDelimitor + data.getKeyword();
	}
}
