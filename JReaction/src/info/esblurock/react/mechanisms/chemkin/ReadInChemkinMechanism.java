package info.esblurock.react.mechanisms.chemkin;

import info.esblurock.react.common.URLToString;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadInChemkinMechanism {
	String commentChar = null;
	
	public ReadInChemkinMechanism() {
		init();
	}
	public void init() {
		commentChar = "!";
	}
	public ChemkinMechanism parseMechanismString(String mechanismS) throws IOException {
		ChemkinMechanism mechanism = new ChemkinMechanism();
		ChemkinString tok = new ChemkinString(mechanismS,commentChar);
		tok.nextToken();
		mechanism.parse(tok, commentChar);
		return mechanism;
	}
	public ChemkinMechanism parseMechanismFile(File mechanismF) throws IOException{
		byte[] encoded = Files.readAllBytes(Paths.get(mechanismF.toString()));
		String mechanismS = new String(encoded,"UTF-8");
		return parseMechanismString(mechanismS);
	}
	public ChemkinMechanism parseMechanismURL(URL mechanismURL) throws IOException{
		URLToString urlstring = new URLToString(mechanismURL.toString());
		return parseMechanismString(urlstring.toString());
	}
	
	

}
