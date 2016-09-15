/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.esblurock.react.parse.nlp.filter;

import info.esblurock.react.parse.nlp.Phrase;

/**
 *
 * @author edwardblurock
 */
public class PhraseSizeGreaterThan extends FilterPhrase {
    int phraseSize;
    
    public PhraseSizeGreaterThan(int s) {
        phraseSize = s;
    }
    @Override
    public boolean includeToken(Phrase phrase) {
        return phrase.size() > phraseSize;
    }
}
