/*
 * BRSMechanismRxn.java
 *
 * Created on February 17, 2004, 2:17 PM
 */

package info.esblurock.react.mechanisms.BRS;

import info.esblurock.react.mechanisms.ReactMechanismRxn;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author  moliate
 */
public class BRSMechanismRxn extends ReactMechanismRxn
{
    
    /** Creates a new instance of BRSMechanismRxn */
    public BRSMechanismRxn() {
    }
    
    public void parse(byte[] data) throws java.text.ParseException {
        boolean ok = parse(new String(data));
        if (!ok)
            throw new java.text.ParseException(new String(data), -1);
    }    
    
    public boolean parse(java.lang.String line) 
    {
        boolean success = true;
        Vector r = new Vector();
        Vector p = new Vector();
        StringTokenizer elements = new StringTokenizer(line);
        try {
            String mult = elements.nextToken();
            reactantMultiplicity = Integer.parseInt(mult);
            boolean notdone = true;
            while(notdone) {
                String name = elements.nextToken();
                String ptoken = elements.nextToken();
                
                r.add(isolateName(name));
                if(ptoken.equals("=")) 
                       notdone = false; 
            }
            notdone = true;
            while(notdone) {
                 String name = elements.nextToken();
                 p.add(isolateName(name));

                 if(!elements.hasMoreTokens())
                     notdone = false;
                 else 
                     name = elements.nextToken();
            }
            } catch (NoSuchElementException ios) {
            //ErrorFrame fr = new ErrorFrame("Parse Error: " + line);
            success = false;
        }
        
        numReactants = r.size();
        numProducts = p.size();
        reactantMolecules = new String[numReactants];
        productMolecules  = new String[numProducts];  
        r.copyInto(reactantMolecules);
        p.copyInto(productMolecules);
        
        return success;
    }
        
    private String isolateName(String name) {
        return name.trim().substring(1,name.length()-1); // remove '{' and '}'
    }
}
