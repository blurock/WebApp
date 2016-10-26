package info.esblurock.reaction.client.panel.query.synonyms;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.client.panel.query.ReactionSearchServiceAsync;

public class SetOfSynonymsPanel extends Composite implements HasText {

	private static SetOfSynonymsPanelUiBinder uiBinder = GWT.create(SetOfSynonymsPanelUiBinder.class);

	interface SetOfSynonymsPanelUiBinder extends UiBinder<Widget, SetOfSynonymsPanel> {
	}
	
	@UiField
	MaterialButton submit;
	@UiField
	MaterialTitle synonymtitle;
	@UiField
	MaterialTextBox standardkey;
	@UiField
	MaterialButton referenceheader;
	@UiField
	MaterialCollapsible collapsable;
	
	HashMap<String,ArrayList<String>> standardKeywordSynonyms;

	public SetOfSynonymsPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	@UiHandler("referenceheader")
	void onClick(ClickEvent e) {
		String standard = standardkey.getText();
		if(addStandardKeyword(standard)) {
			addSynonymPanel(standard);
		} else {
			MaterialToast.fireToast("Standard keyword already exists");
		}
		
	}
	@UiHandler("submit")
	void onSubmit(ClickEvent e) {
		MaterialToast.fireToast("Synonyms:\n" + standardKeywordSynonyms.toString());
		RegisterSetOfSynonymsCallback callback = new RegisterSetOfSynonymsCallback();
		ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
		async.registerSynonyms(standardKeywordSynonyms, callback);
	}
	private void init() {
		standardKeywordSynonyms = new HashMap<String,ArrayList<String>>();
		synonymtitle.setTitle("Standard Keyword Synonyms");
		synonymtitle.setDescription("Associated with each standard keyword, a set of keyword synonyms is set up and managed");
		standardkey.setText("Keyword");
		standardkey.setValue("Standard");
		referenceheader.setText("Standard");
		submit.setText("Update");
		submit.setIconType(IconType.UPDATE);
		submit.setIconPosition(IconPosition.RIGHT);
		FillInSetOfSynonymsCallback callback = new FillInSetOfSynonymsCallback(this);
		ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
		async.getSynonymsForStandardKeywords(callback);
	}
	public SynonymPanel addSynonymPanel(String standard) {
		SynonymPanel synonyms = new SynonymPanel(standard,this);
		collapsable.add(synonyms);
		return synonyms;
	}
	public boolean addStandardKeyword(String standard) {
		boolean success = true;
		if(standardKeywordSynonyms.containsKey(standard)) {
			success = false;
		} else {
			ArrayList<String> synonymlist = new ArrayList<String>();
			standardKeywordSynonyms.put(standard, synonymlist);
		}
		return success;
	}
	public void addSynonymChip(SynonymPanel synonyms, String synonym) {
		synonyms.addSynonymToPanel(synonym);
	}
	public boolean addSynonymToStandard(String standard, String synonym) {
		boolean success = true;
		if(standardKeywordSynonyms.containsKey(standard)) {
			ArrayList<String> synonymlist = standardKeywordSynonyms.get(standard);
			if(synonymlist.contains(synonym)) {
				success = false;
			} else {
				synonymlist.add(synonym);
			}
		} else {
			success = false;
		}
		return success;
	}
	public boolean removeSynonymFromStandard(String standard, String synonym) {
		boolean success = true;
		if(standardKeywordSynonyms.containsKey(standard)) {
			ArrayList<String> synonymlist = standardKeywordSynonyms.get(standard);
			synonymlist.remove(synonym);
		} else {
			success = false;
		}
		return success;
	}
	public boolean removeStandard(String standard) {
		boolean success = true;
		if(standardKeywordSynonyms.containsKey(standard)) {
			standardKeywordSynonyms.remove(standard);
		} else {
			success = false;
		}
		return success;		
	}
	public void setText(String text) {
	}

	public String getText() {
		return null;
	}

}
