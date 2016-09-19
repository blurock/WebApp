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

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;

/**
 *
 * @author edwardblurock
 */
public class NLPChunker extends SetOfTokens {

    private String categorizeResource;
    private String tokenResource;
    private String chunkerResource;

    public NLPChunker(String text, String categorizeResource, String tokenResource, String chunkerResource) {
        this.categorizeResource = categorizeResource;
        this.tokenResource = tokenResource;
        this.chunkerResource = chunkerResource;
        CatagorizeWords tokenize = new CatagorizeWords(text, categorizeResource, tokenResource, chunkerResource);
        process(tokenize);
    }

    public void process(CatagorizeWords tokenize) {
        InputStream modelIn = null;
        ChunkerModel model = null;

        try {
        	ClassLoader classLoader = getClass().getClassLoader();
        	File file = new File(classLoader.getResource(chunkerResource).getFile());
            modelIn = new FileInputStream(file);
            model = new ChunkerModel(modelIn);

            ChunkerME chunker = new ChunkerME(model);

            String[] tokens = tokenize.getStringTokens();
            String[] categories = tokenize.getCategorizations();

            String tag[] = chunker.chunk(tokens, categories);
            double probs[] = chunker.probs();

            for (int i = 0; i < tokens.length; i++) {
                NLPChunkedToken chunked = new NLPChunkedToken(tokens[i], categories[i], tag[i], probs[i]);
                this.add(chunked);
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

}
