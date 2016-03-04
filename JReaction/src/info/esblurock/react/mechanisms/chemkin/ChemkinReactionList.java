package info.esblurock.react.mechanisms.chemkin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ChemkinReactionList  extends ArrayList<ChemkinReaction> {
	boolean duplicatesAllowed = true;
	private String commentString = "!";

	public ChemkinReactionList() {
	}

	public boolean parseReactions(ChemkinString lines,
			ChemkinMoleculeList molecules)
			throws IOException {
		System.out.println("Current at parse begin: " + lines.getCurrent());
		boolean done = false;
		String next = null;
		while (!done) {
			if(lines.getCurrent() == null) 
				throw new IOException("Reaction with no END statement");
			else {
				next = lines.getCurrent().trim();
			}
			System.out.println("Current: " + next);
			while(next.startsWith(commentString)) {
				next = lines.nextToken();
				if(next == null)
					throw new IOException("End of reactions encountered unexpectedly");
			}
			System.out.println("Next Reaction------------------------------");
			System.out.println(next);
						
			if (next.trim().toUpperCase().startsWith("END")) {
				System.out.println("END of Reactions");
				done = true;
			} else {
				ChemkinReaction rxn = new ChemkinReaction(lines, molecules);
				rxn.setComment(commentString);
				next = rxn.parse();
				if (next.trim().toUpperCase().startsWith("DUP")) {
					if (duplicatesAllowed) {
						rxn.setMarkedAsDuplicate(true);
					}
					next = lines.nextToken();
				}
				this.add(rxn);
			}
		}
		return done;
	}
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("\nREACTIONS=================================================\n");
		int cnt = 0;
		for(ChemkinReaction rxn : this) {
			build.append("Count: " + cnt++);
			build.append("\n");
			build.append(rxn.toString());
			build.append("\n______________________________________________\n");
		}
		return build.toString();
	}
}
