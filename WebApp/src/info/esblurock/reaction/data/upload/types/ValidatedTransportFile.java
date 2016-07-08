package info.esblurock.reaction.data.upload.types;

import javax.jdo.annotations.PersistenceCapable;

import info.esblurock.reaction.data.upload.ValidatedFileTransaction;

@PersistenceCapable
public class ValidatedTransportFile extends ValidatedFileTransaction {

	public ValidatedTransportFile() {
		super();
	}

	public ValidatedTransportFile(String user, String filename, String fileCode, String sourceType) {
		super(user, filename, fileCode, sourceType);
	}
}
