package info.esblurock.react.mechanisms.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.react.mechanisms.chemkin.ChemkinMoleculeList;
import info.esblurock.react.mechanisms.chemkin.ChemkinReaction;
import info.esblurock.react.mechanisms.chemkin.ChemkinString;

import org.junit.Test;

public class ChemkinReactionReadTest3 {

	@Test
	public void test() {

		System.out.println("ChemkinReactionTest()");
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

		String commentString = "!";
		
		

		
		try {
			String rxn1 = "2 H+M<=>H2+M      1.300e+18   -1.000      0.00";
			ChemkinString ch = new ChemkinString(rxn1, commentString);
			ChemkinReaction reaction = new ChemkinReaction(ch, mols);
			String repaired = reaction.repairReactionDeclaration(rxn1);
			System.out.println("Before:  " + rxn1 + 
					         "\nAfter :  " + repaired);
			
			String rxn2 = "2 T-CH2<=>C2H2+2 H    1.000e+14    0.000      0.00!The comment";
			ch = new ChemkinString(rxn2, commentString);
			reaction = new ChemkinReaction(ch, mols);
			repaired = reaction.repairReactionDeclaration(rxn2);
			System.out.println("Before:  " + rxn2 + 
					         "\nAfter :  " + repaired);

			String rxn3 = "2 T-CH2<=>C2H2+2 H    1.000e+14    0.000      0.00!The comment";
			ch = new ChemkinString(rxn3, commentString);
			ch.nextToken();
			reaction = new ChemkinReaction(ch, mols);
			reaction.parse();
			System.out.println(reaction.toString());
			
			String rxn4 = "OH+M<=>H2O2+H+M                        7.780e+13    0.000  13513.38" +"\n" +
					"AR/0.70/ H2/2.00/ H2O/6.00/ CO/1.50/ CO2/2.00/ CH4/2.00/ ";
			ch = new ChemkinString(rxn4, commentString);
			ch.nextToken();
			reaction = new ChemkinReaction(ch, mols);
			reaction.parse();
			System.out.println(reaction.toString());

			String rxn5 = "h2o2(+M)<=>2oh(+M)                    8.850e+20   -1.230 102222.75" +"\n" +
					"AR/0.70/ H2/2.00/ H2O/6.00/ CO/1.50/ CO2/2.00/ CH4/2.00/ C2H6/3.00/ " +"\n" +
					"LOW  /  4.900e+42   -6.430 107169.93 /" +"\n" +
					"TROE/    0.84      125      2219      6882 /" +"\n" +
					"AR/0.70/ H2/2.00/ H2O/6.00/ CO/1.50/ CO2/2.00/ CH4/2.00/ ";
			ch = new ChemkinString(rxn5, commentString);
			ch.nextToken();
			reaction = new ChemkinReaction(ch, mols);
			reaction.parse();
			System.out.println(reaction.toString());
			
			String rxn6 = "h2o2(+M)<=>2oh(+M)                    3.000e+13    0.000      0.00" +"\n" +
					"" +"\n" +
					"LOW  /  9.000e+15    1.000      0.00 /" +"\n" +
					"TROE/     0.5    1e+30     1e-30        /";
			ch = new ChemkinString(rxn6, commentString);
			ch.nextToken();
			reaction = new ChemkinReaction(ch, mols);
			reaction.parse();
			System.out.println(reaction.toString());
			
			String rxn7 = " H+O2(+M) = HO2(+M)                           5.116E+12   0.440     0.00 !00 TROE - Based on M=N2 * 1.10" +"\n" +
                                        "LOW / 6.328E+19  -1.400     0.00 /" +"\n" +
                                        "TROE/ 0.5  1E-30  1E+30            /" +"\n" +
                            "O2/0.85/  H2O/11.89/ CO/1.09/ CO2/2.18/ AR/0.40/ ";
			ch = new ChemkinString(rxn7, commentString);
			ch.nextToken();
			reaction = new ChemkinReaction(ch, mols);
			reaction.parse();
			System.out.println(reaction.toString());
			
			String rxn8 = "H+O2(+M) =(+M) H+HO2 2.082E+17 -0.3835 101197.8              !<KPS: REV  1e+14 0.0 0.0>" + "\n"
			+ "LOW /   5.6650E+091  -20.540   1.2265E+05 /" + "\n"
			+ "TROE /  1.4258E-01  2.0660E+01  4.2385E+03  9.2309E+03 /" + "\n";
			ch = new ChemkinString(rxn8, commentString);
			ch.nextToken();
			reaction = new ChemkinReaction(ch, mols);
			reaction.parse();
			System.out.println(reaction.toString());

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
