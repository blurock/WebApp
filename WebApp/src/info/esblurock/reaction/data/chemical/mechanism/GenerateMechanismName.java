package info.esblurock.reaction.data.chemical.mechanism;

public class GenerateMechanismName {

	public static String delimitor = "#";

	static public String createMechanismName(String source,String keyword) {
		String name = source + delimitor + keyword;
		return name;
	}
	static public String sourceFromMechanismName(String mechname) {
		int pos = mechname.indexOf(delimitor);
		String ans = "";
		if(pos > 0) {
			ans = mechname.substring(0, pos);
		}
		return ans;
	}
	static public String keywordFromMechanismName(String mechname) {
		int pos = mechname.indexOf(delimitor);
		String ans = "";
		if(pos > 0) {
			ans = mechname.substring(pos+1);
		}
		return ans;
	}

}
