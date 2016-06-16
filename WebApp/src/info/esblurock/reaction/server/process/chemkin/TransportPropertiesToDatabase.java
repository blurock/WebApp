package info.esblurock.reaction.server.process.chemkin;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.data.StoreDatabaseObject;
import info.esblurock.reaction.data.chemical.transport.ListOfTransportProperties;
import info.esblurock.reaction.data.transaction.chemkin.TransportPropertiesToDatabaseTransaction;
import info.esblurock.reaction.data.upload.types.TransportFileUpload;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;
import info.esblurock.reaction.data.chemical.transport.SpeciesTransportProperty;

public class TransportPropertiesToDatabase extends ProcessBase {
	String uploadS;
	String toDatabaseS;	
	TransportFileUpload upload;
	InputStreamToLineDatabase input;
	TransportPropertiesToDatabaseTransaction transportDatabase;

	public TransportPropertiesToDatabase() {
		super();
	}

	public TransportPropertiesToDatabase(ProcessInputSpecificationsBase input) {
		super(input);
	}

	@Override
	public void initialization() {
		uploadS     = "info.esblurock.reaction.data.upload.types.TransportFileUpload";
		toDatabaseS = "info.esblurock.reaction.data.transaction.chemkin.TransportPropertiesToDatabaseTransaction";
	}

	@Override
	protected String getProcessName() {
		return "TransportPropertiesToDatabase";
	}

	@Override
	protected String getProcessDescription() {
		return "Store Transport properties to database";
	}

	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		ArrayList<String> input = new ArrayList<String>();
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
	protected void initializeOutputObjects() {
		super.initializeOutputObjects();
		transportDatabase = (TransportPropertiesToDatabaseTransaction)
				 new TransportPropertiesToDatabaseTransaction(user, outputSourceCode, keyword, 0);
		objectOutputs.add(transportDatabase);
	}

	@Override
	protected void createObjects() throws IOException {
		upload = (TransportFileUpload) getInputSource(uploadS);
		String commentString = "!";
		StoreDatabaseObject store = new StoreDatabaseObject();
		
		ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(upload, commentString);
		ListOfTransportProperties transportset = new ListOfTransportProperties();
		transportset.parse(keyword, chemkinstring);
		
		int count = 0;
		for(SpeciesTransportProperty transport : transportset) {
			store.store(transport);
			count++;
		}
		store.flushStore();
		transportDatabase.setTransportCount(new Integer(count));
	}
}
