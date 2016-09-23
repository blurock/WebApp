package info.esblurock.reaction.client;



public class GenerateKeywords {
	static public String delimitor = "#";
	
/** Create a full keyword from the source and the data keyword
 * 
 * @param source: The source of the data
 * @param datakeyword: The keyword within the source
 * @return The full keyword
 */
	static public String createDataKeyword(String source,String datakeyword) {
		String name = source + delimitor + datakeyword;
		return name;
	}
/** Get the source from the full keyword
 * 
 * @param datakeyword
 * @return The source of the data
 */
	static public String sourceFromDataKeyword(String datakeyword) {
		int pos = datakeyword.indexOf(delimitor);
		String ans = "";
		if(pos > 0) {
			ans = datakeyword.substring(0, pos);
		}
		return ans;
	}
/** Get the keyword from the full keyword
 *
 * @param datakeyword
 * @return The keyword from the source
 */
	static public String keywordFromDataKeyword(String datakeyword) {
		int pos = datakeyword.indexOf(delimitor);
		String ans = "";
		if(pos > 0) {
			ans = datakeyword.substring(pos+1);
		}
		return ans;
	}

/** Generate the molecular keyword
 * 
 * @param mechname The mechanism name
 * @param molname The molecule name
 * @return The molecule keyword
 */
	static public String generateMoleculeKeyword(String mechname, String molname) {
		String key = mechname + delimitor + molname;
		return key;
	}
/** Extract the source from the molecule keyword
 * 
 * @param molname The full molecule keyword
 * @return The source
 */
	static public String sourceFromMoleculeKeyword(String molname) {
		int pos = molname.lastIndexOf(delimitor);
		String ans = "";
		if(pos > 0) {
			ans = molname.substring(0, pos);
		}
		return ans;
	}
/** Extract the molecule name from the molecule keyword
 * 
 * @param molname The full molecule keyword
 * @return The molecule name
 */
	static public String moleculeNameFromMoleculeKeyword(String molname) {
		int pos = molname.lastIndexOf(delimitor);
		String ans = "";
		if(pos > 0) {
			ans = molname.substring(pos+1);
		}
		return ans;
	}

}
