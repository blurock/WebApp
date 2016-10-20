package info.esblurock.reaction.data.upload.types;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;

import info.esblurock.reaction.data.upload.UploadFileTransaction;

@PersistenceCapable
@Inheritance(customStrategy = "complete-table")
public class ChemkinMechanismFileUpload extends UploadFileTransaction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChemkinMechanismFileUpload() {
		super();
	}

	public ChemkinMechanismFileUpload(String user, String filename, String fileCode, String sourceType,
			Integer lineCount) {
		super(user, filename, fileCode, sourceType, lineCount);
	}


}
