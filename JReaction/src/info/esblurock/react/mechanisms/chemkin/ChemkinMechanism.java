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
	String thermoLabel = "THERMO";
	String reactionsLabel = "REACTIONS";
	String endLabel = "END";

	public ChemkinMechanism() {

	}

	public void parse(ChemkinString lines, String commentChar) throws IOException {
		//parseComments(lines, commentChar);
		mechanismComment = lines.skipOverComments();
		String next = currentNonBlank(lines);
		if (elementStart(next)) {
			System.out.println("Process ELEMENTS");
			//next = lines.nextToken().trim();
			elementList = new ChemkinElementList(lines);
			elementList.parse();
			System.out.println(elementList.toString());
			String commentElements = lines.skipOverComments();
			next = currentNonBlank(lines);
			if (speciesStart(next)) {
				System.out.println("Process SPECIES");
				speciesList = new ChemkinMoleculeList(lines);
				next = nextNonBlank(lines);
				speciesList.parse();
				System.out.println(speciesList.toString());
				String commentSpecies = lines.skipOverComments();
				next = currentNonBlank(lines);
				System.out.println("Next: " + next);
				if (thermoStart(next)) {
					while(!isEnd(next)) {
						next = lines.nextToken();
					}
					next = nextNonBlank(lines);
				} 
				String commentReactions = lines.skipOverComments();
				next = currentNonBlank(lines);
				if (reactionStart(next)) {
					System.out.println("Process REACTIONS");
					reactionList = new ChemkinReactionList();
					next = nextNonBlank(lines);
					reactionList.parseReactions(lines, speciesList);
					System.out.println(reactionList.toString());
				} else {
					throw new IOException("Expected: " + reactionsLabel + " got '" + next + "'");
				}

			} else {
				throw new IOException("Expected: " + speciesLabel + " got '" + next + "'");
			}
		} else {
			throw new IOException("Expected: " + elementsLabel + " got '" + next + "'");
		}

	}

	private String currentNonBlank(ChemkinString lines) {
		String next = lines.getCurrent().trim();
		while(next.length() == 0) {
			next = lines.nextToken().trim();
		}
		return next;
	}
	private String nextNonBlank(ChemkinString lines) {
		lines.nextToken();
		return currentNonBlank(lines);
	}
	
	private boolean isEnd(String line) {
		String l = line.trim().toUpperCase();
		return l.startsWith(endLabel);
	}

	private boolean elementStart(String line) {
		String l = line.trim().toUpperCase();
		return l.startsWith(elementsLabel);
	}

	private boolean speciesStart(String line) {
		String l = line.trim().toUpperCase();
		return l.startsWith(speciesLabel);
	}

	private boolean thermoStart(String line) {
		String l = line.trim().toUpperCase();
		return l.startsWith(thermoLabel);
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
