package info.esblurock.react.mechanisms.test;

import static org.junit.Assert.*;

import java.io.IOException;

import info.esblurock.react.common.ReadToString;
import info.esblurock.react.common.URLToString;
import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.react.mechanisms.chemkin.ChemkinString;

import org.junit.Test;

public class ChemkinMechanismRead3 {

	@Test
	public void test() {
		//String url = "http://web.stanford.edu/group/haiwanglab/JetSurF/JetSurF2.0/Mech_JetSurF2.0.txt";
		//String url = "http://web.eng.ucsd.edu/mae/groups/combustion/sdmech/sandiego20141004/sandiego20141004_mechCK.txt";
		//String url = "http://web.eng.ucsd.edu/mae/groups/combustion/sdmech/sandiego_nitrogen/NOx_20041209/NOXsandiego20041209.mec";
		String url = "URL https://combustion.llnl.gov/content/assets/docs/combustion/h2_v1b_mech.txt";
		String resource = "RESOURCE info/esblurock/react/resources/mechanisms/LLNLHeptane";
		ReadToString read = new ReadToString();
		try {
			//String input = read.parseFromLineSpecification(resource);
			String input = read.parseFromLineSpecification(url);
			//URLToString urlstring = new URLToString(resource);
			//String input = urlstring.toString();
			String commentChar = "!";
			ChemkinString lines = new ChemkinString(input, commentChar);
			ChemkinMechanism mechanism = new ChemkinMechanism();
			lines.nextToken();
			mechanism.parse(lines, commentChar);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(urlstring.toString());

	}

}
