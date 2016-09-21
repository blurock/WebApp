package info.esblurock.reaction.server.process.description;

import info.esblurock.reaction.data.GenerateKeywordFromDescription;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class DataDescriptionSpecification extends ProcessInputSpecificationsBase {

	DescriptionDataData data;
	
	public DataDescriptionSpecification(DescriptionDataData data,  String userName, String sourceCode) {
		super(userName, GenerateKeywordFromDescription.createKeyword(data), sourceCode);
		this.data = data;
	}

	public DescriptionDataData getData() {
		return data;
	}

}
