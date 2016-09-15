/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.esblurock.react.parse.nlp;

/**
 *
 * @author edwardblurock
 */
public class CreateAdjectiveNounPhraseFromFullPhrase {

    public CreateAdjectiveNounPhraseFromFullPhrase() {
    }
    public Phrase constructPhase(Phrase phrase) {
        Phrase newphrase = null;
        if(phrase.size() > 2) {
            int lastindex = phrase.size() -1;
            NLPChunkedToken last = phrase.get(lastindex);
            if(isNoun(last)) {
                int nextlastindex = lastindex -1;
                NLPChunkedToken nextlast = phrase.get(nextlastindex);
                if(isAdjective(nextlast) || isNoun(nextlast)){
                    newphrase = new Phrase();
                    newphrase.add(nextlast);
                    newphrase.add(last);
                }
            }
        }
        return newphrase;
    }


    private boolean isNoun(NLPChunkedToken token) {
        boolean ans = false;
        if(token.getCategorizedS().startsWith("NN")){
            ans = true;
    }
        return ans;
    }

    private boolean isAdjective(NLPChunkedToken token) {
        boolean ans = false;
        if(token.getCategorizedS().startsWith("JJ")){
            ans = true;
    }
        return ans;
    }
}
