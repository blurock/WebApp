/*
 * BRSMechanismGeneration.java
 *
 * Created on February 26, 2004, 12:51 PM
 */

package info.esblurock.react.mechanisms.BRS;

import info.esblurock.react.common.ReactionLog;
import info.esblurock.react.mechanisms.ReactMechanismGeneration;
import info.esblurock.react.mechanisms.ReactMechanismGenerationStep;

import java.util.StringTokenizer;

/**
 *
 * @author  moliate
 */
public class BRSMechanismGeneration extends ReactMechanismGeneration
{
    
    /** Creates a new instance of BRSMechanismGeneration */
    public BRSMechanismGeneration() {
    }
    
    public void parse(byte[] data) throws java.text.ParseException 
    {
        
        String line = new String(data);
        line = line.replaceAll("%[^\\n]*\\n", ""); 		// process comments
        line = line.replaceAll("\\\\[^\\n]*\\n", "");     	// process concatenations '\' 
        StringTokenizer linetokens = new StringTokenizer(line, "\n");
        mechanismName = linetokens.nextToken();
        String molecules = linetokens.nextToken();
        StringTokenizer moltokens = new StringTokenizer(molecules,",");
        initialMolecules = new String[moltokens.countTokens()];
        ReactionLog.logInfo("Begin: Parse mechanism generation");
        int i = 0;
        while(moltokens.hasMoreTokens()) {
            initialMolecules[i] = moltokens.nextToken();
            i++;
        }
        int num = 0;
        Steps = new ReactMechanismGenerationStep[linetokens.countTokens()];
        boolean success = true;

        while(linetokens.hasMoreTokens() && success) {
            Steps[num] = new BRSMechanismGenerationStep(); 
            String next = linetokens.nextToken();
            Steps[num].parse(next.getBytes()); 
            Steps[num].index = num;
            num++;
        }
        ReactionLog.logInfo("End:   Parse mechanism generation");
    }
    
    
}
