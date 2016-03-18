package info.esblurock.reaction.data.chemical.molecule;

public class GenerateMoleculeKeywords {
	static public String delimitor = "#";
	String keywordBase;

	public GenerateMoleculeKeywords(String keywordBase) {
		this.keywordBase = keywordBase;
	}
	public String getDataKeyword(MechanismMoleculeData molecule) {
		String key = CreateMechanismMoleculeData.createMoleculeKey(molecule.getMechanismKeyword(), molecule.getMoleculeName());
		return key;
	}
}
