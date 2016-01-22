/*
 * ReactMechanismGenerationStep.java
 *
 * Created on February 26, 2004, 12:37 PM
 */

package info.esblurock.react.mechanisms;

import info.esblurock.react.common.IParsableElement;
import info.esblurock.react.common.ReactionLog;


/**
 *
 * @author  reaction
 * @version 
 */
public abstract class ReactMechanismGenerationStep implements IParsableElement
{
    
    public String[] Patterns = {};   
    public String[] stepMolecules = {};
    public int index;
    
   
   public String toString()
   {
       String str = new String();
       for(int i = 0; i < Patterns.length; i++) {
            String m = new String(Patterns[i]);
            str.concat(m);
            if(i != Patterns.length - 1) 
                        str.concat(",\\");
            str.concat("\n");
        }
       String colon = new String(":");
       str.concat(colon);
        for(int i = 0; i < stepMolecules.length; i++) {
                String m = new String(stepMolecules[i]);
                str.concat(m);
                if(i != stepMolecules.length - 1) 
                        str.concat(",");
            }
       str.concat("\n");
       return str;         
   }


   public void print() 
   {
       System.out.println(this.toString());
       ReactionLog.logInfo(this.toString());
   }
   
   public void setData(IParsableElement element) 
   {
        if (! (element instanceof ReactMechanismGenerationStep) )
        {   
            ReactionLog.logError(" > Tried to parse an element of wrong type: " + element.getClass().getName() + " where " + this.getClass().getName()+ " was expected.");
            return;
        }
        
        ReactMechanismGenerationStep e = (ReactMechanismGenerationStep)element;
        index           = e.index;
        Patterns        = e.Patterns;
        stepMolecules   = e.stepMolecules;
   }
   
   /**
    *@deprecated
    */
   public String write() {return this.toString();}
}
