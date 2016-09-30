package info.esblurock.react.mechanisms.chemkin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.StringTokenizer;

public class ChemkinElementList extends HashMap<String, ChemkinElement> {
	private ChemkinString lines;
	private String endS = "END";
	private String elementsS = "ELEMENTS";
	private int modulocount = 5;

	public ChemkinElementList(ChemkinString lines) {
		this.lines = lines;
	}

	public void parse() throws IOException {
		lines.skipOverComments();
		String next = lines.nextNonBlank();
		boolean notdone = true;
		while (notdone) {
			next = next.trim();
			if (next != null) {
				if (isEND(next.toUpperCase())) {
					notdone = false;
				} else if (next.startsWith(lines.getCommentChar())) {

				} else {
					StringTokenizer tok = new StringTokenizer(next);
					notdone = true;
					while (tok.hasMoreTokens() && notdone) {
						String molS = tok.nextToken();
						if (isEND(molS)) {
							notdone = false;
						}
						if (!isELEMENTS(molS))
							this.put(molS, new ChemkinElement(molS));
					}
				}
			} else {
				notdone = false;
			}
			lines.nextNonBlank();
			lines.skipOverComments();
			next = lines.currentNonBlank();
		}
	}

	public boolean isEND(String line) {
		String l = line.trim().toUpperCase();
		return l.startsWith(endS);
	}

	public boolean isELEMENTS(String line) {
		String l = line.trim().toUpperCase();
		return l.startsWith(elementsS);
	}

	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("ELEMENTS\n");
		Set<String> keys = this.keySet();
		int cnt = 0;
		for (String key : keys) {
			if (cnt++ % modulocount == 0) {
				build.append("\n");
			}
			build.append(key);
			build.append(" ");
		}
		build.append("\nEND\n");
		return build.toString();
	}

}
