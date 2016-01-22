/*
 * BRSMechanismGenerationStep.java
 *
 * Created on February 26, 2004, 12:45 PM
 */

package info.esblurock.react.mechanisms.BRS;

import info.esblurock.react.common.ReactionLog;
import info.esblurock.react.mechanisms.ReactMechanismGenerationStep;

import java.util.StringTokenizer;
/**
 *
 * @author  moliate
 */
public class BRSMechanismGenerationStep extends ReactMechanismGenerationStep 
{
           
        public void parse(byte[] data) throws java.text.ParseException 
        {
            
            String line = new String(data);
            ReactionLog.logInfo("Parse steps start.");
            StringTokenizer tok = new StringTokenizer(line,":");

            String elements = tok.nextToken();         
            ReactionLog.logInfo("The elements:  " + elements);
            StringTokenizer elementTokens = new StringTokenizer(elements,",");

            ReactionLog.logInfo("Number of Steps: " + elementTokens.countTokens());
            Patterns = new String[elementTokens.countTokens()];
            int numElements = 0;
            ReactionLog.logInfo("Begin: Through elements");
            while(elementTokens.hasMoreTokens()) 
            {
                String ele = elementTokens.nextToken();
                ReactionLog.logInfo("Token: '" + ele + "'");
                Patterns[numElements] = ele;
                numElements++;
            }
            if(tok.hasMoreTokens()) 
            {
                ReactionLog.logInfo("Begin: Through molecules");
                String molecules = tok.nextToken();
                ReactionLog.logInfo("The Molecules: " + molecules);
                StringTokenizer moleculeTokens = new StringTokenizer(molecules,",");
                stepMolecules = new String[moleculeTokens.countTokens()];
                int numMolecules = 0;
                while(moleculeTokens.hasMoreTokens()) {
                    String ele = moleculeTokens.nextToken();
                    ReactionLog.logInfo("Token: '" + ele + "'");
                    stepMolecules[numMolecules] = ele;
                    numMolecules++;
                }
            }   
            ReactionLog.logInfo("Parse steps done.");
        }
        
}
