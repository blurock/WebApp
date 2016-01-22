package info.esblurock.react.mechanisms.chemkin;

import info.esblurock.react.common.URLToString;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadThermodynamicData {
	String commentChar = null;
	
	public ReadThermodynamicData() {
		init();
	}
	public void init() {
		commentChar = "!";
	}
	public ChemkinNASAThermodynamics parseThermoString(String mechanismS) throws IOException {
		ChemkinNASAThermodynamics thermo = new ChemkinNASAThermodynamics();
		ChemkinString tok = new ChemkinString(mechanismS,commentChar);
		tok.nextToken();
		thermo.parse(tok);
		return thermo;
	}
	public ChemkinNASAThermodynamics parseThermoFile(File mechanismF) throws IOException{
		byte[] encoded = Files.readAllBytes(Paths.get(mechanismF.toString()));
		String mechanismS = new String(encoded,"UTF-8");
		return parseThermoString(mechanismS);
	}
	public ChemkinNASAThermodynamics parseThermoURL(URL mechanismURL) throws IOException{
		URLToString urlstring = new URLToString(mechanismURL.toString());
		return parseThermoString(urlstring.toString());
	}

}
