package info.esblurock.react.mechanisms.BRS;

//import se.lth.forbrf.terminus.utilities.ErrorFrame;
import info.esblurock.react.common.ReactionLog;
import info.esblurock.info.react.data.molecules.SDF.SDFMolecule;
import info.esblurock.react.mechanisms.ReactionMechanism;

import java.util.*;
import java.io.*;

/**
 *
 * @author  debug.reaction
 * @version 
 */
public class BRSMechanism extends ReactionMechanism
{
    public void readFromFile(File chosenFile) throws IOException 
    {
        readMolecules(chosenFile);
        try { 
	      	StringBuffer sb   = new StringBuffer(4096);
        	BufferedReader br = new BufferedReader(new FileReader(chosenFile));
        	while(br.ready())
            	sb.append(br.readLine() + System.getProperty("line.separator"));
        
        	String data = sb.toString();
        	data = data.replaceAll("%[^\\n]*\\n", ""); 		// process comments
        	data = data.replaceAll("\\\\[^\\n]*\\n", "");     	// process concatenations '\'   
        	//System.out.println(data);
        	parse(data.getBytes());	
        } 
        catch(FileNotFoundException io) {
            ReactionLog.logError("Mech File not Found" + chosenFile.toString());
        } 
        catch(IOException io)  {
            ReactionLog.logError("I/O exception: " + io.toString());
        }
        catch(java.text.ParseException io)  {
            ReactionLog.logError("Error in parsing file: " + io.toString());
        }
    }
    
    public void readMolecules(File mechfile)  {
      	String name = mechfile.getName();
      	String sdfS = name.replaceAll(".mech$", ".sdf");
      	StringBuffer sb = new StringBuffer(4096);
        File mechfileF = new File(mechfile.getParent(), sdfS);
      	try 
      	{ 
        	BufferedReader br = new BufferedReader(new FileReader(mechfileF));
        	while(br.ready())
                    sb.append(br.readLine() + "\n");
        
        	//String[] molecules = sb.toString().split("\\$+\\n");// + System.getProperty("line.separator"));
                String[] molecules = sb.toString().split("M  END");
                ReactionLog.logInfo("(M  END) Number of molecules: " + molecules.length);
                boolean noseed = true;
        	for(int i =0; i<molecules.length; i++)
        	{
                    SDFMolecule m = new SDFMolecule();
                    String mol = molecules[i]; 
                    m.parse(mol.getBytes());
   
                    if(noseed) 
                    {
                        seedMolecule = m.getMoleculeName();
                        noseed = false;
                        ReactionLog.logInfo("Read in Molecule Seed: " + seedMolecule);
                    }
                    Molecules.put(m.getMoleculeName(), m);
        	}
        } 
        catch(FileNotFoundException io) 
        {
            ReactionLog.logError("Molecule File not Found: " + sdfS);
        } 
        catch(IOException io) 
        {
            System.out.println("Done Reading Molecule File");
        }
        catch(java.text.ParseException io) 
        {
            ReactionLog.logError("Error in parsing file: " + io.toString());
        }      
  }
  /*
    public static void main(String[] args) throws Exception
    {
        new BRSMechanism().readFromFile(new File("C:\\Java\\test\\mech\\mech\\test.mech"));
    }
 /**/
    public void parse(byte[] data) throws java.text.ParseException 
    {
        String lines = new String(data);
        boolean success = true;
        rxnClasses = new Vector(); 
        StringTokenizer elements = new StringTokenizer(lines,"\n"); 
        
        String classcoeffs = elements.nextToken();
        while(!classcoeffs.startsWith("CLASSCOEFFICIENTS") && elements.hasMoreTokens()) 
            classcoeffs = elements.nextToken();
        
        if(classcoeffs.startsWith("CLASSCOEFFICIENTS")) 
        {
            try 
            {
                boolean notdone = true;
                while(notdone && success) {
                    String line = elements.nextToken();
                    if(line.startsWith("END"))
                        notdone = false;
                    else  {
                        BRSMechanismRxnClass rxnclass = new BRSMechanismRxnClass();
                        success = rxnclass.parseCoeffs(line);
                        rxnClasses.add(rxnclass);
                    }
                }
            } 
            catch(Exception ios)  {
                ReactionLog.logError("Error in parsing ReactionMechanism Coefficients: " + ios.toString());
            }
            try   {
                String classequivs = elements.nextToken();
                String equivsend = elements.nextToken();
                if(classequivs.startsWith("CLASSEQUIV") && equivsend.startsWith("END")) {
                    for(int i=0;i<rxnClasses.size() && success;i++)  {
                        String res = "";
                        String line;
                        
                        do {
                            line = elements.nextToken();
                            res += line + "\n";
                        }
                        while(!line.startsWith("END"));
                        BRSMechanismRxnClass rxnclass = (BRSMechanismRxnClass) rxnClasses.elementAt(i);
                        rxnclass.parse(res.getBytes());
                    }
                } 
                else {
                    success = false;
                }
            }
            catch (NoSuchElementException ios)  {
                //throw new java.text.ParseException(ios, -1);
            }
        } 
        else  {
            ReactionLog.logError("CLASSCOEFFICIENTS not found");
        }
        
    }
 
 
    public boolean parse(String name) 
    {
        try {
            parse(name.getBytes());
        }
        catch (java.text.ParseException e)
        {
            return false;
        }
        
        return true;
    }
}