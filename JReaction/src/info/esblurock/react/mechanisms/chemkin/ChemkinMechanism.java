package info.esblurock.react.mechanisms.chemkin;

import java.io.IOException;

public class ChemkinMechanism {
	
	String mechanismName;
	
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
		if (elementStart(next)) {
			System.out.println("Process ELEMENTS");
			next = lines.nextToken().trim();
			elementList = new ChemkinElementList(lines);
			elementList.parse();
			System.out.println(elementList.toString());
			next = lines.getCurrent().trim();
			if (speciesStart(next)) {
				System.out.println("Process SPECIES");
				speciesList = new ChemkinMoleculeList(lines);
				next = lines.nextToken();
				speciesList.parse();
				System.out.println(speciesList.toString());
				next = lines.getCurrent();
				System.out.println("Next: " + next);
				if (reactionStart(next)) {
					System.out.println("Process REACTIONS");
					reactionList = new ChemkinReactionList();
					next = lines.nextToken();
					reactionList.parseReactions(lines, speciesList);
					System.out.println(reactionList.toString());
				} else {
					throw new IOException("Expected: " + reactionsLabel + "got '" + next + "'");
				}
			} else { 
				throw new IOException("Expected: " + speciesLabel + "got '" + next + "'");
			}
		} else {
			throw new IOException("Expected: " + elementsLabel + "got '" + next + "'");
		}

	}

	private boolean elementStart(String line) {
		String l = line.trim().toUpperCase();
		return l.startsWith(elementsLabel);
	}
	private boolean speciesStart(String line) {
		String l = line.trim().toUpperCase();
		return l.startsWith(speciesLabel);
	}
	private boolean reactionStart(String line) {
		String l = line.trim().toUpperCase();
		return l.startsWith(reactionsLabel);
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
				next = lines.nextToken();
			} else {
				notdone = false;
			}
		}
		mechanismComment = builder.toString();
	}
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		
		build.append(elementList.toString());
		build.append(speciesList.toString());
		build.append(reactionList.toString());
		
		return build.toString();
	}

	public ChemkinElementList getElementList() {
		return elementList;
	}

	public ChemkinMoleculeList getSpeciesList() {
		return speciesList;
	}

	public ChemkinReactionList getReactionList() {
		return reactionList;
	}
	
}
