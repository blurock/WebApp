package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.tools.ant.types.resources.Sort;

import info.esblurock.react.mechanisms.chemkin.ChemkinReaction;

public class GenerateReactionKeywords {
	String keywordBase;
	String delimitor = "#";

	public GenerateReactionKeywords(String keywordBase) {
		super();
		this.keywordBase = keywordBase;
	}
	
	public String getKeyword(ChemkinReaction reaction) {
		return keywordBase;
	}
	
	public String getKeyword(ChemkinReactionData reaction) {
		return keywordBase;
	}
	
	public String getReactionName(ArrayList<String> reactants,ArrayList<String> products) {
		String reacS = getMoleculeListString(reactants);
		String prodS = getMoleculeListString(products);
		String rxnS = reacS + "=" + prodS;
		return rxnS;
	}
	
	public String getMoleculeListString(ArrayList<String> mol) {
		Collections.sort(mol);
		StringBuilder build = new StringBuilder();
		for(String name : mol) {
			build.append(name);
			build.append("+");
		}
		String appendname = build.toString();
		String endmol = appendname.substring(0, appendname.length()-2);
		return endmol;
	}
	
}
