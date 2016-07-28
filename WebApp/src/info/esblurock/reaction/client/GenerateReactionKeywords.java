package info.esblurock.reaction.client;

import java.util.ArrayList;
import java.util.Collections;

public class GenerateReactionKeywords {
	String keywordBase;
	String keywordDelimitor = "#";

	public GenerateReactionKeywords(String keywordBase) {
		this.keywordBase = keywordBase;
	}

	public String getReactionFullName(String rxnEquation) {
		String key;
		if (keywordBase != null) {
			key = keywordBase + keywordDelimitor + rxnEquation;
		} else {
			key = rxnEquation;
		}
		return key;
	}

	public String getReactionName(ArrayList<String> reactants, ArrayList<String> products) {
		String reacS = getMoleculeListString(reactants);
		String prodS = getMoleculeListString(products);
		String rxnS = reacS + "=" + prodS;
		return rxnS.toLowerCase();
	}

	public String getMoleculeListString(ArrayList<String> mol) {
		Collections.sort(mol);
		StringBuilder build = new StringBuilder();
		for (String name : mol) {
			build.append(name);
			build.append("+");
		}
		String appendname = build.toString();
		String endmol = appendname.substring(0, appendname.length() - 1);
		return endmol;
	}

	public String parseOutReactionMechanismName(String rxnname) {
		String ans = rxnname;
		int pos = rxnname.lastIndexOf(keywordDelimitor);
		if (pos > 0) {
			ans = rxnname.substring(0, pos);
		}
		return ans;
	}

	private String parseOutSimpleMoleculeName(String name) {
		String delim = GenerateKeywords.delimitor;
		String simplename = name;
		int pos = 0;
		while (pos >= 0) {
			String sub = simplename.substring(pos + 1);
			simplename = sub;
			pos = simplename.indexOf(delim);
		}
		return simplename;
	}

}
