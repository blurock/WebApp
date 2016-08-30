package info.esblurock.reaction.client.panel.transaction;

import gwt.material.design.client.constants.ModalType;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.modal.TextMessagePopup;
import info.esblurock.reaction.data.transaction.TransactionInfo;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class UpdateObjectTransactionsCallback implements
		AsyncCallback<List<TransactionInfo>> {

	private ObjectTransaction gui;

	public UpdateObjectTransactionsCallback(ObjectTransaction gui) {
		this.gui = gui;
	}

	@Override
	public void onFailure(Throwable caught) {
		MaterialToast.fireToast("ERROR");
		TextMessagePopup popup = new TextMessagePopup("ERROR",
				caught.toString());
		popup.openModal(ModalType.FIXED_FOOTER);
	}

	@Override
	public void onSuccess(List<TransactionInfo> result) {
		try {
			for (TransactionInfo info : result) {
				gui.addTransactionInfo(info);
			}
			gui.refresh();
		} catch (Exception ex) {
			Window.alert(ex.toString());
		}

	}

}
