/*
 * BRSRxnPattern.java
 *
 * Created on February 4, 2004, 12:12 PM
 */

package info.esblurock.react.reactions.BRS;

import info.esblurock.react.common.Parser;
import info.esblurock.react.common.ReactionLog;
import info.esblurock.react.reactions.ReactAtomCorrespondence;
import info.esblurock.react.reactions.ReactCorrespondenceSet;
import info.esblurock.react.reactions.ReactRxnPattern;
import info.esblurock.react.reactions.ReactSubstructureSet;

import java.util.StringTokenizer;

/**
 *
 * @author  moliate
 */
public class BRSRxnPattern extends ReactRxnPattern{
   
  void parseReaction(String rxnpatinp)
  {
      ReactionLog.logInfo("parseReaction: reaction pattern");
      try 
      {
	  StringTokenizer rxnpatblocks  = new StringTokenizer(rxnpatinp,"~");
          //String preamble0            = rxnpatblocks.nextToken();
	  String preamble1              = rxnpatblocks.nextToken();
	  String topinfo                = rxnpatblocks.nextToken();
          try 
          {
                parseTopInfo(topinfo);
          } 
          catch(NullPointerException io) 
          {
                ReactionLog.logError("Exception caught in reading top info: " +io);
          }
          
	  ReactionLog.logInfo("Parsing Rxn Pattern: Blocks");
	  while(rxnpatblocks.hasMoreTokens())
	    {
		String property = rxnpatblocks.nextToken();
		ReactionLog.logInfo("Property '" + property +"'");
		if(-1 != property.indexOf("Reaction Constants"))
		    {
			ReactionLog.logInfo("Reaction Constants");
			parseReactionConstants(property);
		    }
		else if(-1 != property.indexOf("Total Set of Atom Correspondences"))
		    {
			parseCorrespondences(property);
		    }
	    }
	  
	  
      } catch(java.util.NoSuchElementException ios) {
	  ReactionLog.logError("Get Reaction Pattern failed: " + ios);
	  ReactionLog.logError(rxnpatinp);
      }
  }
  
    void parseTopInfo(String topinfo) throws NullPointerException
    {
	ReactionLog.logInfo("parseTopInfo");
	StringTokenizer info    = new StringTokenizer(topinfo,"\n");
	String dummy            = info.nextToken();
	Name                    = info.nextToken().trim(); 
	ReactionLog.logInfo("Name of Reaction Pattern: " + Name); 
        Parser parser           = new Parser(info.nextToken());  
	//StringNumber idS = new StringNumber(idline.substring(ids,ide));
        Id                      = parser.nextInt(); 
        ReactionLog.logInfo("Reaction ID: " + Id);
     
        int rID = 0;
        parser                  = new Parser(info.nextToken()); 
        while(parser.hasNext())
	    {
		String mid = "" + parser.nextInt();
		ReactionLog.logInfo("Reactant Molecule: " + rID + " '" + mid + "'");
		Reactants.add(rID++,mid);   

	    }
        
        rID = 0;
        parser                  = new Parser(info.nextToken()); 
        while(parser.hasNext())
	    {
		String mid = "" + parser.nextInt();
		ReactionLog.logInfo("Reactant Molecule: " + rID + " '" + mid + "'");
		Products.add(rID++,mid);   
	    }
    }
    
    void parseReactionConstants(String constants)
	{            
	    StringTokenizer constTokens = new StringTokenizer(constants,"\n");
	    try {
            if(constTokens.countTokens() >= 3) 
            {
	            
	        	String firstS   = constTokens.nextToken();
	        	String titleS   = constTokens.nextToken();
	        	String typeS    = constTokens.nextToken();
            	int idx = titleS.indexOf("Standard");
            	titleS = titleS.substring(idx + 8).trim();

			
	        if(-1 != typeS.indexOf("Forward"))
	            {

                            Forward = new BRSReactionConstants(); 
                            Forward.parse(typeS.getBytes());
                            Forward.reference = titleS;
                            Forward.direction = true;

	            }
	        if(-1 != typeS.indexOf("Reverse"))
	            {
                   
                            Reverse = new BRSReactionConstants(); 
                            Reverse.parse(typeS.getBytes());
                            Reverse.reference = titleS;  
                            Reverse.direction = false;                          

	            }
            } 
            else 
            {
                if(-1 != constTokens.nextToken().indexOf("Forward")) 
                    Forward = new BRSReactionConstants(0.0,0.0,0.0,1,true); 
                else
                    Reverse = new BRSReactionConstants(0.0,0.0,0.0,1,false); 
            }
            				}
		catch (Exception ignore) {
                    ReactionLog.logError(" > parseReactionConstants: "+ ignore.toString());
                }
	}
    
    void parseCorrespondences(String corrs)
	{
	    StringTokenizer constTokens = new StringTokenizer(corrs,"\n");

	    String firstS   = constTokens.nextToken();
	    String titleS   = constTokens.nextToken();
	    String numS     = constTokens.nextToken();
	    Parser parser   = new Parser(numS);
	    int nummatched  = parser.nextInt();
	    CorrSet = new ReactCorrespondenceSet(nummatched);
            
	    for(int i=0; i< nummatched;i++)
		{
                       try 
                       {
                            String matchpairS = constTokens.nextToken();
                            ReactAtomCorrespondence acorr = new BRSAtomCorrespondence(); 
                            acorr.parse(matchpairS.getBytes());
                            CorrSet.addCorrespondence(i,acorr);
                        } catch(java.text.ParseException e)
                        {
                            ReactionLog.logError(" > parseCorrespondences: "+ e.toString());
                        }
		}	
            // TODO: parse bonds???
	}
    
    public void parse(byte[] data) throws java.text.ParseException 
    {
        parseReaction(new String(data));
    }
    
}
