/*
 * $Id: ReactCorrespondenceSet.java,v 1.1.1.1 2008/11/13 13:33:14 blurock Exp $
 *
 * Copyright (c) 2000, Edward S. Blurock.
 * All Rights Reserved.
 */
package info.esblurock.react.reactions;

import java.util.*;

public class ReactCorrespondenceSet extends Vector implements Cloneable
{

    public ReactCorrespondenceSet(int num)
    {
	super(num);
    }

    public void addCorrespondence(int index, ReactAtomCorrespondence corr)
    {
	add(index,corr);
    }
    
    ReactAtomCorrespondence getCorrespondence(int index)
    {
	return (ReactAtomCorrespondence) elementAt(index);
    }
    
    public ReactAtomCorrespondence getReactantCorrespondence_id(int mol_id, int atm)
    {

	for(int i=0;i<size();i++)
	    {
		ReactAtomCorrespondence element = getCorrespondence(i);
		if(element.Molecule1_id == mol_id && element.Atom1 == atm)
		    return element;
	    }
        
        return null;
    }
    
    public ReactAtomCorrespondence getProductCorrespondence_id(int mol_id, int atm)
    {
	for(int i=0;i<size();i++)
	    {
		ReactAtomCorrespondence element = getCorrespondence(i);
		if(element.Molecule2_id == mol_id && element.Atom2 == atm)
		    return element;
	    }
        return null;
    }
    
    public ReactAtomCorrespondence getReactantCorrespondence(int mol, int atm)
    {

	for(int i=0;i<size();i++)
	    {
		ReactAtomCorrespondence element = getCorrespondence(i);
		if(element.Molecule1 == mol && element.Atom1 == atm)
		    return element;
	    }
        
        return null;
    }
    
    public ReactAtomCorrespondence getProductCorrespondence(int mol, int atm)
    {
	for(int i=0;i<size();i++)
	    {
		ReactAtomCorrespondence element = getCorrespondence(i);
		if(element.Molecule2 == mol && element.Atom2 == atm)
		    return element;
	    }
        return null;
    }
}
