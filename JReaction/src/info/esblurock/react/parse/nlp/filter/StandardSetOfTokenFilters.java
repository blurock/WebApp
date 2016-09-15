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
public class StandardSetOfTokenFilters extends SetOfTokenFilters {
    public StandardSetOfTokenFilters() {
        FilterTokenType filter = new FilterTokenType("NN", true, false);
        this.add(filter);
    }
    
}
