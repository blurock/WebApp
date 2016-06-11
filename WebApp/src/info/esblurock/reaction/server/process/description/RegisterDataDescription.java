package info.esblurock.reaction.server.process.description;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class RegisterDataDescription extends ProcessBase {

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
		return new ArrayList<String>();
	}

	@Override
	protected void initializeOutputObjects() {
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
	}

	@Override
	public void initialization() {
	}

}
