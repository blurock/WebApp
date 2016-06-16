package info.esblurock.reaction.data.chemical.molecule;

public class GenerateMoleculeKeywords {
	static public String delimitor = "#";

	static public String getDataKeyword(MechanismMoleculeData molecule) {
		String key = generateKeyword(molecule.getMechanismKeyword(),molecule.getMoleculeName());
		return key;
	}
	static public String generateKeyword(String mechname, String molname) {
		String key = mechname + delimitor + molname;
		return key;
	}
	static public String sourceFromMechanismName(String molname) {
		int pos = molname.indexOf(delimitor);
		String ans = "";
		if(pos > 0) {
			ans = molname.substring(0, pos);
		}
		return ans;
	}
	static public String keywordFromMechanismName(String molname) {
		int pos = molname.indexOf(delimitor);
		String ans = "";
		if(pos > 0) {
			ans = molname.substring(pos+1);
		}
		return ans;
	}

}
