package info.esblurock.reaction.parse;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import info.esblurock.reaction.data.chemical.reaction.CanonicalReactionName;
import info.esblurock.reaction.data.chemical.reaction.ParsedReactionInformation;

public class ParseKeyword {

	@Test
	public void test() {
		String inputS = "Standard#C2H4";
		
		CanonicalReactionName canonical = new CanonicalReactionName();
		ParsedReactionInformation parsed;
		try {
			parsed = canonical.getCanonicalReactionName(inputS);
			System.out.println("Parsed: '" + parsed.tokensAsString() + "'");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
