/*
 * ReactionMechanism.java
 *
 * Created on February 3, 2001, 6:40 PM
 */

package info.esblurock.react.mechanisms;
import info.esblurock.react.common.IParsableElement;
import info.esblurock.react.common.ReactionLog;
import java.util.Vector;
import java.util.Hashtable;

/**
 *
 * @author  moliate
 * @version 
 */
public abstract class ReactionMechanism implements IParsableElement 
{
    public Vector rxnClasses = new Vector();
    public Hashtable Molecules = new Hashtable();
    public String seedMolecule = "";
    /** Creates new ReactionMechanism */
    
    public ReactionMechanism() 
    {
    }
    
    
    public String write() 
    {
        return this.toString();
    }  
     public String toString() 
    {
       StringBuffer str = new StringBuffer();
        str.append("CLASSCOEFFICIENTS\n");
        for(int i=0;i<rxnClasses.size();i++) {
           ReactMechanismRxnClass rxnclass = 
                  (ReactMechanismRxnClass) rxnClasses.elementAt(i);
           str.append(rxnclass.writeCoeffs());
        }
        str.append("END\n");
        str.append("CLASSEQUIVALENT\n");
        str.append("END\n");
        for(int i=0;i<rxnClasses.size();i++) {
           ReactMechanismRxnClass rxnclass = 
                  (ReactMechanismRxnClass) rxnClasses.elementAt(i);
           str.append(rxnclass.write());
        }
        //str.append("END\n");
        return str.toString();
    }        
    
    public void print() 
    {
        System.out.println(this.toString());
        ReactionLog.logInfo(this.toString());
    }
    
    public void setData(IParsableElement element) 
    {
        if (! (element instanceof ReactionMechanism) )
        {   
            ReactionLog.logError(" > Tried to parse an element of wrong type: " + element.getClass().getName() + " where " + this.getClass().getName()+ " was expected.");
            return;
        }
        ReactionMechanism e = (ReactionMechanism)element;
        
        Molecules = e.Molecules;
        rxnClasses = e.rxnClasses;
        seedMolecule = e.seedMolecule;
    }
    
}
