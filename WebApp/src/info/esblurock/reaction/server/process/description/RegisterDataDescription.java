package info.esblurock.reaction.server.process.description;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import info.esblurock.reaction.data.DateAsString;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class RegisterDataDescription extends ProcessBase {

	static String descriptionString = "Description";
	static String oneLineDescription = "OneLine";
	static String fullDescription = "FullDescription";
	static String sourceOfData= "SourceOfData";
	static String keywordString = "Keyword";
	
	DescriptionDataData data;

	public RegisterDataDescription() {
	}

	public RegisterDataDescription(ProcessInputSpecificationsBase spec) {
		super(spec);
		DataDescriptionSpecification dataspec = (DataDescriptionSpecification) spec;
		data = dataspec.getData();
	}

	@Override
	protected String getProcessName() {
		return "RegisterDataDescription";
	}

	@Override
	protected String getProcessDescription() {
		return "Register the basic description of data object: Used to produce full keyword";
	}

	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		ArrayList<String> input = new ArrayList<String>();
		return input;
	}

	@Override
	protected void initializeOutputObjects() throws IOException {
		super.initializeOutputObjects();
		objectOutputs.add(data);
	}

	@Override
	protected ArrayList<String> getOutputTransactionObjectNames() {
		String o1 = "info.esblurock.reaction.data.description.DescriptionDataData";
		ArrayList<String> output = new ArrayList<String>();
		output.add(o1);
		return output;
	}

	@Override
	protected void createObjects() throws IOException {
		/*
		System.out.println("RegisterDataDescription: createObjects()    DescriptionDataData");
		StoreObject store = new StoreObject(user, keyword, outputSourceCode);		
		store.storeObjectRDF(descriptionString, data);
		store.storeStringRDF(oneLineDescription,data.getOnlinedescription());
		store.storeStringRDF(fullDescription,data.getFulldescription());
		store.storeStringRDF(sourceOfData,data.getSourcekey());
		Date sourcedate = data.getSourceDate();
		if(sourcedate == null) {
			sourcedate = new Date();
		}
		String sourcedateS = DateAsString.dateAsString(sourcedate);
		store.storeStringRDF(sourceOfData,sourcedateS);
		for(String key : data.getKeywords()) {
			store.storeStringRDF(keywordString, key);
		}
		store.finish();
		*/
	}

	@Override
	public void initialization() {
	}

}
