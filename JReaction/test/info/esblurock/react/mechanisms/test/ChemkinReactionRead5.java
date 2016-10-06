package info.esblurock.react.mechanisms.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;
import info.esblurock.react.mechanisms.chemkin.ChemkinMoleculeList;
import info.esblurock.react.mechanisms.chemkin.ChemkinReaction;
import info.esblurock.react.mechanisms.chemkin.ChemkinString;

public class ChemkinReactionRead5 {

	@Test
	public void test() {
		try {
		System.out.println("ChemkinReactionTest()");
		ChemkinMoleculeList mols = new ChemkinMoleculeList();
		mols.put("c4h6-1", new ChemkinMolecule("c4h6-1"));
		mols.put("h", new ChemkinMolecule("h"));
		mols.put("chcch2ch2", new ChemkinMolecule("chcch2ch2"));
		
		String commentString = "!";

		String rxn8 = "c4h6-1(+M) =(+M) h+chcch2ch2 2.082E+17 -0.3835 101197.8              !<KPS: REV  1e+14 0.0 0.0>" + "\n"
		+ "LOW /   5.6650E+091  -20.540   1.2265E+05 /" + "\n"
		+ "TROE /  1.4258E-01  2.0660E+01  4.2385E+03  9.2309E+03 /" + "\n";
		ChemkinString ch = new ChemkinString(rxn8, commentString);
		ch.nextToken();
		ChemkinReaction reaction = new ChemkinReaction(ch, mols);
			reaction.parse();

			System.out.println(reaction.toString());
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
