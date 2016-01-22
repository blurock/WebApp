package info.esblurock.react.mechanisms;

import info.esblurock.react.common.IParsableElement;
import info.esblurock.react.common.ReactionLog;

/**
 *
 * @author  moliate
 * @version 
 */
public abstract class ReactMechanismRxn implements IParsableElement 
{

    public String[] reactantMolecules = {};
  
    public String[] productMolecules = {};
    
    public int productMultiplicity = 1;
    
    public int reactantMultiplicity = 1;
    
    public int numReactants = 0;
    
    public int numProducts = 0;
    
    public int maxMolecules = 10;
    
    /** Creates new ReactMechanismRxn */
    public ReactMechanismRxn() {
    }
    
    public boolean moleculeAsReactant(String molname) {
        for (int i=0; i < reactantMolecules.length; i++)
            if (reactantMolecules[i].equals(molname))
                return true;
        
        return false;
    }
    
    public boolean moleculeAsProduct(String molname) {
        for (int i=0; i < productMolecules.length; i++)
            if (productMolecules[i].equals(molname))
                return true;
        
        return false;
    }
    

        
    public String toString() 
    {
        StringBuffer str = new StringBuffer();
        str.append(Integer.toString(reactantMultiplicity) + " ");
        
        for(int i = 0; i<numReactants;i++) 
        {
            if(i>0)
                str.append(" + ");
            str.append(" {" + reactantMolecules[i] + "} ");
        }
        str.append(" = ");
        
        for(int j = 0; j<numProducts;j++) 
        {
            if(j>0)
                str.append(" + ");
            str.append(" {" + productMolecules[j] + "} ");
        }
        str.append("\n");
        return str.toString();
    }
    
    public String write() 
    {
        return this.toString();
    }   
    
    public String writeAsLine() 
    {
         StringBuffer str = new StringBuffer();
       for(int i = 0; i<numReactants;i++) {
            if(i>0)
                str.append("+");
            str.append(reactantMolecules[i]);
        }
        str.append("=");
        for(int j = 0; j<numProducts;j++) {
            if(j>0)
                str.append("+");
            str.append(productMolecules[j]);
        }
        return str.toString();
    }     
    

    
    public void print() 
    {
        System.out.println("----");
        System.out.println(this.toString());
        System.out.println("----");        
        ReactionLog.logInfo("----");
        ReactionLog.logInfo(this.toString());
        ReactionLog.logInfo("----");
    }
    
    public void setData(IParsableElement element) 
    {
        if (! (element instanceof ReactMechanismRxn) )
        {   
            ReactionLog.logError(" > Tried to parse an element of wrong type: " + element.getClass().getName() + " where " + this.getClass().getName()+ " was expected.");
            return;
        }
        ReactMechanismRxn e = (ReactMechanismRxn)element;
        
        maxMolecules = e.maxMolecules;
        numProducts  = e.numProducts;
        numReactants = e.numReactants;
        productMolecules = e.productMolecules;
        productMultiplicity = e.productMultiplicity;
        reactantMolecules = e.reactantMolecules;
        reactantMultiplicity = e.reactantMultiplicity;        
    }
    
    public String generateName()
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < reactantMolecules.length; i++)
        {   
           sb.append(0 == i ? "": " + ");
           sb.append(reactantMolecules[i]);

        }
                
        sb.append(" -> ");
        for (int i = 0; i < productMolecules.length; i++)
        {   
           sb.append(0 == i ? "": " + ");
           sb.append(productMolecules[i]);
        }    
                
        return sb.toString();
    }
}

