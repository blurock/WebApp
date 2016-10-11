package info.esblurock.reaction.server.process.description;

import java.util.ArrayList;

import info.esblurock.reaction.data.description.DataSetReference;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class DataSetReferencesSpecifications extends ProcessInputSpecificationsBase {

	ArrayList<DataSetReference> referencelist;
	
	public DataSetReferencesSpecifications(ArrayList<DataSetReference> referencelist,
			String keyword, String userName, String sourceCode) {
		super(userName, keyword, sourceCode);
		this.referencelist = referencelist;
	}

	public ArrayList<DataSetReference> getReferences() {
		return referencelist;
	}
}
