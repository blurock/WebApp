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
public class NLPChunkedToken extends NLPCategorizedToken {
    String chunkCategory;
    
    NLPChunkedToken(String token, String categorizedS, String chunk, double probability) {
        super(token,categorizedS,probability);
        chunkCategory = chunk;
    }

    public String getChunkCategory() {
        return chunkCategory;
    }
    
    public boolean belongsTo(String category) {
        boolean ans = chunkCategory.equals(category);
        return ans;
    }
    
    @Override
        public String toString() {
        StringBuilder build = new StringBuilder();
        
        build.append("['");
        build.append(this.token);
        build.append("', '");
        build.append(this.categorizedS);
        build.append("', '");
        build.append(this.chunkCategory);
        build.append("', ");
        build.append(this.probability);
        build.append("]");
        
        return build.toString();
    }

}
