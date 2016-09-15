/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.esblurock.react.parse.nlp;

import java.util.ArrayList;
import java.util.Iterator;

import info.esblurock.react.parse.keywords.SetOfKeyWords;
import info.esblurock.react.parse.nlp.filter.SetOfPhraseFilters;

/**
 *
 * @author edwardblurock
 */
public class Phrases extends ArrayList<Phrase> {

    public Phrases(SetOfTokens tokens) {
        process(tokens);
    }
    public Phrases() {
    }

    private void process(SetOfTokens tokens) {
        Iterator<NLPToken> iterator = tokens.iterator();
        Phrase phrase = new Phrase();
        NLPChunkedToken token = (NLPChunkedToken) iterator.next();
        String currentPhrase = token.getChunkCategory();
        while (iterator.hasNext()) {
            phrase.add(token);
            token = (NLPChunkedToken) iterator.next();
            if (!token.belongsTo(currentPhrase)) {
                this.add(phrase);
                phrase = new Phrase();
                currentPhrase = token.getChunkCategory();
            }
        }
        phrase.add(token);
        this.add(phrase);
    }

    public void filter(SetOfPhraseFilters filters) {
        int count = 0;
        while (count < this.size()) {
            Phrase phrase = this.get(count);
            if (!filters.includePhrase(phrase)) {
                this.remove(count);
            } else {
                count++;
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        Iterator<Phrase> iterator = this.iterator();
        while (iterator.hasNext()) {
            Phrase next = iterator.next();
            if (next.size() > 1) {
                String phrase = next.buildPhrase();
                build.append(phrase);
                build.append("\n");
            }
        }

        return build.toString();
    }

    public SetOfKeyWords setOfKeys() {
        SetOfKeyWords keys = new SetOfKeyWords();
        Iterator<Phrase> iterator = this.iterator();
        while (iterator.hasNext()) {
            Phrase next = iterator.next();
            String phrase = next.buildPhrase();
            keys.add(phrase);
        }
        return keys;
    }
}
