package info.esblurock.reaction.client.panel.transaction;

import gwt.material.design.client.constants.ModalType;
import info.esblurock.reaction.client.panel.modal.TextMessagePopup;
import info.esblurock.reaction.data.upload.UploadFileTransaction;

import java.util.Set;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class UpdateUploadTransactionsCallback implements
		AsyncCallback<Set<UploadFileTransaction>> {
	private TransactionSources gui;

	public UpdateUploadTransactionsCallback(TransactionSources gui) {
		this.gui = gui;

	}

	@Override
	public void onFailure(Throwable caught) {
		TextMessagePopup popup = new TextMessagePopup("ERROR",
				caught.toString());
		popup.openModal(ModalType.FIXED_FOOTER);
	}

	@Override
	public void onSuccess(Set<UploadFileTransaction> result) {
		try {
			for (UploadFileTransaction source : result) {
				gui.addUploadFileTransaction(source);
			}
			//gui.setGrid(null);
			gui.refresh();
		} catch (Exception ex) {
			Window.alert(ex.toString());
		}
	}

}
