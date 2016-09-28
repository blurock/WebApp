package info.esblurock.react.mechanisms.chemkin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class ThirdBodyMolecules extends HashMap<String,ThirdBodyWeight> {
	String commentChar = "!";
	String comment;
	
	public ThirdBodyMolecules() {
	}
	
	public void parse(String text)  throws IOException {
		int pos = text.indexOf(commentChar);
		String rest;
		if(pos>0) {
			rest = text.substring(0,pos);
		} else {
			rest = text;
		}
		boolean notdone = true;

		while(notdone) {
			rest  = rest.trim();
			int pos1 = rest.indexOf('/');
			if(pos1 >= 0) { 
			int pos2 = rest.indexOf('/',pos1+1);
			if(pos2 == -1) 
				throw new IOException("Illegal ThirdBody format: " + rest);
			
			String mol = rest.substring(0,pos1);
			String weightS = rest.substring(pos1+1,pos2);
			ThirdBodyWeight weight = new ThirdBodyWeight(mol, weightS);
			this.put(mol,weight);
			
			rest = rest.substring(pos2+1);
			} else {
				notdone = false;
			}
		}
		if(this.size() == 0) {
			throw new IOException("Expected Third Body molecules, got: " + rest);
		}
	}	
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("ThirdBody:  ");
		Set<String> keys = this.keySet();
		for(String key: keys) {
			ThirdBodyWeight wieght = this.get(key);
			build.append(wieght.toString());
			build.append("  ");
		}
		
		return build.toString();
	}
	
 }
