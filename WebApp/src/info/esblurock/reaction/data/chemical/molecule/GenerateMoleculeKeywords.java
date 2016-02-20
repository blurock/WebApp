package info.esblurock.reaction.data.chemical.molecule;

import info.esblurock.react.mechanisms.chemkin.ChemkinMolecule;

public class GenerateMoleculeKeywords {
	String delimitor = "#";
	String keywordBase;

	public GenerateMoleculeKeywords(String keywordBase) {
		this.keywordBase = keywordBase;
	}
	
	public String getKeyword(ChemkinMolecule molecule) {
		String key = keywordBase + delimitor + molecule.getLabel();
		return key;
	}
}
