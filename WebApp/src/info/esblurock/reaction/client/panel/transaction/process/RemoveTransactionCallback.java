package info.esblurock.reaction.client.panel.transaction.process;

import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModal.TYPE;
import info.esblurock.reaction.client.panel.modal.TextMessagePopup;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class RemoveTransactionCallback  implements
AsyncCallback<String>{

	@Override
	public void onFailure(Throwable caught) {
		TextMessagePopup popup = new TextMessagePopup("ERROR",
				caught.toString());
		MaterialModal.showModal(popup, TYPE.FIXED_FOOTER);
	}

	@Override
	public void onSuccess(String result) {
		TextMessagePopup popup = new TextMessagePopup("SUCCESS",
				result);
		MaterialModal.showModal(popup, TYPE.FIXED_FOOTER);
	}

}
