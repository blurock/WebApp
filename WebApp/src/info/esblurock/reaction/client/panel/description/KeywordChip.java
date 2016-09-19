package info.esblurock.reaction.client.panel.description;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialToast;

public class KeywordChip extends Composite implements HasText {

	private static KeywordChipUiBinder uiBinder = GWT.create(KeywordChipUiBinder.class);

	interface KeywordChipUiBinder extends UiBinder<Widget, KeywordChip> {
	}

	public KeywordChip() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialChip keyword;

	public KeywordChip(String key) {
		initWidget(uiBinder.createAndBindUi(this));
		keyword.setText(key);
		keyword.getIcon().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				MaterialToast.fireToast(keyword.getText());
			}
		});
	}

	public void setText(String text) {
		keyword.setText(text);
	}

	public String getText() {
		return keyword.getText();
	}

}
