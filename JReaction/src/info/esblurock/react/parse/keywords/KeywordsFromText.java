/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.esblurock.react.parse.keywords;

import java.util.Iterator;

import info.esblurock.react.parse.nlp.CreateAdjectiveNounPhraseFromFullPhrase;
import info.esblurock.react.parse.nlp.NLPChunker;
import info.esblurock.react.parse.nlp.Phrase;
import info.esblurock.react.parse.nlp.Phrases;
import info.esblurock.react.parse.nlp.Resource;
import info.esblurock.react.parse.nlp.filter.SetOfPhraseFilters;
import info.esblurock.react.parse.nlp.filter.SetOfTokenFilters;
import info.esblurock.react.parse.nlp.filter.StandardSetOfPhraseFilters;
import info.esblurock.react.parse.nlp.filter.StandardSetOfTokenFilters;

/**
 *
 * @author edwardblurock
 */
public class KeywordsFromText {
    private String categorizeResource= "resources/en-pos-maxent.bin";
    private String tokenResource= "resources/en-token.bin";
    private String chunkerResource = "resources/en-chunker.bin";

    SetOfPhraseFilters phraseFilters;
    SetOfTokenFilters tokenFilters;
    Phrases phrases;
    NLPChunker categorize;
    
    SetOfKeyWords phraseKeyWords;
    SetOfKeyWords singleKeyWords;
    
    public KeywordsFromText(String categorizeResource, String tokenResource, String chunkerResource) {
        this.categorizeResource = categorizeResource;
        this.tokenResource = tokenResource;
        this.chunkerResource = chunkerResource;
        phraseFilters = new StandardSetOfPhraseFilters();
        tokenFilters = new StandardSetOfTokenFilters();
    }

    public SetOfKeyWords getPhraseKeyWords() {
        return phraseKeyWords;
    }

    public SetOfKeyWords getSingleKeyWords() {
        return singleKeyWords;
    }
    
    
    public SetOfKeyWords calculateKeyWords(String text) {
        process(text);
        filter();
       
        singleKeyWords = categorize.setOfKeys();
        phraseKeyWords = phrases.setOfKeys();
        
        SetOfKeyWords keys = new SetOfKeyWords();
        keys.addAll(singleKeyWords);
        keys.addAll(phraseKeyWords);
        
        return keys;
        
    }
    
    private void process(String text) {
        categorize = new NLPChunker(text, categorizeResource, tokenResource, chunkerResource);
        phrases = new Phrases(categorize);
        
        CreateAdjectiveNounPhraseFromFullPhrase anphrase = new CreateAdjectiveNounPhraseFromFullPhrase();
        Iterator<Phrase> iterator = phrases.iterator();
        Phrases newphrases = new Phrases();
        while(iterator.hasNext()) {
            Phrase next = iterator.next();
            //System.out.println(next.toString());
            Phrase constructPhase = anphrase.constructPhase(next);
            if(constructPhase != null){
                newphrases.add(constructPhase);
                System.out.println(constructPhase.toString());
            }
        }
        phrases.addAll(newphrases);
    }
    private void filter() {
        categorize.filter(tokenFilters);
        phrases.filter(phraseFilters);
    }
    
    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("Single KeyWords =====================\n");
        build.append(singleKeyWords.toString());
        build.append("Phrase KeyWords =====================\n");
        build.append(phraseKeyWords.toString());
        build.append("=====================================\n");
        return build.toString();
    }
}
