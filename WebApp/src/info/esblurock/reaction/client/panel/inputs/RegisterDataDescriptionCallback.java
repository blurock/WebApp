package info.esblurock.reaction.client.panel.inputs;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.constants.ModalType;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import info.esblurock.reaction.client.TextToDatabase;
import info.esblurock.reaction.client.TextToDatabaseAsync;
import info.esblurock.reaction.client.panel.description.DataDescriptionAsRows;
import info.esblurock.reaction.client.panel.description.DataDescriptionRegisteredPanel;
import info.esblurock.reaction.client.panel.transaction.TransactionService;
import info.esblurock.reaction.client.panel.transaction.TransactionServiceAsync;
import info.esblurock.reaction.data.GenerateKeywordFromDescription;
import info.esblurock.reaction.data.description.DataSetReference;
import info.esblurock.reaction.data.description.DescriptionDataData;

public class RegisterDataDescriptionCallback  implements AsyncCallback<String> {
	SetOfInputs inputs;
	
	public RegisterDataDescriptionCallback(String dataType, 
			SetOfInputs inputs) {
		this.inputs = inputs;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		inputs.findValidProcesses();
	}

	@Override
	public void onSuccess(String result) {
		inputs.askRegisterModal(result);
	}

}
