/*
 * $Id: ReactReactionConstants.java,v 1.1.1.1 2008/11/13 13:33:14 blurock Exp $
 *
 * Copyright (c) 2000, Edward S. Blurock.
 * All Rights Reserved.
 */
package info.esblurock.react.reactions;

import info.esblurock.react.common.IParsableElement;

public abstract class ReactReactionConstants implements IParsableElement
{
    public double aFactor;
    public double nFactor;
    public double eFactor;
    public int multiplicity;
    public boolean direction;
    public String reference; 
    
    public void print() 
    {
        System.out.println(this.toString());
        System.out.println(this.toString());
    }
    
    public String toString()
    {
        return "Constants: a=" + aFactor + ", n=" + nFactor + ", e=" + eFactor + ", multiplicity=" + multiplicity; 
    }
    
    public void setData(IParsableElement element) 
    {        
        if (! (element instanceof ReactReactionConstants) )
        {   
        	System.out.println(" > Tried to parse an element of wrong type: " + element.getClass().getName() + " where " + this.getClass().getName()+ " was expected.");
            return;
        }
        ReactReactionConstants e = (ReactReactionConstants)element;
        aFactor = e.aFactor;
        nFactor = e.nFactor;
        eFactor = e.eFactor;
        multiplicity = e.multiplicity;
        direction = e.direction;
        reference = e.reference;
    }
    
}
