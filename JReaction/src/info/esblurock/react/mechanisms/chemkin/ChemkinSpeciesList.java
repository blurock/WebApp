package info.esblurock.react.mechanisms.chemkin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;

public class ChemkinSpeciesList extends HashMap<String,ChemkinMolecule>  {
	private ChemkinString lines;
	private String endS = "END";
	private int modulocount = 5;
	
	
	public ChemkinSpeciesList() {
		
	}
	
	
	public void parse()  throws IOException {
		String next = lines.getCurrent();
		boolean notdone = true;
		while(notdone) {
			next = next.trim();
			if(next != null) {
				if(isEND(next)) {
					notdone = false;
				} else if(next.startsWith(lines.getCommentChar())) {
					
				} else {
					StringTokenizer tok = new StringTokenizer(next);
					while(tok.hasMoreTokens()) {
						String molS = tok.nextToken();
						this.put(molS, new ChemkinMolecule(molS));
					}
				}
			} else {
				notdone = false;
			}
			next = lines.nextToken();
		}
	}
	public boolean isEND(String line) {
		String l = line.trim().toUpperCase();
		return l.startsWith(endS);
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("SPECIES");
		Set<String> keys = this.keySet();
		int cnt = 0;
		for(String key : keys) {
			if(cnt++ % modulocount == 0) {
				build.append("\n");
			}
			build.append(key);
			build.append(" ");
		}
		build.append("\nEND");
		return build.toString();
	}
	

}
