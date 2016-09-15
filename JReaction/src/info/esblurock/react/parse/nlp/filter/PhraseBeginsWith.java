/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.esblurock.react.parse.nlp.filter;

import info.esblurock.react.parse.nlp.NLPChunkedToken;
import info.esblurock.react.parse.nlp.Phrase;

/**
 *
 * @author edwardblurock
 */
public class PhraseBeginsWith extends FilterPhrase {

    String filterType;
    boolean include;

    public PhraseBeginsWith(String type, boolean include) {
        filterType = type;
        this.include = include;
    }

    public boolean includeToken(Phrase phrase) {
        boolean ans = true;
        NLPChunkedToken first = phrase.get(0);
        String type = first.getCategorizedS();
        ans = type.equals(filterType);
        if(!include) {
            ans = !ans;
        }
        return ans;
    }

}
