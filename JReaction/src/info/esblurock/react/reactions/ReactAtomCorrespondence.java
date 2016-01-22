/*
 * $Id: ReactAtomCorrespondence.java,v 1.1.1.1 2008/11/13 13:33:14 blurock Exp $
 *
 * Copyright (c) 2000, Edward S. Blurock.
 * All Rights Reserved.
 */
package info.esblurock.react.reactions;

import info.esblurock.react.common.IParsableElement;
import info.esblurock.react.common.ReactionLog;


public abstract class ReactAtomCorrespondence implements IParsableElement
{
    public int Molecule1;
    public int Molecule1_id;
    public int Atom1;
    public int Molecule2;
    public int Molecule2_id;
    public int Atom2;

    public ReactAtomCorrespondence()
    {
    }
    
    public void print() {
        System.out.println("Atom correspondance: "+ this);
    }
    
    public String toString()
    {
        return  Molecule1_id+":"+Atom1 + " <-> " + Molecule2_id+":"+Atom2;
    }
    
    public void setData(IParsableElement element) {
        if (! (element instanceof ReactAtomCorrespondence) )
        { 
            System.out.println(" > Tried to parse an element of wrong type: " + element.getClass().getName() + " where " + this.getClass().getName()+ " was expected.");
            return;
        }
        ReactAtomCorrespondence e = (ReactAtomCorrespondence)element;
        Atom1 = e.Atom1;
        Atom2 = e.Atom2;
        Molecule1 = e.Molecule1;
        Molecule2 = e.Molecule2;
        Molecule1_id = e.Molecule1_id;
        Molecule2_id = e.Molecule2_id;
    }
    
    // _______________________________________________________________________________________
    //                              DEPRECATED BELOW THIS LINE
    
    /**
     * @deprecated
     */   
    public ReactAtomCorrespondence(String matchpairS)
    {
		Molecule1 = Integer.parseInt(matchpairS.substring(13,21));
		Atom1 = Integer.parseInt(matchpairS.substring(22,27));
		Molecule2 = Integer.parseInt(matchpairS.substring(31,39));
		Atom2 = Integer.parseInt(matchpairS.substring(40,45));
    }
}
