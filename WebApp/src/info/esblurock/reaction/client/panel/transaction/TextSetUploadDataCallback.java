package info.esblurock.reaction.client.panel.transaction;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialModal.TYPE;
import info.esblurock.reaction.client.panel.modal.TextMessagePopup;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.TextSetUploadData;

public class TextSetUploadDataCallback implements AsyncCallback<List<TextSetUploadData>> {

	UploadFileSetsTransactions gui;
	
	public TextSetUploadDataCallback(UploadFileSetsTransactions gui) {
		this.gui = gui;
	}

	@Override
	public void onFailure(Throwable caught) {
		MaterialToast.alert("ERROR");
		TextMessagePopup popup = new TextMessagePopup("ERROR",
				caught.toString());
		MaterialModal.showModal(popup, TYPE.FIXED_FOOTER);
	}

	@Override
	public void onSuccess(List<TextSetUploadData> result) {
		try {
			for (TextSetUploadData info : result) {
				gui.addTextSetUploadData(info);
			}
			gui.refresh();
		} catch (Exception ex) {
			Window.alert(ex.toString());
		}

	}

}
