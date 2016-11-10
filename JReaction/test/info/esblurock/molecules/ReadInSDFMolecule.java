package info.esblurock.molecules;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.junit.Test;

import com.ibm.icu.util.StringTokenizer;

import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.react.mechanisms.BRS.BRSMechanism;
import info.esblurock.react.parse.nlp.utilities.ReadTextFileToString;

public class ReadInSDFMolecule {

	@Test
	public void test() {
        File sampleFile = new File("test/resource/moleculesdf.sdf");
        ReadTextFileToString read = new ReadTextFileToString();
        read.read(sampleFile);
        //System.out.println(read.outputString);
        
        BRSMechanism mech = new BRSMechanism();
        try {
			mech.readMolecules(sampleFile);
			Set<String> keys = mech.Molecules.keySet();
			System.out.println(keys);
			for(String key: keys) {
				ReactMolecule molecule = (ReactMolecule) mech.Molecules.get(key);
				System.out.println("'" + key + "' -------------------------------------");
				System.out.println(molecule.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
