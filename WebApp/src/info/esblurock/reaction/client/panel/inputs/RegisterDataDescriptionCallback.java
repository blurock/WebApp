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
	//InputConstants inputConstants = GWT.create(InputConstants.class);
	//InterfaceConstants interfaceConstants = GWT.create(InterfaceConstants.class);

	private DescriptionDataData description;
	MaterialModalContent modalcontent;
	MaterialModal modal;
	ArrayList<DataSetReference> referenceList;
	
	public RegisterDataDescriptionCallback(String dataType, 
			DescriptionDataData description, ArrayList<DataSetReference> reflist,
			MaterialModal modal,MaterialModalContent modalcontent) {
		this.description = description;
		this.modalcontent = modalcontent;
		this.modal = modal;
		this.referenceList = reflist;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		String keyword = GenerateKeywordFromDescription.createKeyword(description);
		TransactionServiceAsync findprocess = TransactionService.Util.getInstance();
		SetUpProcessesCallback callback = new SetUpProcessesCallback(keyword,modal,modalcontent);
		findprocess.findValidProcessing(keyword, callback);
	}

	@Override
	public void onSuccess(String result) {
		Window.alert("RegisterDataDescriptionCallback:onSuccess");
		DataDescriptionAsRows panel = new DataDescriptionAsRows(result,description);
		modalcontent.clear();
		modalcontent.add(panel);
		modal.openModal();
		TextToDatabaseAsync async = TextToDatabase.Util.getInstance();
		SuccessfulRegistrationCallback callback = new SuccessfulRegistrationCallback();
		async.registerDataInputDescription(description,referenceList,callback);
	}

}
