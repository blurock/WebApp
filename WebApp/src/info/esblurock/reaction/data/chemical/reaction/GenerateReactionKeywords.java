package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;
import java.util.Collections;

import info.esblurock.reaction.data.chemical.molecule.GenerateMoleculeKeywords;

public class GenerateReactionKeywords {
	String keywordBase;
	String delimitor = "#";

	public GenerateReactionKeywords(String keywordBase) {
		super();
		this.keywordBase = keywordBase;
	}
	
	public String getKeyword(ChemkinReactionData reaction) {
		return keywordBase;
	}
	
	public String getReactionName(ArrayList<String> reactants,ArrayList<String> products) {
		String reacS = getMoleculeListString(reactants);
		String prodS = getMoleculeListString(products);
		String rxnS = reacS + "=" + prodS;
		return rxnS.toLowerCase();
	}
	
	public String getReactionFullName(String rxnEquation) {
		String key = keywordBase + delimitor + rxnEquation;
		return key;
	}
	private String parseOutSimpleMoleculeName(String name) {
		String delim = GenerateMoleculeKeywords.delimitor;
		String simplename = name;
		int pos = 0;
		while(pos >= 0) {
			String sub = simplename.substring(pos+1);
			simplename = sub;
			pos = simplename.indexOf(delim);
		}
		return simplename;
	}
	public String getReactionSimpleName(ChemkinReactionData reaction) {
		ArrayList<String> simpleReactantNames = new ArrayList<String>();
		ArrayList<String> simpleProductNames = new ArrayList<String>();
		for(String name : reaction.getReactantKeys()) {
			simpleReactantNames.add(parseOutSimpleMoleculeName(name));
		}
		for(String name : reaction.getProductKeys()) {
			simpleProductNames.add(parseOutSimpleMoleculeName(name));
		}
		String rxn = getReactionName(simpleReactantNames,simpleProductNames);
		return rxn.toLowerCase();
	}
	
	public String getReactionFullName(ChemkinReactionData reaction) {
		String rxn = getReactionSimpleName(reaction);
		return getReactionFullName(rxn);
	}
	public String getMoleculeListString(ArrayList<String> mol) {
		Collections.sort(mol);
		StringBuilder build = new StringBuilder();
		for(String name : mol) {
			build.append(name);
			build.append("+");
		}
		String appendname = build.toString();
		String endmol = appendname.substring(0, appendname.length()-1);
		return endmol;
	}
	
}
