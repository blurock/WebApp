package info.esblurock.reaction.data.upload.types;

import javax.jdo.annotations.PersistenceCapable;

import info.esblurock.reaction.data.upload.ValidatedFileTransaction;

@PersistenceCapable
public class ValidatedReactMolCorrespondencesFile extends ValidatedFileTransaction {
	public ValidatedReactMolCorrespondencesFile() {
		
	}
	public ValidatedReactMolCorrespondencesFile(String user, String filename, String fileCode, String sourceType) {
		super(user, filename, fileCode, sourceType);
	}
}
