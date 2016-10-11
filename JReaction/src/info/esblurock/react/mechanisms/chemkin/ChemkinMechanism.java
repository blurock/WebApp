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
		String next = lines.currentNonBlank();
		System.out.println("ChemkinMechanism::parse" + next );
		if (elementStart(next,lines)) {
			System.out.println("Process ELEMENTS");
			//next = lines.nextToken().trim();
			elementList = new ChemkinElementList(lines);
			elementList.parse();
			System.out.println(elementList.toString());
			//String commentElements = lines.skipOverComments();
			lines.skipOverComments();
			next = lines.currentNonBlank();
			if (speciesStart(next)) {
				System.out.println("Process SPECIES");
				speciesList = new ChemkinMoleculeList(lines);
				next = lines.nextNonBlank();
				speciesList.parse();
				System.out.println(speciesList.toString());
				//String commentSpecies = lines.skipOverComments();
				lines.skipOverComments();
				next = lines.currentNonBlank();
				if (thermoStart(next)) {
					while(!isEnd(next)) {
						next = lines.nextToken();
					}
					next = lines.nextNonBlank();
				} 
				//String commentReactions = lines.skipOverComments();
				lines.skipOverComments();
				next = lines.currentNonBlank();
				if (reactionStart(next)) {
					System.out.println("Process REACTIONS");
					reactionList = new ChemkinReactionList();
					next = lines.nextNonBlank();
					reactionList.parseReactions(lines, speciesList);
					System.out.println("Processed: " + reactionList.size() + "reactions");
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
/*
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
*/
	private boolean isEnd(String line) {
		String l = line.trim().toUpperCase();
		return l.startsWith(endLabel);
	}

	private boolean elementStart(String line,ChemkinString lines) {
		String l = line.trim().toUpperCase();
		boolean ans = true;
		int count = 0;
		while(!l.startsWith(elementsLabel) && count < 5) {
			lines.skipOverComments();
			line = lines.nextNonBlank();
			l = line.trim().toUpperCase();
			count++;
		}
		return ans;
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
