package info.esblurock.reaction.client.panel.data;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.MaterialTitle;

public class BaseDataPresentation extends Composite implements HasText {

	private static BaseDataPresentationUiBinder uiBinder = GWT.create(BaseDataPresentationUiBinder.class);

	interface BaseDataPresentationUiBinder extends UiBinder<Widget, BaseDataPresentation> {
	}

	@UiField
	MaterialTitle title;
	@UiField
	MaterialButton close;
	@UiField
	MaterialModalContent modalcontent;
	
	
	public BaseDataPresentation() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public BaseDataPresentation(String text, String description) {
		initWidget(uiBinder.createAndBindUi(this));
		title.setTitle(text);
		title.setDescription(description);
	}

	@UiHandler("close")
	void onClick(ClickEvent e) {
		MaterialModal.closeModal();
	}

	public void setText(String text) {
		title.setTitle(text);
	}

	public String getText() {
		return title.getTitle();
	}
	public MaterialModalContent getModalContent() {
		return modalcontent;
	}
}
