package info.esblurock.react.mechanisms.test;

import static org.junit.Assert.*;
import info.esblurock.react.mechanisms.chemkin.ChemkinCoefficients;
import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.react.mechanisms.chemkin.ChemkinMoleculeList;
import info.esblurock.react.mechanisms.chemkin.ChemkinReaction;
import info.esblurock.react.mechanisms.chemkin.ChemkinString;
import info.esblurock.react.mechanisms.chemkin.ChemkinReactionList;
import info.esblurock.react.mechanisms.chemkin.ThirdBodyMolecules;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class ChemkinReactionReadTest2 {
	@Test
	public void ChemkinReactionTest() {
		System.out.println("ChemkinReactionTest2()");
		//HashMap<String,ChemkinMolecule> mols = new HashMap<String,ChemkinMolecule>();
		ChemkinMoleculeList mols = new ChemkinMoleculeList();
		mols.put("co", new ChemkinMolecule("co"));
		mols.put("h2o2", new ChemkinMolecule("h2o2"));
		mols.put("co2", new ChemkinMolecule("co2"));
		mols.put("oh", new ChemkinMolecule("oh"));
		mols.put("o2", new ChemkinMolecule("o2"));
		mols.put("h", new ChemkinMolecule("h"));
		mols.put("ho2", new ChemkinMolecule("ho2"));
		mols.put("h2", new ChemkinMolecule("h2"));
		mols.put("h2o", new ChemkinMolecule("h2o"));
		mols.put("T-CH2", new ChemkinMolecule("T-CH2"));
		mols.put("c2h2", new ChemkinMolecule("c2h2"));
		mols.put("o", new ChemkinMolecule("o"));

		String commentString = "!";
				
		ChemkinMolecule molco = new ChemkinMolecule("co");
		ChemkinMolecule molco2 = new ChemkinMolecule("co");
		
		if(molco.getLabel().equals(molco2.getLabel()))
			System.out.println("equals");
		else
			System.out.println("not equals");
		
		
		
		try {
			
			String text = "co+ho2=>co2+oh  3.010E+13  0.00  2.300E+04";
			System.out.println("Parse: " + text);
			ChemkinString cs = new ChemkinString(text, "!");
			cs.nextToken();
			ChemkinReaction reaction = new ChemkinReaction(cs, mols);
			reaction.parse();
			System.out.println(reaction.toString());
			
			
			text = "co+o+m=>co2+m  3.010E+13  0.00  2.300E+04";
			System.out.println("Parse: " + text);
			cs = new ChemkinString(text, "!");
			cs.nextToken();
			reaction = new ChemkinReaction(cs, mols);
			reaction.parse();
			System.out.println(reaction.toString());
			
			text = "OH+M<=>O+H+M 9.780E+17 -0.743 1.021E+05  !This is a comment";
			System.out.println("Parse: " + text);
			cs = new ChemkinString(text, "!");
			cs.nextToken();
			reaction = new ChemkinReaction(cs, mols);
			reaction.parse();
			System.out.println(reaction.toString());
			
			text = "REV/4.500E+22 -2.000 1.000E+01/";
			ChemkinCoefficients coeffs = new ChemkinCoefficients();
			boolean revB = coeffs.parseReverse(text);
			System.out.println("Reverse: '" + text + "' : " + revB);
			System.out.println(coeffs.toString());

			text = "REV/  4.500E+22 -2.000   1.000E+01  /";
			coeffs = new ChemkinCoefficients();
			revB = coeffs.parseReverse(text);
			System.out.println("Reverse: '" + text + "' : " + revB);
			System.out.println(coeffs.toString());
			
			text = "LOW / 3.4820E+16 -4.1100E-01 -1.1150E+03 /";
			coeffs = new ChemkinCoefficients();
			revB = coeffs.parseLow(text);
			System.out.println("Low: '" + text + "' : " + revB);
			System.out.println(coeffs.toString());
			
			text = "TROE / 5.0000E-01 1.0000E-30 1.0000E+30 1.0000E+10 / !Troe Fall-off reaction";
			coeffs = new ChemkinCoefficients();
			revB = coeffs.parseTroe(text);
			System.out.println("TROE: '" + text + "' : " + revB);
			System.out.println(coeffs.toString());
			
			text = "H2/1.3/ H2O/14/ AR/.67/ CO/1.9/ CO2/3.8/ CH4/2/ C2H6/3/ HE/.67/";
			ThirdBodyMolecules thirdbody = new ThirdBodyMolecules();
			System.out.println("ThirdBody: " + text);
			thirdbody.parse(text);
			System.out.println("ThirdBody: '" + thirdbody.toString() + "'");
			
			System.out.println("-------------------------------------");
			text = 
					"REV/ 3.656E+08 1.140 -2.584E+03 /" + "\n" +
                    "LOW / 1.2020E+17 0.0000E+00 4.5500E+04 / " + "\n" +
                    "TROE / 5.0000E-01 1.0000E-30 1.0000E+30 1.0000E+10 / !Troe Fall-off reaction" + "\n" +
                    "H2/2.5/ H2O/12/ AR/.64/ CO/1.9/ CO2/3.8/ CH4/2/ C2H6/3/ HE/.64/ " + "\n";
			ChemkinString lines = new ChemkinString(text,"!");
			lines.nextToken();
			reaction = new ChemkinReaction(lines, mols);
			reaction.parseThirdBodyCoeffs();
			System.out.println(reaction.toString());
			System.out.println("-------------------------------------");
			
			text = 
					"H+O2(+M)<=>HO2(+M) 1.475E+12 0.600 0.000E+00" + "\n" +
							"REV/ 3.091E+12 0.528 4.887E+04 /" + "\n" +
							"LOW / 3.4820E+16 -4.1100E-01 -1.1150E+03 / " + "\n" +
							"TROE / 5.0000E-01 1.0000E-30 1.0000E+30 1.0000E+10 / !Troe Fall-off reaction" + "\n" +
							"H2/1.3/ H2O/14/ AR/.67/ CO/1.9/ CO2/3.8/ CH4/2/ C2H6/3/ HE/.67/ ";
			lines = new ChemkinString(text,"!");
			lines.nextToken();
			reaction = new ChemkinReaction(lines, mols);
			reaction.parse();
			System.out.println(reaction.toString());
			System.out.println("-------------------------------------");
			
			
			System.out.println("==================================================");
			ChemkinReactionList readreactions = new ChemkinReactionList();
			text = "!\n"
		+ "H+O2<=>O+OH 3.547E+15 -0.406 1.660E+04\n"
		+ "REV/ 1.027E+13 -0.015 -1.330E+02 /\n"
		+ "O+H2<=>H+OH 5.080E+04 2.670 6.292E+03\n"
				+ "REV/ 2.637E+04 2.651 4.880E+03 /\n"
		+ "OH+H2<=>H+H2O 2.160E+08 1.510 3.430E+03\n"
		+ "REV/ 2.290E+09 1.404 1.832E+04 /\n"
		+ "O+H2O<=>OH+OH 2.970E+06 2.020 1.340E+04\n"
		+ "REV/ 1.454E+05 2.107 -2.904E+03 /\n"
		+ "H2+M<=>H+H+M 4.577E+19 -1.400 1.044E+05\n"
		+ "REV/ 1.145E+20 -1.676 8.200E+02 /\n"
		+ "H2/2.5/ H2O/12/ CO/1.9/ CO2/3.8/\n"
		+ "O2+M<=>O+O+M 4.420E+17 -0.634 1.189E+05\n"
		+ "REV/ 6.165E+15 -0.500 0.000E+00 /\n"
		+ "H2/2.5/ H2O/12/ AR/.83/ CO/1.9/ CO2/3.8/ CH4/2/ C2H6/3/ HE/.83/ \n"
		+ "OH+M<=>O+H+M 9.780E+17 -0.743 1.021E+05\n"
		+ "REV/ 4.714E+18 -1.000 0.000E+00 /\n"
		+ "H2/2.5/ H2O/12/ AR/.75/ CO/1.5/ CO2/2/ CH4/2/ C2H6/3/ HE/.75/ \n"
		+ "END\n";
			lines = new ChemkinString(text,"!");
			System.out.println(lines.nextToken());
			System.out.println(lines.getCurrent());
			readreactions.parseReactions(lines, mols);
			System.out.println(readreactions.toString());
			System.out.println("==================================================");
						
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
