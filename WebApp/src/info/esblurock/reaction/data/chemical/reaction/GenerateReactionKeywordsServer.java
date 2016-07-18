package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;

import info.esblurock.reaction.client.GenerateKeywords;
import info.esblurock.reaction.client.GenerateReactionKeywords;

public class GenerateReactionKeywordsServer extends GenerateReactionKeywords {
	String keywordBase;
	String delimitor = "#";

	public GenerateReactionKeywordsServer(String keywordBase) {
		super(keywordBase);
		this.keywordBase = keywordBase;
	}
	
	public String getReactionSimpleName(ChemkinReactionData reaction) {
		ArrayList<String> simpleReactantNames = new ArrayList<String>();
		ArrayList<String> simpleProductNames = new ArrayList<String>();
		for(String fullname : reaction.getReactantKeys()) {
			String name = GenerateKeywords.moleculeNameFromMoleculeKeyword(fullname);
			simpleReactantNames.add(name);
		}
		for(String fullname : reaction.getProductKeys()) {
			String name = GenerateKeywords.moleculeNameFromMoleculeKeyword(fullname);
			simpleProductNames.add(name);
		}
		String rxn = getReactionName(simpleReactantNames,simpleProductNames);
		return rxn.toLowerCase();
	}
	
	public String getReactionFullName(ChemkinReactionData reaction) {
		String rxn = getReactionSimpleName(reaction);
		return getReactionFullName(rxn);
	}
	
}
