package info.esblurock.reaction.data.upload.types;

import javax.jdo.annotations.PersistenceCapable;

import info.esblurock.reaction.data.upload.UploadFileTransaction;

@PersistenceCapable
public class TransportFileUpload extends UploadFileTransaction {

	private static final long serialVersionUID = 1L;

	public TransportFileUpload() {
		super();
	}

	public TransportFileUpload(String user, String filename, String fileCode, String sourceType, Integer lineCount) {
		super(user, filename, fileCode, sourceType, lineCount);
	}

}