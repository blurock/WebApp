package info.esblurock.react.data;

public class DescriptionData extends SetOfPropertyAttributes {
	public static String keyWordKey;
	public static String oneLineDescriptionKey;
	public static String fullDescriptionKey;
	
	public DescriptionData() {
	}
	
	public DescriptionData(String attribute, String keyword, String oneline, String full) {
		this.addProperty(attribute, keyword, keyWordKey);
		this.addProperty(attribute, oneline, oneLineDescriptionKey);
		this.addProperty(attribute, full, fullDescriptionKey);
	}
}
