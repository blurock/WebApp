package info.esblurock.reaction.parse;
import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.client.GenerateKeywords;

public class TestKeywords {

	@Test
	public void test() {
		String keyword = GenerateKeywords.createDataKeyword("LLNL", "H2");
		System.out.println("Keyword: " + keyword 
				+ ",   Source="
				+ GenerateKeywords.sourceFromDataKeyword(keyword)
				+ ",   Keyword=" + 
				GenerateKeywords.keywordFromDataKeyword(keyword));
	}

}
