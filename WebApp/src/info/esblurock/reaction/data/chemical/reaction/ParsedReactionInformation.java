package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;

import info.esblurock.reaction.parse.objects.ParsedInput;

public class ParsedReactionInformation extends ParsedInput {
	ArrayList<String> mechanismNames;
	ArrayList<String> forwardReactions;
	ArrayList<String> reverseReactions;
	ArrayList<String> plusDelimitedSpeciesList;

	ParsedReactionInformation() {
		super();
		mechanismNames = new ArrayList<String>();
		forwardReactions = new ArrayList<String>();
		reverseReactions = new ArrayList<String>();
		plusDelimitedSpeciesList = new ArrayList<String>();
		tokenCount = 0;
	}
	public void addMechanismName(String mechanismS) {
		mechanismNames.add(mechanismS);
		tokenCount++;
	}

	public void addReverseReaction(String reactionS) {
		reverseReactions.add(reactionS);
		tokenCount++;
	}

	public void addForwardReaction(String reactionS) {
		forwardReactions.add(reactionS);
		tokenCount++;
	}
	public void addPlusList(String reactionS) {
		plusDelimitedSpeciesList.add(reactionS);
		tokenCount++;
	}
	public ArrayList<String> getMechanismNames() {
		return mechanismNames;
	}

	public ArrayList<String> getForwardReactions() {
		return forwardReactions;
	}

	public ArrayList<String> getReverseReactions() {
		return reverseReactions;
	}

	public ArrayList<String> getPlusDelimitedSpeciesList() {
		return plusDelimitedSpeciesList;
	}

	public String toString() {
		StringBuilder build = new StringBuilder();
		String keys = super.toString();
		if (keys != null) {
			build.append(keys + "\n");
		}
		if (mechanismNames.size() > 0) {
			build.append("MechanismNames: " + mechanismNames + "\n");
		}
		if (forwardReactions.size() > 0) {
			build.append("Forward Reactions:\n");
			for (String rxn : forwardReactions) {
				build.append(rxn + "\n");
			}
		}
		if (reverseReactions.size() > 0) {
			build.append("Reverse Reactions:\n");
			for (String rxn : reverseReactions) {
				build.append(rxn + "\n");
			}
		}
		if (plusDelimitedSpeciesList.size() > 0) {
			build.append("Reactant or Product list:\n");
			for (String rxn : plusDelimitedSpeciesList) {
				build.append(rxn + "\n");
			}
		}
		return build.toString();
	}
}
