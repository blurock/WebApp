/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.esblurock.react.parse.nlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import info.esblurock.react.parse.keywords.SetOfKeyWords;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

/**
 *
 * @author edwardblurock
 */
public class CatagorizeWords extends SetOfTokens{
    private String categorizeResource;
    private String tokenResource;
    private String chunkerResource;
    /**
     *
     * @param text
     */
    public CatagorizeWords(String text, String categorizeResource, String tokenResource, String chunkerResource) {
        this.categorizeResource = categorizeResource;
        this.tokenResource = tokenResource;
        this.chunkerResource = chunkerResource;
        NLPTokenize tokenize = new NLPTokenize(text, categorizeResource, tokenResource, chunkerResource);
        SetOfTokens tokenSet = tokenize.getTokenSet();
        process(tokenSet);
    }

    private void process(SetOfTokens tokenSet) {
        StringBuilder build = new StringBuilder();
        String[] tokens = tokenSet.getStringTokens();
        InputStream modelIn = null;

        try {
        	ClassLoader classLoader = getClass().getClassLoader();
        	File file = new File(classLoader.getResource(categorizeResource).getFile());
            modelIn = new FileInputStream(file);
            POSModel model = new POSModel(modelIn);
            POSTaggerME tagger = new POSTaggerME(model);
            String tags[] = tagger.tag(tokens);
            double probs[] = tagger.probs();
            String[] stringTokens = tokenSet.getStringTokens();
            
            for(int i=0;i<stringTokens.length;i++) {
                NLPCategorizedToken token 
                        = new NLPCategorizedToken(tokens[i],tags[i],probs[i]);
                this.add(token);
            }
        } catch (IOException e) {
            // Model loading failed, handle the error
            e.printStackTrace();
        } finally {
            if (modelIn != null) {
                try {
                    modelIn.close();
                } catch (IOException e) {
                }
            }
        }
        
    }
    String[] getCategorizations() {
        String[] categories = new String[this.size()];
        Iterator<NLPToken> iterator = this.iterator();
        int count = 0;
        while(iterator.hasNext()) {
            NLPCategorizedToken next = (NLPCategorizedToken) iterator.next();
            categories[count++] = next.getCategorizedS();            
        }
        return categories;
    }
    
        public SetOfKeyWords setOfKeys() {
        SetOfKeyWords keys = new SetOfKeyWords();
        Iterator<NLPToken> iterator = this.iterator();
        while (iterator.hasNext()) {
            NLPCategorizedToken next = (NLPCategorizedToken) iterator.next();
            String token = next.getToken();
            keys.add(token);
        }
        return keys;
    }

}
