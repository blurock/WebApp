package info.esblurock.reaction.client.panel.query.synonyms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.description.KeywordChip;

public class SynonymPanel extends Composite implements HasText {

	private static SynonymPanelUiBinder uiBinder = GWT.create(SynonymPanelUiBinder.class);

	interface SynonymPanelUiBinder extends UiBinder<Widget, SynonymPanel> {
	}
	@UiField
	MaterialLink standardKeyword;
	@UiField
	HTMLPanel keywordpanel;
	@UiField
	MaterialChip addkeyword;
	@UiField
	MaterialModal keywordModal;
	@UiField
	MaterialTextBox newKeywordText;
	@UiField
	MaterialButton newKeywordButton;
	@UiField
	MaterialButton keywordCloseButton;
	@UiField
	MaterialIcon delete;
	
	SetOfSynonymsPanel parent;

	public SynonymPanel(String standardKeyword, SetOfSynonymsPanel parent) {
		initWidget(uiBinder.createAndBindUi(this));
		setText(standardKeyword);
		this.parent = parent;
		init();
	}

	void init() {
		newKeywordButton.setText("Add Synonym");
		keywordCloseButton.setText("Close");
		newKeywordText.setText("Synonym");
		newKeywordText.setPlaceholder("synonym");
	}
	@UiHandler("addkeyword")
	void onAddKeyword(ClickEvent event) {
		keywordModal.openModal();
	}
	@UiHandler("newKeywordButton")
	void onNewKeywordButton(ClickEvent event) {
		if(parent.addSynonymToStandard(getText(), newKeywordText.getText())) {
			addSynonymToPanel(newKeywordText.getText());
		} else {
			MaterialToast.fireToast("Error in adding keyword: " + getText() + ": " + newKeywordText.getText());
		}
	}
	@UiHandler("keywordCloseButton")
	void onKeywordCloseButton(ClickEvent event) {
		keywordModal.closeModal();
	}
	public void addSynonymToPanel(String synonym) {
		SynonymChip chip = new SynonymChip(synonym,this);
		keywordpanel.add(chip);		
	}
	public boolean removeKeyword(String keyword) {
		MaterialToast.fireToast("removeKeyword");
		return parent.removeSynonymFromStandard(getText(), keyword);
	}
	public void setText(String text) {
		standardKeyword.setText(text);
	}

	public String getText() {
		return standardKeyword.getText();
	}
	@UiHandler("delete")
	public void onDeleteReference(ClickEvent event) {
		if(parent.removeStandard(getText())) {
			this.removeFromParent();
		} else {
			MaterialToast.fireToast("Error in removing standard keyword: " + getText());
		}
	}

}
