/*
 * $Id: ReactRxnPattern.java,v 1.1.1.1 2008/11/13 13:33:14 blurock Exp $
 *
 * Copyright (c) 2000, Edward S. Blurock.
 * All Rights Reserved.
 */
package info.esblurock.react.reactions;

import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.react.common.IParsableElement;
import info.esblurock.react.common.ReactionLog;
import info.esblurock.react.reactions.BRS.BRSReactionConstants;

public abstract class ReactRxnPattern implements IParsableElement
{
    public String Name;
    public int Id;
    public ReactReactionConstants Forward = new BRSReactionConstants();
    public ReactReactionConstants Reverse = new BRSReactionConstants();
    public ReactSubstructureSet Reactants = new ReactSubstructureSet(8);
    public ReactSubstructureSet Products  = new ReactSubstructureSet(8);  
    public ReactCorrespondenceSet CorrSet = new ReactCorrespondenceSet(8);
    
    //TopReactionMenu Top;
        
    public ReactRxnPattern()
    { 
    }
    /*
    public void getReactionInfo(String rxnname)
    {
	String command = new String(Top.Scripts.printReactionPattern.getValue() + " " + rxnname);
	System.out.println("Command: '" + command + "'");
	String rxnstring = Top.tLink.singleCommand(command);
	Top.tLink.stop();
	parseReaction(rxnstring);
        
    }
     */
    

    
    
    public ReactMolecule findReactant(int molID)
    {
	return Reactants.getSubstructure(molID); 
    }
    
    public ReactMolecule findProduct(int molID)
    {
	return Products.getSubstructure(molID); 
    }
    
 
    public int findReactantIndex(int molID)
    {
	return Reactants.getSubstructureIndex(molID); 
    }

    public int findProductIndex(int molID)
    {
	return Products.getSubstructureIndex(molID); 
    }
    
    
    public void print() 
    {
        ReactionLog.logInfo(Name);
        for (int i = 0; i < CorrSet.size(); i++) {
            ReactionLog.logInfo(CorrSet.elementAt(i).toString());
            System.out.println(CorrSet.elementAt(i).toString());
        }
        
        for (int i = 0; i < Reactants.size(); i++)
            {
                ReactMolecule m = (ReactMolecule)Reactants.elementAt(i);
                m.print();
            }
        
        for (int i = 0; i < Products.size(); i++)
            {
                ReactMolecule m = (ReactMolecule)Products.elementAt(i);
                m.print();
            }
        
        ReactionLog.logInfo(Forward.toString());
        ReactionLog.logInfo(Reverse.toString());     
        System.out.println(Forward.toString());  
        System.out.println(Reverse.toString());            
    }
    
    public void setData(IParsableElement element) {
        if (! (element instanceof ReactRxnPattern) )
        {   
            ReactionLog.logError(" > Tried to parse an element of wrong type: " + element.getClass().getName() + " where " + this.getClass().getName()+ " was expected.");
            return;
        }
        ReactRxnPattern e = (ReactRxnPattern)element;
        
        Name = e.Name;
        Id   = e.Id;
        Reactants.addAll(e.Reactants);
        Products.addAll(e.Products);
        Forward.setData(e.Forward);
        Reverse.setData(e.Reverse);
        CorrSet.addAll(e.CorrSet);        
    }
    
  }
