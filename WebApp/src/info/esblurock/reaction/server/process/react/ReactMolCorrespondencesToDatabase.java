package info.esblurock.reaction.server.process.react;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.data.transaction.reaction.ReactMolCorrespondencesToDatabaseTransaction;
import info.esblurock.reaction.data.upload.types.ReactMolCorrespondencesFileUpload;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class ReactMolCorrespondencesToDatabase  extends ProcessBase {
	String validateS;
	String toDatabaseS;
	String uploadS;
	ReactMolCorrespondencesFileUpload upload;
	ReactMolCorrespondencesToDatabaseTransaction moltransaction;

	public ReactMolCorrespondencesToDatabase() {
	}
	public ReactMolCorrespondencesToDatabase(ProcessInputSpecificationsBase input) {
		super(input);
	}
	@Override
	protected String getProcessDescription() {
		return "Store reaction molecules correspondences to database";
	}
	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		ArrayList<String> input = new ArrayList<String>();
		input.add(validateS);
		input.add(uploadS);
		return input;
	}

	@Override
	protected ArrayList<String> getOutputTransactionObjectNames() {
		ArrayList<String> output = new ArrayList<String>();
		output.add(toDatabaseS);
		return output;
	}
	@Override
	protected void initializeOutputObjects() throws IOException {
		super.initializeOutputObjects();
		moltransaction = new ReactMolCorrespondencesToDatabaseTransaction(user, outputSourceCode, keyword,0);
		objectOutputs.add(moltransaction);
	}

	@Override
	public void initialization() {
		uploadS = "info.esblurock.reaction.data.upload.types.ReactMolCorrespondencesFileUpload";
		validateS = "info.esblurock.reaction.data.upload.types.ValidatedReactMolCorrespondencesFile";
		toDatabaseS = "info.esblurock.reaction.data.transaction.reaction.ReactMolCorrespondencesToDatabaseTransaction";
	}

	@Override
	protected void createObjects() throws IOException {
		upload = (ReactMolCorrespondencesFileUpload) getInputSource(uploadS);
		
	}
	@Override
	protected String getProcessName() {
		return "ReactMolCorrespondencesToDatabase";
	}
}
