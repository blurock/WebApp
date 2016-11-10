/*
 * SDFAtom.java
 *
 * Created on February 17, 2004, 7:41 AM
 */

package info.esblurock.info.react.data.molecules.SDF;

import info.esblurock.react.common.Parser;
import info.esblurock.info.react.data.molecules.ReactAtom;
import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.info.react.data.molecules.ReactPeriodicTable;

import java.io.IOException;




/**
 *
 * @author  moliate
 */
public class SDFAtom extends ReactAtom{
    static final String[] pattern = {Parser.FLOAT, Parser.FLOAT, Parser.FLOAT, Parser.STRING, "*" + Parser.INT, Parser.INT};
    /** Creates a new instance of SDFAtom */
    public SDFAtom() {
    }
    
    public void parse(byte[] data) throws java.text.ParseException {
        String line = new String(data);
        Parser parser = new Parser(pattern);
        parser.parse(line);
        
        X = parser.nextFloat();
        Y = parser.nextFloat();
        Z = parser.nextFloat();
        AtomicNumber = ReactPeriodicTable.AtomNumber( parser.nextString() );
        Info = parser.nextInt();
           }
    // Deprecated below this line
    // _______________________________________________________________________________________________________________
    
     /**
     * @deprecated
     * @see parse(byte[])
     */  
     public void parse(String atom, ReactMolecule mol) throws IOException {
        try {
            String xS = atom.substring(0,9);
            String yS = atom.substring(10,19);
            String zS = atom.substring(20,29);
            X = Float.parseFloat(xS);
            Y = Float.parseFloat(yS);
            Z = Float.parseFloat(zS);

            String atomicNumberS = atom.substring(31,32);
            AtomicNumber = mol.getAtomicNumber(atomicNumberS);
            String infoS = atom.substring(38,39);
            Info = Integer.parseInt(infoS);
        } catch(java.lang.NumberFormatException io) {
            throw new IOException("Invalid Atom of SDF file: '" + atom +  "'");
    }
    }
}
