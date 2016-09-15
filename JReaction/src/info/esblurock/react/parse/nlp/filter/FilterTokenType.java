/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.esblurock.react.parse.nlp.filter;

import info.esblurock.react.parse.nlp.NLPCategorizedToken;
import info.esblurock.react.parse.nlp.NLPToken;

/**
 *
 * @author edwardblurock
 */
public class FilterTokenType extends FilterToken {
    String tokenType;
    boolean include;
    boolean exact;

    public FilterTokenType(String type, boolean include, boolean exact) {
        tokenType = type;
        this.include = include;
        this.exact = exact;
    }
    
    
    
    
    
    public boolean includeToken(NLPToken token) {
        NLPCategorizedToken cat = (NLPCategorizedToken) token;
        String type = cat.getCategorizedS();
        boolean ans = true;
        if(exact) {
            ans = type.equals(tokenType);
        } else {
            ans = type.startsWith(tokenType);
        }
        if(!include) {
            ans = !ans;
        }
        return ans;
    }
}
