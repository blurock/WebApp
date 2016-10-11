package info.esblurock.react.mechanisms.chemkin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;

public class ChemkinMoleculeList extends HashMap<String,ChemkinMolecule> {
	private static final long serialVersionUID = 1L;
	private ChemkinString lines;
	private String endS = "END";
	private String reactionS = "REACTIONS";
	private int modulocount = 5;
	
	public ChemkinMoleculeList() {
		lines = null;
	}
	public ChemkinMoleculeList(ChemkinString ls) {
		lines = ls;
	}
	
	public void parse()  throws IOException {
		String next = lines.getCurrent();
		boolean notdone = true;
		while(notdone) {
			next = next.trim();
			if(next != null) {
				if(next.trim().toUpperCase().startsWith(endS)) {
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
	public ChemkinMolecule put(String key, ChemkinMolecule molecule) {
		return super.put(key.toLowerCase(),molecule);
	}
	public ChemkinMolecule get(String key) {
		return super.get(key.toLowerCase());
	}
	public boolean containsKey(String key) {
		return super.containsKey(key.toLowerCase());
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("SPECIES\n");
		Set<String> keys = this.keySet();
		int count = 0;
		for(String key : keys) {
			if(count++ % modulocount == 0) {
				build.append("\n");
			}
			build.append(key);
			build.append(" ");
		}
		build.append("\nEND\n");
		return build.toString();
	}
}
