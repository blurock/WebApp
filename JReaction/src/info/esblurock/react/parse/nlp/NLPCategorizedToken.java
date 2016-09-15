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
public class NLPCategorizedToken extends NLPToken {
    String categorizedS;

    public NLPCategorizedToken(String token, String categorizedS, double probability) {
        super(token, probability);
        this.categorizedS = categorizedS;
    }

    public String getCategorizedS() {
        return categorizedS;
    }

    
    public NLPCategorizedToken(NLPToken nlptoken, String categorized) {
        super(nlptoken);
        categorizedS = categorized;
    }
    
    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        
        build.append("['");
        build.append(this.token);
        build.append("', '");
        build.append(this.probability);
        build.append("', '");
        build.append(this.categorizedS);
        build.append("']");
        
        return build.toString();
    }
    
}
