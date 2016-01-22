/*
 * BRSMechanismRxn.java
 *
 * Created on February 17, 2004, 1:38 PM
 */

package info.esblurock.react.mechanisms.BRS;

import info.esblurock.react.common.Parser;
import info.esblurock.react.common.ReactionLog;
import info.esblurock.react.mechanisms.ReactMechanismRxnClass;
import info.esblurock.react.reactions.BRS.BRSReactionConstants;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author  moliate
 */
public class BRSMechanismRxnClass extends ReactMechanismRxnClass
{
    public static String[] pattern = {Parser._STRING, "*=", Parser.FLOAT, Parser.FLOAT, Parser.FLOAT, Parser.FLOAT, Parser.FLOAT, Parser.FLOAT};

    /** Creates a new instance of BRSMechanismRxn */
    public BRSMechanismRxnClass() 
    {
    }
        
    public boolean parse(StringTokenizer elements) 
    {
        boolean success = true;
        reactions = new Vector();
        try {
            //String clsname = elements.nextToken();
            //System.out.println("ClassName: " + clsname);
            boolean notdone = true;
            while(notdone) {
                String rxn = elements.nextToken();
                if(rxn.equals("END")) 
                    notdone = false;
                else 
                {
                    BRSMechanismRxn r = new BRSMechanismRxn();
                    success = r.parse(rxn);
                    reactions.add(r);
                }
            }
        } catch (NoSuchElementException ios) {
            ReactionLog.logError("Error in parsing Reaction Tokens: " + ios);
            success = false;
        } catch(Exception ios) {
            ReactionLog.logError("Error in parsing Reaction: " + ios);
            success = false;
        }
        return success;
    }
        
    public boolean parseCoeffs(String line) 
    {
     try{
        Parser parser = new Parser(pattern);
        parser.parse(line);
        ReactionLog.logInfo("parseCoeffs " + line);

        className = parser.nextString().trim();
        double af = parser.nextDouble();
        double nf = parser.nextDouble();
        double ef = parser.nextDouble();
        double ar = parser.nextDouble();
        double nr = parser.nextDouble();
        double er = parser.nextDouble();
        forwardConstants = new BRSReactionConstants( af, nf, ef, 1, true );  
        reverseConstants = new BRSReactionConstants( ar, nr, er, 1, false ); 
     } 
     catch(java.text.ParseException e)
     {
         return false;
     }
     return true;
    }

    

    
    public void parse(byte[] data) throws java.text.ParseException 
    {       
        StringTokenizer elements = new StringTokenizer(new String(data), "\n");
        boolean success = true;
        reactions = new Vector();
        try 
        {
            String clsname = elements.nextToken().trim();
            System.out.println("ClassName: " + clsname);
            boolean notdone = true;
            while(notdone) 
            {
                String rxn = elements.nextToken();
                if(rxn.startsWith("END")) 
                    notdone = false;
                else 
                {
                    BRSMechanismRxn r = new BRSMechanismRxn();
                    success = r.parse(rxn);
                    reactions.add(r);
                }
            }
        } 
        catch (NoSuchElementException ios) 
        {
            System.out.println("Error in parsing Reaction Tokens");
            success = false;
        } 
        catch(Exception ios) 
        {
            System.out.println("Error in parsing Reaction");
            success = false;
        }
    }
}
