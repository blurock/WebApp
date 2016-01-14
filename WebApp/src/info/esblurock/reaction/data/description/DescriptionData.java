package info.esblurock.reaction.data.description;

import info.esblurock.reaction.server.datastore.SetsOfPropertyAttributes;

public class DescriptionData extends SetsOfPropertyAttributes {
	private static final long serialVersionUID = 1L;
	public static String keyWordKey = "keyword";
	public static String oneLineDescriptionKey = "oneline";
	public static String fullDescriptionKey = "fulldescription";
	
	public static final String data = "DataDescriptionEntity";
	
	public DescriptionData() {
		super(data);
	}
	
	public DescriptionData(String attribute, String keyword, String oneline, String full) {
		super(data);
		this.addProperty(attribute, keyword, keyWordKey);
		this.addProperty(attribute, oneline, oneLineDescriptionKey);
		this.addProperty(attribute, full, fullDescriptionKey);
	}
	public DescriptionData(String datatype, String attribute, String keyword, String oneline, String full) {
		super(datatype);
		this.addProperty(attribute, keyword, keyWordKey);
		this.addProperty(attribute, oneline, oneLineDescriptionKey);
		this.addProperty(attribute, full, fullDescriptionKey);
	}
	
}
