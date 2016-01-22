package info.esblurock.react.mechanisms.test;

import static org.junit.Assert.*;
import info.esblurock.react.common.ReadFileToString;
import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.react.mechanisms.chemkin.ChemkinString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class ChemkinReactionReadTest4 {

	@Test
	public void test() {
		//String filename = "info.esblurock.react.resources.mechanisms.LLNLHeptane";
		String filename = "/Users/edwardblurock/Box Sync/ScientificNotebook/Project/JReaction/src/info/esblurock/react/resources/mechanisms/LLNLHeptane";
		
		
		String mechanismS;
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(filename));
			mechanismS = new String(encoded,"UTF-8");
			
			
			String commentChar = "!";
			ChemkinMechanism chemkin = new ChemkinMechanism();
			ChemkinString tok = new ChemkinString(mechanismS,commentChar);
			tok.nextToken();
			chemkin.parse(tok, commentChar);

			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
