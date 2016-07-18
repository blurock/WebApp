package info.esblurock.reaction.data.chemical.molecule;

import info.esblurock.reaction.client.GenerateKeywords;

public class GenerateMoleculeKeywords {
	static public String delimitor = "#";

	static public String getDataKeyword(MechanismMoleculeData molecule) {
		String key = GenerateKeywords.generateMoleculeKeyword(molecule.getMechanismKeyword(),molecule.getMoleculeName());
		return key;
	}

}
