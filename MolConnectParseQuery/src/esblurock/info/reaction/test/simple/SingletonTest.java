package esblurock.info.reaction.test.simple;

import static org.junit.Assert.*;

import org.junit.Test;

import info.esblurock.reaction.parse.SetOfInterpretations;
import info.esblurock.reaction.parse.register.RegisteredQueries;
import info.esblurock.reaction.parse.register.SetOfParseQueries;

public class SingletonTest {

	@Test
	public void test() {
		SetOfParseQueries queries = RegisteredQueries.getRegistered();
		System.out.println("Queries:\n" + queries.toString());
		
		System.out.println("Query a single token: ");
		String token = "token";
		SetOfInterpretations set = queries.parseInput(token);
		System.out.println("Interpretations: \n" + set.toString());
		
		
	}

}
