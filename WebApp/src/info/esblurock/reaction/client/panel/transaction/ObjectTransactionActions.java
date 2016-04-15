package info.esblurock.reaction.client.panel.transaction;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.transaction.process.RemoveTransactionCallback;
import info.esblurock.reaction.client.resources.InterfaceConstants;
import info.esblurock.reaction.data.transaction.TransactionInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ObjectTransactionActions extends Composite implements HasText {

	private static ObjectTransactionActionsUiBinder uiBinder = GWT
			.create(ObjectTransactionActionsUiBinder.class);

	interface ObjectTransactionActionsUiBinder extends
			UiBinder<Widget, ObjectTransactionActions> {
	}
	
	InterfaceConstants interfaceConstants = GWT
			.create(InterfaceConstants.class);
	
	public ObjectTransactionActions() {
		
	}
	public ObjectTransactionActions(TransactionInfo transaction) {
		initWidget(uiBinder.createAndBindUi(this));
		storedObjectKeylabel.setText(interfaceConstants.keyword());
		storedObjectKeylabel.setTooltip(interfaceConstants.keywordtooltip());
		
		storedObjectKeylabel.setText(interfaceConstants.storedObjectKey());
		storedObjectKeylabel.setTooltip(interfaceConstants.storedObjectKeytooltip());
		userlabel.setText(interfaceConstants.user());
		userlabel.setTooltip(interfaceConstants.usertooltip());
		keywordlabel.setText(interfaceConstants.keyword());
		keywordlabel.setTooltip(interfaceConstants.keywordtooltip());
		inputDatelabel.setText(interfaceConstants.inputDate());
		inputDatelabel.setTooltip(interfaceConstants.inputDatetooltip());
		transactionObjectTypelabel.setText(interfaceConstants.transactionObjectType());
		transactionObjectTypelabel.setTooltip(interfaceConstants.transactionObjectTypetooltip());
		rdfKeysSizelabel.setText(interfaceConstants.rdfKeyWordsSize());
		rdfKeysSizelabel.setTooltip(interfaceConstants.rdfKeyWordsSizetooltip());
		
		source = transaction;
		
		
		storedObjectKey.setText(transaction.getStoredObjectKey());
		user.setText(transaction.getUser());
		keyword.setText(transaction.getKeyword());
		inputDate.setText(transaction.getInputDate().toString());
		transactionObjectType.setText(transaction.getTransactionObjectType());
		Integer rdfsize = new Integer(transaction.getNumberOfElements());
		rdfKeysSize.setText(rdfsize.toString());
		
		btndelete.setText(interfaceConstants.delete());
		btndelete.setTooltip(interfaceConstants.deletetooltip());
	}

	@UiField
	MaterialLink storedObjectKeylabel;
	@UiField
	MaterialLink storedObjectKey;
	@UiField
	MaterialLink userlabel;
	@UiField
	MaterialLink user;
	@UiField
	MaterialLink keywordlabel;
	@UiField
	MaterialLink keyword;
	@UiField
	MaterialLink inputDatelabel;
	@UiField
	MaterialLink inputDate;
	@UiField
	MaterialLink transactionObjectTypelabel;
	@UiField
	MaterialLink transactionObjectType;
	@UiField
	MaterialLink rdfKeysSizelabel;
	@UiField
	MaterialLink rdfKeysSize;
	
	@UiField
	MaterialButton btndelete;
	
	TransactionInfo source;
	
	@UiHandler("btndelete")
	void onClickDelete(ClickEvent e) {
		MaterialModal.closeModal();
		TransactionServiceAsync async = TransactionService.Util.getInstance();
		RemoveTransactionCallback callback = new RemoveTransactionCallback();
		async.deleteTransactionInfoFromKey(source.getKey(), callback);
		MaterialToast.alert("Delete: "  + source.getTransactionObjectType());
	}

	public void setText(String text) {
	}

	public String getText() {
		return user.getText();
	}

}
