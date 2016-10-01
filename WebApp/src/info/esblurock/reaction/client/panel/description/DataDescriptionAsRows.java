package info.esblurock.reaction.client.panel.description;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.ModalType;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.resources.DescriptionConstants;
import info.esblurock.reaction.client.resources.InterfaceConstants;
import info.esblurock.reaction.data.description.DescriptionDataData;

public class DataDescriptionAsRows extends Composite implements HasText {

	private static DataDescriptionAsRowsUiBinder uiBinder = GWT.create(DataDescriptionAsRowsUiBinder.class);

	interface DataDescriptionAsRowsUiBinder extends UiBinder<Widget, DataDescriptionAsRows> {
	}

	public DataDescriptionAsRows() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	InterfaceConstants interfaceConstants = GWT.create(InterfaceConstants.class);
	DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);

	@UiField
	MaterialLabel keylabel;
	@UiField
	MaterialLink key;
	@UiField
	MaterialLabel keywordlabel;
	@UiField
	MaterialLink keyword;
	@UiField
	MaterialLabel onelinelabel;
	@UiField
	MaterialLink oneline;
	@UiField
	MaterialLabel sourcekeylabel;
	@UiField
	MaterialLink sourcekey;
	@UiField
	MaterialLabel sourcedatelabel;
	@UiField
	MaterialLink sourcedate;
	@UiField
	MaterialLabel entrykeylabel;
	@UiField
	MaterialLink entrykey;
	@UiField
	MaterialLabel entrydatelabel;
	@UiField
	MaterialLink entrydate;

	public DataDescriptionAsRows(String datakey, DescriptionDataData description) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
		key.setText(datakey);
		keyword.setText(description.getKeyword());
		oneline.setText(description.getOnlinedescription());
		sourcekey.setText(description.getSourcekey());
		if (description.getSourceDate() != null) {
			sourcedate.setText(description.getSourceDate().toString());
		}
		entrykey.setText(description.getInputkey());
		if (description.getCreationDate() != null) {
			entrydate.setText(description.getCreationDate().toString());
		}
	}

	private void init() {
		//key.setTooltip(interfaceConstants.keywordtooltip());
		keylabel.setText(interfaceConstants.keyword());
		//keyword.setTooltip(interfaceConstants.datakeywordtooltip());
		keywordlabel.setText(interfaceConstants.datakeyword());
		onelinelabel.setText(descriptionConstants.onelinetext());
		//oneline.setTooltip(descriptionConstants.onelineplaceholder());
		sourcekeylabel.setText(interfaceConstants.sourcekey());
		//sourcekey.setTooltip(interfaceConstants.sourcekeytooltip());
		sourcedatelabel.setText(interfaceConstants.sourcedate());
		//sourcedate.setTooltip(interfaceConstants.sourcedatetooltip());
		entrykeylabel.setText(interfaceConstants.user());
		//entrykey.setTooltip(interfaceConstants.usertooltip());
		entrydatelabel.setText(interfaceConstants.entrydate());
		//entrydate.setTooltip(interfaceConstants.entrydatetooltip());

	}

	public void setText(String text) {
	}

	public String getText() {
		return keyword.getText();
	}

}
