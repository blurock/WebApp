package info.esblurock.reaction.client.panel.description;

import info.esblurock.reaction.client.TextToDatabase;
import info.esblurock.reaction.client.TextToDatabaseAsync;
import info.esblurock.reaction.client.panel.inputs.SetOfInputs;
import info.esblurock.reaction.client.resources.DescriptionConstants;
import info.esblurock.reaction.data.description.DescriptionDataData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class DataDescription extends Composite implements HasText {

	private static DataDescriptionUiBinder uiBinder = GWT
			.create(DataDescriptionUiBinder.class);

	interface DataDescriptionUiBinder extends UiBinder<Widget, DataDescription> {
	}
	
	int maxKeywordSize = 20;
	int maxOnlineSize = 80;
	int maxFullDescriptionSize = 1495;
	
	DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
	
	@UiField
	MaterialTextBox keyword;
	@UiField
	MaterialTextBox oneline;
	@UiField
	MaterialTextArea description;
	
	@UiField
	MaterialLink objecttitle;
	@UiField
	MaterialDatePicker date;
	@UiField
	MaterialTextBox sourcekey;
	@UiField
	MaterialTextBox inputkey;
	
	@UiField
	HTMLPanel keywordpanel;
	@UiField
	MaterialChip addkeyword;
	@UiField
	MaterialButton descriptionkeywords;
	
	@UiField
	MaterialModal keywordModal;
	@UiField
	MaterialTextBox newKeywordText;
	@UiField
	MaterialButton newKeywordButton;
	@UiField
	MaterialButton keywordCloseButton;
	
	boolean keywordChanged;
	boolean sourcekeyChanged;
	
	SetOfInputs inputSet;
	HashSet<KeywordChip> keywordset;

	public void setInputSet(SetOfInputs set) {
		inputSet = set;
	}
	public void resetKeyword() {
		String keywordS = createObjectKeyword();
		if(inputSet != null) {
			inputSet.setKeyword(getKeyWord(),getSource());
		}
	}
		
	private void setText() {
		keywordChanged = false;
		sourcekeyChanged = false;
		objecttitle.setText(descriptionConstants.descriptionpaneltitle());
		keyword.setPlaceholder(descriptionConstants.keywordplaceholder());
		keyword.setText(descriptionConstants.keywordtext());
		oneline.setText(descriptionConstants.onelinetext());
		oneline.setPlaceholder(descriptionConstants.onelineplaceholder());
		description.setText(descriptionConstants.descriptiontext());
		description.setPlaceholder(descriptionConstants.descriptionplaceholder());
		date.setPlaceholder(descriptionConstants.dateplaceholder());
		date.setTitle(descriptionConstants.dateplaceholder());
		sourcekey.setText(descriptionConstants.sourcetext());
		sourcekey.setPlaceholder(descriptionConstants.sourceplaceholder());
		inputkey.setPlaceholder(descriptionConstants.inputplaceholder());
		String username = Cookies.getCookie("user");
		inputkey.setText(username);
		addkeyword.setVisible(true);
		addkeyword.setEnabled(true);

		newKeywordText.setText(descriptionConstants.newkeyword());
		newKeywordText.setPlaceholder(descriptionConstants.descriptionkeyword());
		newKeywordButton.setText(descriptionConstants.addkeyword());
		keywordCloseButton.setText(descriptionConstants.close());
	}
	
	private void initData() {
		inputSet = null;
	}
	public DataDescription() {
		initWidget(uiBinder.createAndBindUi(this));
		setText();
		initData();
	}
	public DataDescription(String title) {
		initWidget(uiBinder.createAndBindUi(this));
		setText();
		objecttitle.setTitle(title);
		objecttitle.setText(title);
		initData();
	}
	public DataDescription(String title, String keywordtitle, String onelinetitle, String descriptiontitle) {
		initWidget(uiBinder.createAndBindUi(this));
		setText();
		objecttitle.setTitle(title);
		keyword.setPlaceholder(keywordtitle);
		oneline.setPlaceholder(onelinetitle);
		description.setPlaceholder(descriptiontitle);
		initData();
	}
	
	public void setTextAsUserDescription(String username) {
		sourcekey.setText(username);
		keyword.setText(username);
		inputkey.setText(username);
		date.setVisible(false);
		sourcekey.setVisible(false);
		inputkey.setVisible(false);
		keyword.setEnabled(false);
		keywordChanged = true;
		sourcekeyChanged = true;
		objecttitle.setTitle(descriptionConstants.usertitle());
		keyword.setPlaceholder(descriptionConstants.usernameplaceholder());
		keyword.setText(username);
		oneline.setText(descriptionConstants.useronelineplaceholder());
		oneline.setPlaceholder(descriptionConstants.useronelineplaceholder());
		description.setText(descriptionConstants.userdescriptionplaceholder());
		description.setPlaceholder(descriptionConstants.userdescriptionplaceholder());
	}
	public void fill(String keywordS, String onlineS, String descriptionS) {
		keyword.setText(keywordS);
		oneline.setText(onlineS);
		description.setText(descriptionS);
	}
	public void fill(DescriptionDataData descr) {
		keyword.setText(descr.getKeyword());
		oneline.setText(descr.getOnlinedescription());
		description.setText(descr.getFulldescription());
		date.setDate(descr.getSourceDate());
		sourcekey.setText(descr.getSourcekey());
		inputkey.setText(descr.getInputkey());
		Set<String> keywordset = descr.getKeywords();
		for(String key : keywordset) {
			KeywordChip chip = new KeywordChip(key);
			keywordpanel.add(chip);
		}
	}
	public void setUnEditable() {
		keyword.setReadOnly(true);
		oneline.setReadOnly(true);
		sourcekey.setReadOnly(true);
		description.setReadOnly(true);
		inputkey.setReadOnly(true);
		addkeyword.setVisible(false);
		descriptionkeywords.setVisible(false);
		date.setEnabled(false);
	}
	
	public void setKeywords(ArrayList<KeywordChip> set) {
		descriptionkeywords.setVisible(false);
		descriptionkeywords.setEnabled(false);
		addkeyword.setVisible(true);
		addkeyword.setEnabled(true);
		for(KeywordChip chip: set) {
			keywordpanel.add(chip);
		}
	}
	
	public String getKeyWord() {
		return keyword.getText();
	}
	public boolean keywordEntered() {
		return keywordChanged &&  sourcekeyChanged;
	}

	public String getOneLineDescription() {
		return oneline.getText();
	}
	
	public String getDescription() {
		return description.getText();
	}
	
	public String getSource() {
		return sourcekey.getText();
	}
	
	public String getInputKey() {
		return inputkey.getText();
	}
	public Date getSourceDate() {
		Date current = date.getDate();
		if(current == null) {
			current = new Date();
		}
		return current;
	}
	
	public String createObjectKeyword() {
		String name = getSource() + "#" + getKeyWord();
		return name;
	}
	
	public HashSet<String> getKeywords() {
		HashSet<String> keys = new HashSet<String>();
		Iterator<Widget> iter = keywordpanel.iterator();
		while(iter.hasNext()) {
			Widget widget = iter.next();
			String type = widget.getClass().getSimpleName();
			if(type.matches("KeywordChip")) {
				KeywordChip chip = (KeywordChip) widget;
				keys.add(chip.getText());
			}
		}
		return keys;
	}
	
	@Override
	public void setText(String title) {
		objecttitle.setTitle(title);
	}

	@Override
	public String getText() {
		return objecttitle.getTitle();
	}
	
	@UiHandler("addkeyword")
	void onAddKeyword(ClickEvent event) {
		keywordModal.openModal();
	}
	@UiHandler("newKeywordButton")
	void onNewKeywordButton(ClickEvent event) {
		KeywordChip chip = new KeywordChip(newKeywordText.getText());
		keywordpanel.add(chip);
	}
	@UiHandler("keywordCloseButton")
	void onKeywordCloseButton(ClickEvent event) {
		keywordModal.closeModal();
	}
	@UiHandler("sourcekey")
	void onSourceKey(KeyUpEvent e) {
		sourcekeyChanged = true;
		resetKeyword();
	}

	@UiHandler("descriptionkeywords")
	void onDescriptionKeywordsClick(ClickEvent e) {
		KeywordsFromTextCallback callback = new KeywordsFromTextCallback(this);
		TextToDatabaseAsync async = TextToDatabase.Util.getInstance();
		async.keywordsFromText(description.getText(),callback);
	}
	@UiHandler("keyword")
	void onKeyword(KeyUpEvent e) {
		String text = keyword.getText();
		keywordChanged = true;
		if(text.length() >= maxKeywordSize) {
			MaterialToast.fireToast(descriptionConstants.keywordlimit());
			keyword.setText(text.substring(0,maxKeywordSize-1));
		}
		resetKeyword();		
	}
	@UiHandler("oneline")
	void onOneLine(KeyUpEvent e) {
		String text = oneline.getText();
		if(text.length() >= maxOnlineSize) {
			MaterialToast.fireToast(descriptionConstants.onelinelimit());
			oneline.setText(text.substring(0,maxOnlineSize-1));
		}
	}
	@UiHandler("description")
	void onDescription(KeyUpEvent e) {
		String text = description.getText();
		if(text.length() >= maxFullDescriptionSize) {
			MaterialToast.fireToast(descriptionConstants.descriptionlimit());
			description.setText(text.substring(0,maxFullDescriptionSize-1));
		}
		
	}
}
