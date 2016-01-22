package info.esblurock.react.mechanisms.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.util.StringTokenizer;

import info.esblurock.info.react.data.molecules.isomers.Isomer;
import info.esblurock.react.mechanisms.chemkin.ChemkinNASAThermodynamics;
import info.esblurock.react.mechanisms.chemkin.ReadThermodynamicData;

import org.junit.Test;

import thermo.data.benson.NASAPolynomial;

public class ChemkinThermoTest1 {

	@Test
	public void test() {

		String mechanismS = "https://combustion.llnl.gov/content/assets/docs/combustion/n_heptane_v3.1_therm.dat.txt";
		try {
			/*			String nasaS = "H                 120186H   1               G  0300.00   5000.00  1000.00      1"
					+ "\n"
					+ "0.02500000E+02 0.00000000E+00 0.00000000E+00 0.00000000E+00 0.00000000E+00    2"
					+ "\n"
					+ "0.02547163E+06-0.04601176E+01 0.02500000E+02 0.00000000E+00 0.00000000E+00    3"
					+ "\n"
					+ "0.00000000E+00 0.00000000E+00 0.02547163E+06-0.04601176E+01                   4"
					+ "\n";

			StringTokenizer tok = new StringTokenizer(nasaS,"\n");
			System.out.println("Count Tokens: " + tok.countTokens());
			ChemkinNASAPolynomial nasa = new ChemkinNASAPolynomial();
			nasa.parse(nasaS);
			System.out.println(nasa.toString());
*/
			URL mechanismURL = new URL(mechanismS);
			ReadThermodynamicData read = new ReadThermodynamicData();
			ChemkinNASAThermodynamics thermo = read
					.parseThermoURL(mechanismURL);
			System.out.println(thermo.toString());
			for(NASAPolynomial nasa : thermo.getNASAthermo()) {
				Isomer isomer = new Isomer(nasa);
				System.out.println(nasa.getName() + ": " + isomer.getName());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
