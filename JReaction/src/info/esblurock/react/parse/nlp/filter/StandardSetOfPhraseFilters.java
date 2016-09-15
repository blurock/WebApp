/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.esblurock.react.parse.nlp.filter;

/**
 *
 * @author edwardblurock
 */
public class StandardSetOfPhraseFilters extends SetOfPhraseFilters {
    public StandardSetOfPhraseFilters() {
        String period = ".";
        PhraseBeginsWith beginsP = new PhraseBeginsWith(".", false);
        PhraseBeginsWith beginsC = new PhraseBeginsWith(",", false);
        PhraseSizeGreaterThan greater = new PhraseSizeGreaterThan(1);
        this.add(greater);
        this.add(beginsP);
        this.add(beginsC);
    }
}
