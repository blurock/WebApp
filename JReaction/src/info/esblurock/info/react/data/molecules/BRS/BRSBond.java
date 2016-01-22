
package info.esblurock.info.react.data.molecules.BRS;
import info.esblurock.info.react.data.molecules.ReactBond;
import info.esblurock.info.react.data.molecules.ReactMolecule;

import java.util.*;


public class BRSBond extends ReactBond
{


    
    public void parse(byte[] data) throws java.text.ParseException {
        String bonds = new String(data);
	StringTokenizer bondtokens = new StringTokenizer(bonds ,":");
	String preamble = bondtokens.nextToken();
        BondType        = bondtokens.nextToken().trim();
        I               = Integer.parseInt((bondtokens.nextToken()).trim());
        J               = Integer.parseInt((bondtokens.nextToken()).trim());
        Info            = Integer.parseInt((bondtokens.nextToken()).trim()); 
    }
    
    
    
    // Deprecated below this line
    // _______________________________________________________________________________________________________________
    
    /**
     * @deprecated
     * @see parse(byte[])
     */ 
    public void parse(String bonds, ReactMolecule mol)
    {
	StringTokenizer bondtokens = new StringTokenizer(bonds ,":");
	String preamble = bondtokens.nextToken();
        BondType        = bondtokens.nextToken().trim();
        I               = Integer.parseInt((bondtokens.nextToken()).trim());
        J               = Integer.parseInt((bondtokens.nextToken()).trim());
        Info            = Integer.parseInt((bondtokens.nextToken()).trim()); 
    }
    
    /**
     * @deprecated
     * @see java.lang.String.trim()
     */     
    public String betweenBlanks(String str)
    {
	int count = 0;
	while(str.startsWith(" ",count))
	    count++;
	int count1 = count;
	while(!str.startsWith(" ",count1) && str.length() > count1)
	    count1++;
	return str.substring(count,count1);
    }
    

    
}
