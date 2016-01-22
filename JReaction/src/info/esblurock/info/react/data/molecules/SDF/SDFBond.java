/*
 * SDFBond.java
 *
 * Created on February 17, 2004, 7:51 AM
 */

package info.esblurock.info.react.data.molecules.SDF;

import info.esblurock.info.react.data.molecules.ReactBond;
import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.react.common.Parser;

/**
 *
 * @author  moliate
 */
public class SDFBond extends ReactBond{
    static final String[] pattern = {Parser.INT, Parser.INT, Parser.INT};
    /** Creates a new instance of SDFBond */
    public SDFBond() 
    {
    }
    
    public void parse(byte[] data) throws java.text.ParseException {
        String line = new String(data);
        Parser parser = new Parser(pattern);
        parser.parse(line);
        I = parser.nextInt() - 1;
        J = parser.nextInt() - 1;
        switch(parser.nextInt())
        { 
            case    1:  BondType = "Single"; break;
            case    2:  BondType = "Double"; break;
            case    3:  BondType = "Triple"; break;
            default  :  BondType = "Aromatic"; break;
        }
    }
    
    // Deprecated below this line
    // _______________________________________________________________________________________________________________
    
     /**
     * @deprecated
     * @see parse(byte[])
     */      
    public void parse(String str,ReactMolecule mol) {
            String iS = str.substring(1,3);
            String jS = str.substring(4,6);
            String bS = str.substring(7,9);
            I = Integer.parseInt(iS.trim()) - 1;
            J = Integer.parseInt(jS.trim()) - 1;
            int b = Integer.parseInt(bS.trim());
            if(b == 1) BondType = "Single";
            else if(b == 2) BondType = "Double";
            else if(b == 3) BondType = "Triple";
    }
    
}
