package info.esblurock.reaction.client.panel.inputs;

import java.util.HashSet;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.client.TextToDatabase;
import info.esblurock.reaction.client.TextToDatabaseAsync;
import info.esblurock.reaction.client.callback.StandardStringReturnCallback;
import info.esblurock.reaction.client.resources.InputConstants;
import info.esblurock.reaction.client.resources.InterfaceConstants;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.upload.InputTextSource;
import info.esblurock.reaction.data.upload.TextSetUploadData;

public class SubmitSetOfInputsCheckCallback  implements AsyncCallback<String> {
	InputConstants inputConstants = GWT.create(InputConstants.class);
	InterfaceConstants interfaceConstants = GWT.create(InterfaceConstants.class);

	private DescriptionDataData description;
	private HashSet<DataInput> inputs;

	SubmitSetOfInputsCheckCallback(String dataType, DescriptionDataData description, HashSet<DataInput> inputs) {
		this.description = description;
		this.inputs = inputs;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		Window.alert("The name '" + description.getKeyword() 
			+ "' from source '" + description.getSourcekey() 
			+ "' is already in use");
	}

	@Override
	public void onSuccess(String result) {
		TextSetUploadData	data = new TextSetUploadData(description);
		for (DataInput input : inputs) {
			String typeS = input.getFileSourceType();
			String filename = input.getUploadfileText();
			String id = input.getUploadIDText();
			String textType = input.getType();
			InputTextSource source = new InputTextSource(filename, id, typeS, textType);
			data.addInputTextSource(source);
		}
		String prefix = interfaceConstants.storedObjectKey() + ":   ";
		StandardStringReturnCallback callback = new StandardStringReturnCallback(prefix);
		TextToDatabaseAsync async = TextToDatabase.Util.getInstance();
		async.storeTextSetUploadData(data, callback);
	}

}
