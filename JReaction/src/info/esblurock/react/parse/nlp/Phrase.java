/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.esblurock.react.parse.nlp;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author edwardblurock
 */
public class Phrase extends ArrayList<NLPChunkedToken> {
    
    
    String[] buildPhraseAsArray() {
        String[] array = new String[this.size()];
        
        Iterator<NLPChunkedToken> iterator = this.iterator();
        int count = 0;
        while(iterator.hasNext()) {
            NLPChunkedToken next = iterator.next();
            array[count++] = next.getToken();
        }
        return array;
    }
    public String buildPhrase() {
        StringBuilder build = new StringBuilder();
        String[] array = buildPhraseAsArray();
        for(int i=0;i<array.length;i++) {
            build.append(array[i]);
            build.append(" ");
        }
        return build.toString();
    }
    
    
}
