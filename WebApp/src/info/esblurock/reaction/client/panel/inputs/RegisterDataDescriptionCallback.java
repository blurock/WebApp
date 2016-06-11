package info.esblurock.reaction.client.panel.inputs;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModal.TYPE;
import info.esblurock.reaction.client.TextToDatabase;
import info.esblurock.reaction.client.TextToDatabaseAsync;
import info.esblurock.reaction.client.panel.description.DataDescriptionAsRows;
import info.esblurock.reaction.client.panel.description.DataDescriptionRegisteredPanel;
import info.esblurock.reaction.client.panel.transaction.TransactionService;
import info.esblurock.reaction.client.panel.transaction.TransactionServiceAsync;
import info.esblurock.reaction.data.GenerateKeywordFromDescription;
import info.esblurock.reaction.data.description.DescriptionDataData;

public class RegisterDataDescriptionCallback  implements AsyncCallback<String> {
	//InputConstants inputConstants = GWT.create(InputConstants.class);
	//InterfaceConstants interfaceConstants = GWT.create(InterfaceConstants.class);

	private DescriptionDataData description;

	RegisterDataDescriptionCallback(String dataType, DescriptionDataData description) {
		this.description = description;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		Window.alert("already exists");
		String keyword = GenerateKeywordFromDescription.createKeyword(description);
		//DataDescriptionAsRows panel = new DataDescriptionAsRows(keyword,description);
		//String title = keyword + ":  Registered";
		//DataDescriptionRegisteredPanel popup = new DataDescriptionRegisteredPanel(title,panel);
		//MaterialModal.showModal(popup, TYPE.FIXED_FOOTER);
		TransactionServiceAsync findprocess = TransactionService.Util.getInstance();
		SetUpProcessesCallback callback = new SetUpProcessesCallback(keyword);
		findprocess.findValidProcessing(keyword, callback);
	}

	@Override
	public void onSuccess(String result) {
		Window.alert("RegisterDataDescriptionCallback: " + result);
		DataDescriptionAsRows panel = new DataDescriptionAsRows(result,description);
		MaterialModal.showModal(panel, TYPE.FIXED_FOOTER);
		TextToDatabaseAsync async = TextToDatabase.Util.getInstance();
		SuccessfulRegistrationCallback callback = new SuccessfulRegistrationCallback();
		async.registerDataInputDescription(description,callback);
	}

}
