/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.esblurock.react.nlp;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import info.esblurock.react.parse.keywords.KeywordsFromText;
import info.esblurock.react.parse.keywords.SetOfKeyWords;
import info.esblurock.react.parse.nlp.Resource;
import info.esblurock.react.parse.nlp.utilities.ReadTextFileToString;

/**
 *
 * @author edwardblurock
 */
public class ParseText {

    public ParseText() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void parseText() {
        File sampleFile = new File("test/resource/testParagraph.txt");
        ReadTextFileToString read = new ReadTextFileToString();
        read.read(sampleFile);

	    String categorizeResource= "resources/en-pos-maxent.bin";
	    String tokenResource= "resources/en-token.bin";
	    String chunkerResource = "resources/en-chunker.bin";
        KeywordsFromText keys = new KeywordsFromText(categorizeResource, tokenResource, chunkerResource);
        SetOfKeyWords calculateKeyWords = keys.calculateKeyWords(read.outputString);
        
        System.out.println(keys.toString());
        
    }
}
