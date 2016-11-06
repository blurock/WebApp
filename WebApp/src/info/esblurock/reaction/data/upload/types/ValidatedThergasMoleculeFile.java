package info.esblurock.reaction.data.upload.types;

import javax.jdo.annotations.PersistenceCapable;

import info.esblurock.reaction.data.upload.ValidatedFileTransaction;

@PersistenceCapable
public class ValidatedThergasMoleculeFile extends ValidatedFileTransaction {
	public ValidatedThergasMoleculeFile() {
		super();
	}
	public ValidatedThergasMoleculeFile(String user, String filename, String fileCode, String sourceType) {
		super(user, filename, fileCode, sourceType);
	}
}
