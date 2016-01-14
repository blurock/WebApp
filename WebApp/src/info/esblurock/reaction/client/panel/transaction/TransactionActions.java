package info.esblurock.reaction.client.panel.transaction;

import info.esblurock.reaction.client.TextToDatabase;
import info.esblurock.reaction.client.TextToDatabaseAsync;
import info.esblurock.reaction.client.panel.contact.ClientStoreOrganizationInput;
import info.esblurock.reaction.client.resources.InterfaceConstants;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class TransactionActions extends Composite implements HasText {

	private static TransactionActionsUiBinder uiBinder = GWT
			.create(TransactionActionsUiBinder.class);

	interface TransactionActionsUiBinder extends
			UiBinder<Widget, TransactionActions> {
	}

	ClientStoreOrganizationInput client = new ClientStoreOrganizationInput();
	
	InterfaceConstants interfaceConstants = GWT
			.create(InterfaceConstants.class);
	
	@UiField
	MaterialTitle title;
	@UiField
	MaterialLink keywordlabel;
	@UiField
	MaterialLink keyword;
	@UiField
	MaterialLink userlabel;
	@UiField
	MaterialLink user;
	@UiField
	MaterialLink filenamelabel;
	@UiField
	MaterialLink filename;
	@UiField
	MaterialLink entrydatelabel;
	@UiField
	MaterialLink entrydate;
	@UiField
	MaterialLink numberlineslabel;
	@UiField
	MaterialLink numberlines;
	
	@UiField
	MaterialButton btnprocess;
	@UiField
	MaterialButton btndelete;
	
	UploadFileTransaction source;
	
	public TransactionActions(UploadFileTransaction source) {
		initWidget(uiBinder.createAndBindUi(this));
		this.source = source;
	
		keywordlabel.setText(interfaceConstants.keyword());
		keywordlabel.setTooltip(interfaceConstants.keywordtooltip());
		
		userlabel.setText(interfaceConstants.user());
		userlabel.setTooltip(interfaceConstants.usertooltip());

		filenamelabel.setText(interfaceConstants.filename());
		filenamelabel.setTooltip(interfaceConstants.filenametooltip());

		entrydatelabel.setText(interfaceConstants.entrydate());
		entrydatelabel.setTooltip(interfaceConstants.entrydatetooltip());

		numberlineslabel.setText(interfaceConstants.numberLines());
		numberlineslabel.setTooltip(interfaceConstants.numberLinestooltip());
		
		title.setTitle(interfaceConstants.inputtransaction());

		keyword.setText(source.getKey());
		user.setText(source.getUser());
		filename.setText(source.getFilename());
		entrydate.setText(source.getCreationDate().toString());
		numberlines.setText(Integer.toString(source.getSetOfLinesKeys().size()));
		
		btnprocess.setText(interfaceConstants.process());
		btnprocess.setTooltip(interfaceConstants.processtooltip());
		btndelete.setText(interfaceConstants.delete());
		btndelete.setTooltip(interfaceConstants.deletetooltip());
		
	}


	public TransactionActions(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("btnprocess")
	void onClickProcess(ClickEvent e) {
		MaterialModal.closeModal();
		MaterialToast.alert("Process");
	}

	@UiHandler("btndelete")
	void onClickDelete(ClickEvent e) {
		MaterialModal.closeModal();
		TextToDatabaseAsync async = TextToDatabase.Util.getInstance();
		RemoveTransactionCallback callback = new RemoveTransactionCallback();
		async.removeUploadedFile(source.getKey(), callback);
		MaterialToast.alert("Delete");
	}

	public void setText(String text) {
	}

	public String getText() {
		return btnprocess.getText();
	}

}
