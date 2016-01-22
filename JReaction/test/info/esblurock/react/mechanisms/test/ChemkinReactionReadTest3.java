package info.esblurock.react.mechanisms.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.react.mechanisms.chemkin.ChemkinReaction;
import info.esblurock.react.mechanisms.chemkin.ChemkinString;

import org.junit.Test;

public class ChemkinReactionReadTest3 {

	@Test
	public void test() {

		System.out.println("ChemkinReactionTest()");
		ArrayList<ChemkinMolecule> mols = new ArrayList<ChemkinMolecule>(8);
		mols.add(0, new ChemkinMolecule("co"));
		mols.add(1, new ChemkinMolecule("h2o2"));
		mols.add(2, new ChemkinMolecule("co2"));
		mols.add(3, new ChemkinMolecule("oh"));
		mols.add(4, new ChemkinMolecule("o2"));
		mols.add(5, new ChemkinMolecule("h"));
		mols.add(6, new ChemkinMolecule("ho2"));
		mols.add(7, new ChemkinMolecule("h2"));
		mols.add(6, new ChemkinMolecule("h2o"));
		mols.add(7, new ChemkinMolecule("T-CH2"));
		mols.add(8, new ChemkinMolecule("c2h2"));

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

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
