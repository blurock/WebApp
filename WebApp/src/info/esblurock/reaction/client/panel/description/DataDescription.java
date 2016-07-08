package info.esblurock.reaction.client.panel.description;

import info.esblurock.reaction.client.panel.inputs.InputSet;
import info.esblurock.reaction.client.panel.inputs.SetOfInputs;
import info.esblurock.reaction.client.resources.DescriptionConstants;

import java.util.Date;

import gwt.material.design.client.ui.MaterialDatePicker;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
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
	HTMLPanel contentpanel;
	@UiField
	MaterialLink objecttitle;
	@UiField
	MaterialDatePicker date;
	@UiField
	MaterialTextBox sourcekey;
	@UiField
	MaterialTextBox inputkey;
	
	boolean keywordChanged;
	boolean sourcekeyChanged;
	
	SetOfInputs inputSet;

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
		objecttitle.setText(descriptionConstants.title());
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
		objecttitle.setText(title);
		initData();
	}
	public DataDescription(String title, String keywordtitle, String onelinetitle, String descriptiontitle) {
		initWidget(uiBinder.createAndBindUi(this));
		setText();
		objecttitle.setText(title);
		keyword.setPlaceholder(keywordtitle);
		oneline.setPlaceholder(onelinetitle);
		description.setPlaceholder(descriptiontitle);
		initData();
	}
	public void fill(String keywordS, String onlineS, String descriptionS) {
		keyword.setText(keywordS);
		oneline.setText(onlineS);
		description.setText(descriptionS);
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
		return date.getDate();
	}
	
	public String createObjectKeyword() {
		String name = getSource() + "#" + getKeyWord();
		return name;
	}
	
	@Override
	public void setText(String title) {
		objecttitle.setTitle(title);
	}

	@Override
	public String getText() {
		return objecttitle.getTitle();
	}
	@UiHandler("sourcekey")
	void onSourceKey(KeyUpEvent e) {
		sourcekeyChanged = true;
		resetKeyword();
	}

	@UiHandler("keyword")
	void onKeyword(KeyUpEvent e) {
		String text = keyword.getText();
		keywordChanged = true;
		if(text.length() >= maxKeywordSize) {
			MaterialToast.alert(descriptionConstants.keywordlimit());
			keyword.setText(text.substring(0,maxKeywordSize-1));
		}
		resetKeyword();		
	}
	@UiHandler("oneline")
	void onOneLine(KeyUpEvent e) {
		
		String text = oneline.getText();
		if(text.length() >= maxOnlineSize) {
			MaterialToast.alert(descriptionConstants.onelinelimit());
			oneline.setText(text.substring(0,maxOnlineSize-1));
		}
		
	}
	@UiHandler("description")
	void onDescription(KeyUpEvent e) {
		String text = description.getText();
		if(text.length() >= maxFullDescriptionSize) {
			MaterialToast.alert(descriptionConstants.descriptionlimit());
			description.setText(text.substring(0,maxFullDescriptionSize-1));
		}
		
	}
}
