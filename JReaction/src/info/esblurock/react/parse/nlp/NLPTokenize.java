/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.esblurock.react.parse.nlp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.repackaged.com.fasterxml.jackson.core.json.ReaderBasedJsonParser;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

/**
 *
 * @author edwardblurock
 */
public class NLPTokenize {
    private Resource resource;
    private String tokens[] = null;
    private double tokenProbs[] = null;
    private Span tokenSpans[] = null;
    private String text;
    private SetOfTokens tokenSet;
    
    public NLPTokenize(String t, Resource r) {
        try {
            resource = r;
            tokenSet = new SetOfTokens();
            text = t;
            process();
        } catch (IOException ex) {
            Logger.getLogger(NLPTokenize.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SetOfTokens getTokenSet() {
        return tokenSet;
    }
    
    public String getText() {
        return text;
    }

    protected void process() throws FileNotFoundException, IOException {
        InputStream modelIn = new FileInputStream(resource.getTokenResource());
        TokenizerModel model = new TokenizerModel(modelIn);
        TokenizerME tokenizer = new TokenizerME(model);
        System.out.println(text);
        tokens = tokenizer.tokenize(text);
        tokenProbs = tokenizer.getTokenProbabilities();
        tokenSpans = tokenizer.tokenizePos(text);
        
        for(int i=0;i<tokens.length; i++) {
            tokenSet.addNextToken(tokens[i],tokenProbs[i]);
        }

    }

}
