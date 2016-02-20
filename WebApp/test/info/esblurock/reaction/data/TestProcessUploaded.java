package info.esblurock.reaction.data;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;

public class TestProcessUploaded {

	@Test
	public void test() {
		String name = "Administration";
		String file = "https://combustion.llnl.gov/content/assets/docs/combustion/h2_v1a_therm.txt";
		String commentString = "!";
		ChemkinStringFromStoredFile chmekinstring = new ChemkinStringFromStoredFile(name, file, commentString);
		ChemkinMechanism mechanism = new ChemkinMechanism();
		try {
			mechanism.parse(chmekinstring, commentString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(mechanism.toString());

	}

}
