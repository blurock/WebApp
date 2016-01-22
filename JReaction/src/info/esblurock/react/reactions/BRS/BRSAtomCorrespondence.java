/*
 * $Id: BRSAtomCorrespondence.java,v 1.1.1.1 2008/11/13 13:33:14 blurock Exp $
 *
 * Copyright (c) 2000, Edward S. Blurock.
 * All Rights Reserved.
 */
package info.esblurock.react.reactions.BRS;

import info.esblurock.react.common.Parser;
import info.esblurock.react.reactions.ReactAtomCorrespondence;


public class BRSAtomCorrespondence extends ReactAtomCorrespondence
{
    public void parse(byte[] data) throws java.text.ParseException {
        Parser parser = new Parser(new String(data));  
        parser.nextInt(); 
        Molecule1   = parser.nextInt(); 
	Atom1       = parser.nextInt(); 
	Molecule2   = parser.nextInt();
	Atom2       = parser.nextInt();
    }
}
