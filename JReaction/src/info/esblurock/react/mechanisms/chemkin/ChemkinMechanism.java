package info.esblurock.react.mechanisms.chemkin;

import java.io.IOException;

public class ChemkinMechanism {
	ChemkinElementList elementList;
	ChemkinMoleculeList speciesList;
	ChemkinReactionList reactionList;

	String mechanismComment;

	String elementsLabel = "ELEMENTS";
	String speciesLabel = "SPECIES";
	String reactionsLabel = "REACTIONS";

	public ChemkinMechanism() {

	}

	public void parse(ChemkinString lines, String commentChar)
			throws IOException {
		parseComments(lines, commentChar);

		String next = lines.getCurrent().trim();
		if (next.startsWith(elementsLabel)) {
			next = lines.nextToken().trim();
			elementList = new ChemkinElementList(lines);
			elementList.parse();
			System.out.println(elementList.toString());
			next = lines.getCurrent().trim();
			if (next.startsWith(speciesLabel)) {
				speciesList = new ChemkinMoleculeList(lines);
				next = lines.nextToken();
				speciesList.parse();
				System.out.println(speciesList.toString());
				next = lines.getCurrent().trim();
				System.out.println("Next: " + next);
				if (next.startsWith(reactionsLabel)) {
					reactionList = new ChemkinReactionList();
					next = lines.nextToken();
					reactionList.parseReactions(lines, speciesList);
					System.out.println(reactionList.toString());
				} else {
					throw new IOException("Expected: " + reactionsLabel + "got '" + next + "'");
				}
			}
		}

	}

	public void parseComments(ChemkinString lines, String commentChar) {
		StringBuilder builder = new StringBuilder();
		boolean notdone = true;
		String next = lines.getCurrent();
		while (notdone) {
			next.trim();
			if (next.startsWith(commentChar)) {
				builder.append(next);
				builder.append("\n");
			} else {
				notdone = false;
			}
		}
		mechanismComment = builder.toString();
	}
}
