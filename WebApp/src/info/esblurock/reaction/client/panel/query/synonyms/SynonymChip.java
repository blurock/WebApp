package info.esblurock.reaction.client.panel.query.synonyms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialToast;

public class SynonymChip extends Composite implements HasText {

	private static SynonymChipUiBinder uiBinder = GWT.create(SynonymChipUiBinder.class);

	interface SynonymChipUiBinder extends UiBinder<Widget, SynonymChip> {
	}
	@UiField
	MaterialChip keyword;
	SynonymPanel parent;
	
	ClickHandler click = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			MaterialToast.fireToast("Click: " + keyword.getText());
			boolean ans = parent.removeKeyword(keyword.getText());
			if(ans) {
				MaterialToast.fireToast("Deleted: " + keyword.getText());
			} else {
				MaterialToast.fireToast("Trouble deleting synonym: " + keyword.getText());
			}
		}
	};

	public SynonymChip(String key, SynonymPanel parent) {
		initWidget(uiBinder.createAndBindUi(this));
		this.parent = parent;
		keyword.setText(key);
		keyword.getIcon().addClickHandler(click);
		
	}

	public void setText(String text) {
		keyword.setText(text);
	}

	public String getText() {
		return keyword.getText();
	}

}
