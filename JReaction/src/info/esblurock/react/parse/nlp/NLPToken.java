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
public class NLPToken {
    protected String token;
    protected double probability;

    public NLPToken(NLPToken t) {
        token = t.token;
        probability = t.probability;
    }
    
    

    public NLPToken(String token, double probability) {
        this.token = token;
        this.probability = probability;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
    
    
}
