/*
 * ReactMechanismRxnClass.java
 *
 * Created on February 3, 2001, 6:44 PM
 */

package info.esblurock.react.mechanisms;

import info.esblurock.react.common.IParsableElement;
import info.esblurock.react.common.ReactionLog;
import info.esblurock.react.reactions.ReactReactionConstants;
import info.esblurock.react.reactions.BRS.BRSReactionConstants;

import java.text.DecimalFormat;
//import debug.utilities.ErrorFrame;
import java.util.Vector;
/**
 *
 * @author  reaction
 * @version 
 */
public abstract class ReactMechanismRxnClass implements IParsableElement 
{

    public String className = "";
    public Vector reactions = new Vector();
    public ReactReactionConstants forwardConstants = new BRSReactionConstants(); 
    public ReactReactionConstants reverseConstants = new BRSReactionConstants(); 
    
    /** Creates new ReactMechanismRxnClass */
    public ReactMechanismRxnClass() {
    }
    
    public Vector reactionsWithMoleculeAsReactant(String molname) {
        Vector rxns = new Vector();
        for(int i=0;i<reactions.size();i++) {
            ReactMechanismRxn rxn = (ReactMechanismRxn) reactions.elementAt(i);  
            if(rxn.moleculeAsReactant(molname)) { 
                rxns.add(rxn); 
            }
        }
        return rxns;
    }
    
    public Vector reactionsWithMoleculeAsProduct(String molname) {
        Vector rxns = new Vector();
        for(int i=0;i<reactions.size();i++) {
            ReactMechanismRxn rxn = (ReactMechanismRxn) reactions.elementAt(i); 
            if(rxn.moleculeAsProduct(molname)) {
                rxns.add(rxn);
            }
        }
        return rxns;
    }
    
    public void print() 
    {
        System.out.println(" ______ ReactMechanismRxnClass ______");
        System.out.println( toString());
        System.out.println( writeCoeffs());
        System.out.println(" ____________________________________");
        ReactionLog.logInfo(" ______ ReactMechanismRxnClass ______");
        ReactionLog.logInfo( toString());
        ReactionLog.logInfo( writeCoeffs());
        ReactionLog.logInfo(" ____________________________________");
    }
    
    public void setData(IParsableElement element) 
    {
        if (! (element instanceof ReactMechanismRxnClass) )
        {   
            ReactionLog.logError(" > Tried to parse an element of wrong type: " + element.getClass().getName() + " where " + this.getClass().getName()+ " was expected.");
            return;
        }
        ReactMechanismRxnClass e = (ReactMechanismRxnClass)element;
        
        className = e.className;
        reactions = new Vector(e.reactions);
        forwardConstants.setData(e.forwardConstants);
        reverseConstants.setData(e.reverseConstants);
    }
    
    public String toString() 
    {
        StringBuffer str = new StringBuffer();
        str.append("REACTIONCLASS = " + className + System.getProperty("line.separator"));
        for(int i=0;i<reactions.size();i++) {
            str.append((ReactMechanismRxn) reactions.elementAt(i));
        }
        str.append("END" + System.getProperty("line.separator"));
        return str.toString();
    }  
    
    public String writeCoeffs() 
    {
        StringBuffer str = new StringBuffer();
        str.append(className + " = ");
        DecimalFormat format = new DecimalFormat("00.0000####E00 ");
        str.append(format.format(forwardConstants.aFactor) + " ");
        str.append(format.format(forwardConstants.nFactor) + " ");
        str.append(format.format(forwardConstants.eFactor) + " ");
        str.append(format.format(reverseConstants.aFactor) + " ");
        str.append(format.format(reverseConstants.nFactor) + " ");
        str.append(format.format(reverseConstants.eFactor) + System.getProperty("line.separator"));
        return str.toString();
    }
        
    public String write() {
        return this.toString();
    }
}
