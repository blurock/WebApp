package info.esblurock.reaction.client.panel.transaction.process;

import gwt.material.design.client.constants.ModalType;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.modal.TextMessagePopup;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RemoveTransactionCallback  implements
AsyncCallback<String>{

	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
		TextMessagePopup popup = new TextMessagePopup("ERROR",
				caught.toString());
		popup.openModal(ModalType.FIXED_FOOTER);
	}

	@Override
	public void onSuccess(String result) {
		TextMessagePopup popup = new TextMessagePopup("SUCCESS",
				result);
		popup.openModal(ModalType.FIXED_FOOTER);
	}

}
