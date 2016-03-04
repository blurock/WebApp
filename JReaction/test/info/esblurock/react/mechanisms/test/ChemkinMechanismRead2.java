package info.esblurock.react.mechanisms.test;

import static org.junit.Assert.*;
import info.esblurock.react.common.ReadToString;
import info.esblurock.react.common.URLToString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

public class ChemkinMechanismRead2 {

	@Test
	public void test() {
		//String url = "URL https://combustion.llnl.gov/content/assets/docs/combustion/nc7_ver3.1_mech.txt";
		String url = "URL https://combustion.llnl.gov/content/assets/docs/combustion/h2_v1b_mech.txt";
		String resource = "RESOURCE info/esblurock/react/resources/mechanisms/LLNLHeptane";
		
			ReadToString read = new ReadToString();
			try {
				System.out.println(read.parseFromLineSpecification(url));
				
				//System.out.println(read.parseFromLineSpecification(resource));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}