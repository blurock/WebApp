/*
 * $Id: BRSReactionConstants.java,v 1.1.1.1 2008/11/13 13:33:14 blurock Exp $
 *
 * Copyright (c) 2000, Edward S. Blurock.
 * All Rights Reserved.
 */
package info.esblurock.react.reactions.BRS;

import info.esblurock.react.common.Parser;
import info.esblurock.react.reactions.ReactReactionConstants;

import java.util.StringTokenizer;


public class BRSReactionConstants extends ReactReactionConstants
{
    public BRSReactionConstants(double a, double n, double e, int m, boolean f)
    {
		aFactor = a;
		nFactor = n;
		eFactor = e;
		multiplicity = m;
        direction = f;
    }
    
    public BRSReactionConstants()
    {
    }
    
    public void parse(byte[] data) throws java.text.ParseException {
        Parser parser = new Parser(new String(data));
	
		aFactor = parser.nextDouble();
		nFactor = parser.nextDouble();
		eFactor = parser.nextDouble();
		multiplicity = parser.nextInt();        
    }
    
    // _______________________________________________________________________________________
    //                              DEPRECATED BELOW THIS LINE
    
    /**
     * @deprecated
     */
    
    public BRSReactionConstants(String asLine)
    {
		StringTokenizer cTokens = new StringTokenizer(asLine," ");
	
		aFactor = Double.parseDouble(cTokens.nextToken());
		nFactor = Double.parseDouble(cTokens.nextToken());
		eFactor = Double.parseDouble(cTokens.nextToken());
		multiplicity = Integer.parseInt(cTokens.nextToken());  
    }
    
}
