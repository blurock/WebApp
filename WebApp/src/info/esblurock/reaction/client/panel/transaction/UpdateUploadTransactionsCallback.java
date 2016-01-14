package info.esblurock.reaction.client.panel.transaction;

import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialModal.TYPE;
import info.esblurock.reaction.client.panel.modal.TextMessagePopup;
import info.esblurock.reaction.data.upload.UploadFileTransaction;

import java.util.Date;
import java.util.List;
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
		MaterialModal.showModal(popup, TYPE.FIXED_FOOTER);
	}

	@Override
	public void onSuccess(Set<UploadFileTransaction> result) {
		try {
			MaterialToast.alert("onSuccess begin: "
					+ Integer.toString(result.size()));
			for (UploadFileTransaction source : result) {
				gui.addUploadFileTransaction(source);
			}
			//gui.setGrid(null);
			gui.refresh();
			MaterialToast.alert(Integer.toString(gui.getTransactions().size()));
		} catch (Exception ex) {
			Window.alert(ex.toString());
		}
	}

}
