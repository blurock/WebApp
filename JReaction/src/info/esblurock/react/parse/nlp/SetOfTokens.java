/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.esblurock.react.parse.nlp;

import java.util.ArrayList;
import java.util.Iterator;

import info.esblurock.react.parse.keywords.SetOfKeyWords;
import info.esblurock.react.parse.nlp.filter.SetOfTokenFilters;

/**
 *
 * @author edwardblurock
 */
public class SetOfTokens extends ArrayList<NLPToken> {

    public void addNextToken(String tokenS, double prob) {
        NLPToken token = new NLPToken(tokenS, prob);
        this.addNextToken(token);
    }

    public void addNextToken(NLPToken token) {
        this.add(token);
    }

    public String[] getStringTokens() {
        String[] tokens = new String[this.size()];
        Iterator<NLPToken> iterator = this.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            NLPToken next = iterator.next();
            tokens[count++] = next.getToken();
        }
        return tokens;
    }

    public double[] getProbabilities() {
        double[] probs = new double[this.size()];
        Iterator<NLPToken> iterator = this.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            NLPToken next = iterator.next();
            probs[count] = next.getProbability();
        }
        return probs;
    }

    public void filter(SetOfTokenFilters filters) {
        int count = 0;
        while (count < this.size()) {
            NLPToken token = this.get(count);
            if (!filters.includeToken(token)) {
                this.remove(count);
            } else {
                count++;
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        Iterator<NLPToken> iterator = this.iterator();
        build.append("============================================\n");
        while (iterator.hasNext()) {
            NLPToken next = iterator.next();
            build.append(next.toString());
            build.append("\n");
        }
        build.append("============================================\n");

        return build.toString();
    }

    public SetOfKeyWords setOfKeys() {
        SetOfKeyWords keys = new SetOfKeyWords();
        Iterator<NLPToken> iterator = this.iterator();
        while (iterator.hasNext()) {
            NLPToken next = iterator.next();
            String token = next.getToken();
            keys.add(token);
        }
        return keys;
    }

}
