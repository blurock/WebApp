package info.esblurock.reaction.data.upload.types;

import javax.jdo.annotations.PersistenceCapable;

import info.esblurock.reaction.data.upload.ValidatedFileTransaction;

@PersistenceCapable
public class ValidatedChemkinMechanismFile extends ValidatedFileTransaction {

	public ValidatedChemkinMechanismFile() {
		super();
	}

	public ValidatedChemkinMechanismFile(String user, String filename, String fileCode, String sourceType) {
		super(user, filename, fileCode, sourceType);
	}

}
