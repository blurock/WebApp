/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.esblurock.react.parse.nlp.filter;

import java.util.ArrayList;
import java.util.Iterator;

import info.esblurock.react.parse.nlp.NLPToken;
/**
 *
 * @author edwardblurock
 */
public class SetOfTokenFilters extends ArrayList<FilterToken> {
        public boolean includeToken(NLPToken token) {
        boolean ans = true;
        Iterator<FilterToken> iterator = this.iterator();
        while(ans && iterator.hasNext()) {
            FilterToken next = iterator.next();
            ans = next.includeToken(token);
        }
        return ans;
    }

}
