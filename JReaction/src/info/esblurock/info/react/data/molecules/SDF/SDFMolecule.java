/*
 * SDFMolecule.java
 *
 * Created on February 17, 2004, 8:12 AM
 */

package info.esblurock.info.react.data.molecules.SDF;

import info.esblurock.react.common.Parser;
import info.esblurock.info.react.data.molecules.ReactMolecule;

import java.io.*;
import java.util.*;
/**
 *
 * @author  moliate
 */
public class SDFMolecule extends ReactMolecule{
    static final String[] pattern = {Parser.INT, Parser.LB, "*" + Parser._STRING,  Parser.LB, Parser._STRING,  Parser.LB, Parser.INT, Parser.INT,  Parser.LB};
    
    /** Creates a new instance of SDFMolecule */
    public SDFMolecule() {
    }
    /**
     * Parses a <u>single</u> molecule from an SDF file
     */
    public void parse(byte[] data) throws java.text.ParseException 
    {
        String line = new String(data);
        Parser parser = new Parser(pattern);
        parser.parse(line);
        
        ID = parser.nextInt();
        MoleculeName = parser.nextString();
        NumberOfAtoms = parser.nextInt();
        NumberOfBonds = parser.nextInt();
        String AtomsAndBonds = parser.getRemainder();
        StringTokenizer st = new StringTokenizer(AtomsAndBonds, "\n");
        for (int i = 0; i < NumberOfAtoms; i++)
        {
            SDFAtom a = new SDFAtom();
            a.parse(st.nextToken().getBytes());
            Atoms.add(a);
        }
        for (int i = 0; i < NumberOfBonds; i++)
        {
            SDFBond b = new SDFBond();
            b.parse(st.nextToken().getBytes());
            Bonds.add(b);
        }
    }
    
    /* 
    public static void main(String[] args) throws Exception
    {
        StringBuffer sb = new StringBuffer(4096);
        BufferedReader br = new BufferedReader(new FileReader("//x2//webservices//react//data//mol//molsdf//C0HO.sdf"));
        while(br.ready())
            sb.append(br.readLine() + System.getProperty("line.separator"));
        
        String[] molecules = sb.toString().split("\\$+" + System.getProperty("line.separator"));
 
        for(int i =0; i<molecules.length; i++)
        {
            SDFMolecule m = new SDFMolecule();
            String mol = molecules[i]; 
            //if (mol.trim().equals(""))
            //   continue;
            m.parse(mol.getBytes());
            System.out.println(mol);
            System.out.println("----------------");
            m.print();
            System.out.println("----------------");
        }
    }
    /**/
    
    // Deprecated below this line
    // _______________________________________________________________________________________________________________
    
     /**
     * @deprecated
     * @see parse(byte[])
     */          
    public void parseMolecule(java.io.BufferedReader inp) throws IOException {
        /*
        System.out.println("parseMolecule");
        String idS = inp.readLine();
        if(idS == null) throw new IOException();
        String ignore = inp.readLine();
        MoleculeName = inp.readLine();
        System.out.println("Molecule: " + idS + "  " + MoleculeName);
        try {
            ID = Integer.parseInt(idS);
            String numS = inp.readLine();
            StringTokenizer nums = new StringTokenizer(numS);
            if(nums.countTokens() < 2)
                throw new IOException("Invalid Begining of SDF file: '" + idS +  "'");
            NumberOfAtoms = Integer.parseInt(nums.nextToken());
            NumberOfBonds = Integer.parseInt(nums.nextToken());
        } catch(java.lang.NumberFormatException io) {
            throw new IOException("Invalid Begining of SDF file: '" + idS + "'");
        }
        try {
        for(int i=0; i < NumberOfAtoms ; i++) {
            SDFAtom atom = new SDFAtom();
            parseAtom(inp.readLine(),atom);
        }
        } catch(IOException io) {
            System.out.println(io);
            throw new IOException("Error in Reading Atoms");
        }
	for(int i=0; i < NumberOfBonds ; i++) {
            SDFBond bond = new SDFBond();
            parseBond(inp.readLine(),bond);
        }
        String next = inp.readLine();
        while(!next.startsWith("$$$")) {
            next = inp.readLine();
        }
         */
        throw new IOException("_Very_ deprecated, use parse(byte[]) instead..");
    }
}
