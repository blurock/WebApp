package info.esblurock.react.mechanisms.test;

import static org.junit.Assert.*;

import java.io.IOException;

import info.esblurock.react.mechanisms.chemkin.ChemkinMoleculeList;
import info.esblurock.react.mechanisms.chemkin.ChemkinString;

import org.junit.Test;

public class MoleculeListTest {

	@Test
	public void test() {
		String mol1 = "H              H2             O              O2             OH"
				+ "\n"
				+ "H2O            N2             HO2            H2O2           AR"
				+ "\n"
				+ "CO             CO2            CH2O           HCO            HO2CHO"
				+ "\n"
				+ "O2CHO          HOCHO          OCHO           HOCH2O2H       HOCH2O2"
				+ "\n"
				+ "OCH2O2H        HOCH2O         CH3OH          CH2OH          CH3O"
				+ "\n"
				+ "CH3O2H         CH3O2          CH4            CH3            CH2"
				+ "\n"
				+ "CH2(S)         C              CH             C2H6           C2H5"
				+ "\n" + "END";
		try {
			ChemkinString lines = new ChemkinString(mol1,"!");
			ChemkinMoleculeList mollist = new ChemkinMoleculeList(lines);
			lines.nextToken();
			mollist.parse();
			System.out.println(mollist.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
