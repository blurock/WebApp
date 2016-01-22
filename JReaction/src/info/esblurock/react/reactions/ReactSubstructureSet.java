/*
 * ReactSubstructureSet.java
 *
 * Created on February 4, 2004, 11:06 AM
 */

package info.esblurock.react.reactions;

import info.esblurock.react.common.ReactionLog;
import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.info.react.data.molecules.BRS.BRSMolecule;
import info.esblurock.react.common.SProperties;

import java.util.Vector;

/**
 *
 * @author  moliate
 */
/*
 * $Id: ReactSubstructureSet.java,v 1.1.1.1 2008/11/13 13:33:14 blurock Exp $
 *
 * Copyright (c) 2000, Edward S. Blurock.
 * All Rights Reserved.
 */


public class ReactSubstructureSet extends Vector
{
    private String REACTION_HOME =  SProperties.getProperty("reaction.home");  //SReaction.getHome();
    
    
    public ReactSubstructureSet(int num)
    {
 	super(num); 
    }
    
    public void addSubstructure(ReactMolecule corr)
    {
	add(corr);
    }
    
    
    public ReactMolecule getSubstructure(int id)
    {

	for(int i=0;i<size();i++)
	    {

		ReactMolecule element = (ReactMolecule)elementAt(i);
		if(element.getID() == id)
		    return element;
	    }
        
        return null;
    }
    
    public ReactMolecule getSubstructure(String name)
    {
	for(int i=0;i<size();i++)
	    {
		ReactMolecule element = (ReactMolecule)elementAt(i); 
		if(element.getMoleculeName().equals(name))
		    return element;
	    }
        return null;
    }
    
  
    public int getSubstructureIndex(int id)
    {

	for(int i=0;i<size();i++)
	    {
		ReactMolecule element = (ReactMolecule)elementAt(i);
		if(element.getID() == id)
		    return i;
	    }
        
        return -1;
    }
 
    public int getSubstructureIndex(String name)
    {
	for(int i=0;i<size();i++)
	    {
		ReactMolecule element = getSubstructure(i);
		if(element.getMoleculeName().equals(name))
		    return i;
	    }
        return -1;
    }
}
