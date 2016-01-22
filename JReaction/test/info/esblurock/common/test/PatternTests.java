package info.esblurock.common.test;

import static org.junit.Assert.*;
import info.esblurock.react.common.PatternRecognizer;

import org.junit.Test;

public class PatternTests {

	@Test
	public void test() {
		PatternRecognizer recognizer = new PatternRecognizer();
		
		String integer1 = "123";
		String integer2 = "1";
		String integer3 = "1234567890";
		
		String decimal1 = ".01";
		String decimal2 = "3.1";
		String decimal3 = "3.";
		
		String numberStart1 = "2X";
		String numberStart2 = "2XYZ";
		
		String floating1 = "5E6";
		String floating2 = ".2e-14";
		String floating3 = "7E+3";
		String floating4 = "5.E2";
		String floating5 = "1e2";
		
		System.out.println("Test for integers:");
		System.out.println(integer1 + "\t\ttest=" + recognizer.stringIsAnInteger(integer1));
		System.out.println(integer2 + "\t\ttest=" + recognizer.stringIsAnInteger(integer2));
		System.out.println(integer3 + "\t\ttest=" + recognizer.stringIsAnInteger(integer3));
		System.out.println(decimal1 + "\t\ttest=" + recognizer.stringIsAnInteger(decimal1));
		System.out.println(decimal2 + "\t\ttest=" + recognizer.stringIsAnInteger(decimal2));
		System.out.println(decimal3 + "\t\ttest=" + recognizer.stringIsAnInteger(decimal3));
		System.out.println(numberStart1 + "\t\ttest=" + recognizer.stringIsAnInteger(numberStart1));
		System.out.println(numberStart2 + "\t\ttest=" + recognizer.stringIsAnInteger(numberStart2));
		System.out.println(floating1 + "\t\ttest=" + recognizer.stringIsAnInteger(floating1));
		System.out.println(floating2 + "\t\ttest=" + recognizer.stringIsAnInteger(floating2));
		System.out.println(floating3 + "\t\ttest=" + recognizer.stringIsAnInteger(floating3));
		System.out.println(floating4 + "\t\ttest=" + recognizer.stringIsAnInteger(floating4));
		
		System.out.println("Test for Floating:");
		System.out.println(floating1 + "\t\ttest=" + recognizer.nonIntegerFloat(floating1));
		System.out.println(floating2 + "\t\ttest=" + recognizer.nonIntegerFloat(floating2));
		System.out.println(floating3 + "\t\ttest=" + recognizer.nonIntegerFloat(floating3));
		System.out.println(floating4 + "\t\ttest=" + recognizer.nonIntegerFloat(floating4));
		System.out.println(floating5 + "\t\ttest=" + recognizer.nonIntegerFloat(floating5));
		System.out.println(decimal1 + "\t\ttest=" + recognizer.nonIntegerFloat(decimal1));
		System.out.println(decimal2 + "\t\ttest=" + recognizer.nonIntegerFloat(decimal2));
		System.out.println(decimal3 + "\t\ttest=" + recognizer.nonIntegerFloat(decimal3));
		System.out.println(integer1 + "\t\ttest=" + recognizer.nonIntegerFloat(integer1));
		System.out.println(integer2 + "\t\ttest=" + recognizer.nonIntegerFloat(integer2));
		System.out.println(integer3 + "\t\ttest=" + recognizer.nonIntegerFloat(integer3));
		
		System.out.println("\n Number followed by a character");
		String combo11 = "1o";
		String combo12 = "3o2";
		String combo13 = "5h2o2";
		String combo14 = "6abc123";
		String combo15 = "12a555bc";
		String combo16 = "3H2O";
		String combo17 = "1a.";
		
		String word1 = "oh";
		String word2 = "h2o";
		String word3 = "h2";
		
		
		System.out.println(combo11 + "\t\ttest=" + recognizer.numberFollowedByCharacter(combo11));
		System.out.println(combo12 + "\t\ttest=" + recognizer.numberFollowedByCharacter(combo12));
		System.out.println(combo13 + "\t\ttest=" + recognizer.numberFollowedByCharacter(combo13));
		System.out.println(combo14 + "\t\ttest=" + recognizer.numberFollowedByCharacter(combo14));
		System.out.println(combo15 + "\t\ttest=" + recognizer.numberFollowedByCharacter(combo15));
		System.out.println(combo16 + "\t\ttest=" + recognizer.numberFollowedByCharacter(combo16));
		System.out.println(combo17 + "\t\ttest=" + recognizer.numberFollowedByCharacter(combo17));
		System.out.println(integer1 + "\t\ttest=" + recognizer.numberFollowedByCharacter(integer1));
		System.out.println(integer2 + "\t\ttest=" + recognizer.numberFollowedByCharacter(integer2));
		System.out.println(word1 + "\t\ttest=" + recognizer.numberFollowedByCharacter(word1));
		System.out.println(word2 + "\t\ttest=" + recognizer.numberFollowedByCharacter(word1));
		System.out.println(word3 + "\t\ttest=" + recognizer.numberFollowedByCharacter(word1));

		System.out.println("Isolate counter");
		int pos = recognizer.positionOfFirstLetter(combo11);
		System.out.println(combo11 + "\t\ttest(number, rest)='" + combo11.substring(0,pos) + "', '" +combo11.substring(pos) + "'");
		pos = recognizer.positionOfFirstLetter(combo12);
		System.out.println(combo12 + "\t\ttest(number, rest)='" + combo12.substring(0,pos) + "', '" +combo12.substring(pos) + "'");
		pos = recognizer.positionOfFirstLetter(combo13);
		System.out.println(combo13 + "\t\ttest(number, rest)='" + combo13.substring(0,pos) + "', '" +combo13.substring(pos) + "'");
		pos = recognizer.positionOfFirstLetter(combo14);
		System.out.println(combo14 + "\t\ttest(number, rest)='" + combo14.substring(0,pos) + "', '" +combo14.substring(pos) + "'");
		pos = recognizer.positionOfFirstLetter(combo15);
		System.out.println(combo15 + "\t\ttest(number, rest)='" + combo15.substring(0,pos) + "', '" +combo15.substring(pos) + "'");
		pos = recognizer.positionOfFirstLetter(combo16);
		System.out.println(combo16 + "\t\ttest(number, rest)='" + combo16.substring(0,pos) + "', '" +combo16.substring(pos) + "'");
		pos = recognizer.positionOfFirstLetter(combo17);
		System.out.println(combo17 + "\t\ttest(number, rest)='" + combo17.substring(0,pos) + "', '" +combo17.substring(pos) + "'");

		}
	
	
	}

