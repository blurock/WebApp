package info.esblurock.reaction.client.panel.modal;

import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.MaterialTitle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class TextMessagePopup extends Composite implements HasText {

	private static TextMessagePopupUiBinder uiBinder = GWT
			.create(TextMessagePopupUiBinder.class);

	interface TextMessagePopupUiBinder extends
			UiBinder<Widget, TextMessagePopup> {
	}

	public TextMessagePopup() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTitle textarea;

	@UiField
	MaterialModalContent modalcontent;

	public TextMessagePopup(String title, String text) {
		initWidget(uiBinder.createAndBindUi(this));
		textarea.setTitle(title);
		textarea.setDescription(text);
	}

	@Override
	public String getTitle() {
		return textarea.getTitle();
	}

	@Override
	public void setTitle(String title) {
		textarea.setTitle(title);
	}
	@Override
	public String getText() {
		return textarea.getDescription();
	}

	@Override
	public void setText(String text) {
		textarea.setDescription(text);
	}

	@UiHandler("btnOK")
	void onSubmitData(ClickEvent e) {
		MaterialModal.closeModal();
	}

}
