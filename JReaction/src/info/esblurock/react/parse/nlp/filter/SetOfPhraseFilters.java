/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.esblurock.react.parse.nlp.filter;

import java.util.ArrayList;
import java.util.Iterator;

import info.esblurock.react.parse.nlp.Phrase;

/**
 *
 * @author edwardblurock
 */
public class SetOfPhraseFilters extends ArrayList<FilterPhrase> {
    public boolean includePhrase(Phrase phrase) {
        boolean ans = true;
        Iterator<FilterPhrase> iterator = this.iterator();
        while(ans && iterator.hasNext()) {
            FilterPhrase next = iterator.next();
            ans = next.includeToken(phrase);
        }
        return ans;
    }
}
