package info.esblurock.reaction.client.panel.transaction;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.callback.StandardStringReturnCallback;
import info.esblurock.reaction.client.panel.transaction.process.upload.ProcessUploadFiles;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.upload.InputTextSource;
import info.esblurock.reaction.data.upload.TextSetUploadData;

public class UploadFileProcessModal extends Composite implements HasText {

	private static UploadFileProcessModalUiBinder uiBinder = GWT.create(UploadFileProcessModalUiBinder.class);

	interface UploadFileProcessModalUiBinder extends UiBinder<Widget, UploadFileProcessModal> {
	}

	
	
	InputTextSource object;
	TextSetUploadData data;
	
	public UploadFileProcessModal(TextSetUploadData data, InputTextSource object) {
		initWidget(uiBinder.createAndBindUi(this));
		this.object = object;
		this.data = data;
		btnOK.setText("Close");
	}

	@UiField
	MaterialButton testbutton;
	@UiField
	MaterialButton processbutton;
	@UiField
	MaterialButton deletebutton;
	@UiField
	MaterialButton btnOK;
	
	@UiField
	MaterialLabel testlabel;
	@UiField
	MaterialLabel processlabel;
	@UiField
	MaterialLabel deletelabel;
	

	@UiHandler("testbutton")
	void onTestClick(ClickEvent e) {
		MaterialToast.alert("Process Data (Test): " + object.getTextType());
		ProcessUploadFiles process = ProcessUploadFiles.valueOf(object.getTextType());
		DescriptionDataData description = data.getDescription();
		process.process(description,object.getID(), object.getTextname(), false);
	}

	@UiHandler("processbutton")
	void onProcessClick(ClickEvent e) {
		MaterialToast.alert("Process Data: " + object.getTextType());
		ProcessUploadFiles process = ProcessUploadFiles.valueOf(object.getTextType());
		DescriptionDataData description = data.getDescription();
		process.process(description,object.getID(), object.getTextname(), true);
	}

	@UiHandler("deletebutton")
	void onDeleteClick(ClickEvent e) {
		ProcessUploadFiles process = ProcessUploadFiles.valueOf(object.getTextType());
		String transactionType = process.getFullTypeName();
		DescriptionDataData description = data.getDescription();
		String keyword = description.getSourcekey()+ "#" + description.getKeyword();
		
		TransactionServiceAsync async = TransactionService.Util.getInstance();
		String deleteS = "Operation Delete(" + transactionType + "," + keyword + "): \n";
		StandardStringReturnCallback callback = new StandardStringReturnCallback(deleteS);
		async.removeTransactionWithTypeAndKeyword(transactionType, keyword, callback);
	}

	@UiHandler("btnOK")
	void onOKClick(ClickEvent e) {
		MaterialModal.closeModal();
	}

	public void setText(String text) {
		btnOK.setText(text);
	}

	public String getText() {
		return btnOK.getText();
	}

}
