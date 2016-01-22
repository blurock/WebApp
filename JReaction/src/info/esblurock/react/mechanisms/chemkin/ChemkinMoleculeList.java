package info.esblurock.react.mechanisms.chemkin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;

public class ChemkinMoleculeList extends HashMap<String,ChemkinMolecule> {
	private ChemkinString lines;
	private String endS = "END";
	private String reactionS = "REACTIONS";
	private int modulocount = 5;
	
	public ChemkinMoleculeList(ChemkinString ls) {
		lines = ls;
	}
	
	public void parse()  throws IOException {
		String next = lines.getCurrent();
		boolean notdone = true;
		while(notdone) {
			next = next.trim();
			if(next != null) {
				if(next.startsWith(endS)) {
					notdone = false;
					next = lines.nextToken();
				} else if(next.startsWith(reactionS) ) {
					notdone = false;
				} else if(next.startsWith(lines.getCommentChar())) {
					next = lines.nextToken();
				} else {
					StringTokenizer tok = new StringTokenizer(next);
					while(tok.hasMoreTokens()) {
						String molS = tok.nextToken();
						this.put(molS, new ChemkinMolecule(molS));
					}
					next = lines.nextToken();
				}
			} else {
				notdone = false;
			}
		}
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("SPECIES");
		Set<String> keys = this.keySet();
		int count = 0;
		for(String key : keys) {
			if(count++ % modulocount == 0) {
				build.append("\n");
			}
			build.append(key);
			build.append(" ");
		}
		build.append("\nEND");
		return build.toString();
	}
}
