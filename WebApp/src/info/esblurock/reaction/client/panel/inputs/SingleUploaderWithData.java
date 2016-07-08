package info.esblurock.reaction.client.panel.inputs;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus;
import gwtupload.client.SingleUploader;
import info.esblurock.reaction.client.panel.description.DataDescription;
import info.esblurock.reaction.data.description.DescriptionDataData;

public class SingleUploaderWithData extends SingleUploader {

	DataDescription description;
	
	
	SingleUploaderWithData(DataDescription description, FileInputType fileinputtype, IUploadStatus ustatus) {
		super(fileinputtype, ustatus);
		this.description = description;
	}
	
	String getKeyWord() {
		return description.createObjectKeyword();
	}
}
