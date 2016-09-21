package info.esblurock.reaction.client.panel.inputs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.TextToDatabase;
import info.esblurock.reaction.client.TextToDatabaseAsync;
import info.esblurock.reaction.client.panel.description.DataDescription;
import info.esblurock.reaction.client.panel.description.ReferenceDescriptions;
import info.esblurock.reaction.client.panel.description.SetOfReferenceDescriptions;
import info.esblurock.reaction.client.resources.DescriptionConstants;
import info.esblurock.reaction.client.resources.InputConstants;
import info.esblurock.reaction.client.resources.InterfaceConstants;
import info.esblurock.reaction.data.description.DataSetReference;
import info.esblurock.reaction.data.description.DescriptionDataData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SetOfInputs extends Composite {

	private static SetOfInputsUiBinder uiBinder = GWT
			.create(SetOfInputsUiBinder.class);

	interface SetOfInputsUiBinder extends UiBinder<Widget, SetOfInputs> {
	}

	InputConstants inputConstants = GWT.create(InputConstants.class);
	InterfaceConstants interfaceConstants = GWT.create(InterfaceConstants.class);

	@UiField
	MaterialButton submitdata;
	@UiField
	MaterialCollapsible collapsable;
	@UiField
	MaterialModal modal;
	@UiField
	MaterialModalContent modalcontent;

	DescriptionConstants descriptionConstants = GWT
			.create(DescriptionConstants.class);

	private DataDescription description;
	private HashSet<DataInput> inputs = new HashSet<DataInput>();
	private SetOfReferenceDescriptions referenceset;
	
	String dataType;

	public SetOfInputs(InputSet set, String dataType) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dataType = dataType;
		submitdata.setText(descriptionConstants.submit());
		description = set.getDescription();
		collapsable.add(description);
		description.setInputSet(this);
		
		referenceset = new SetOfReferenceDescriptions();
		collapsable.add(referenceset);
		
		List<DataInput> panels = set.getSet(description);
		for (DataInput panel : panels) {
			collapsable.add(panel);
			inputs.add(panel);
		}
	}

	public DataDescription getDescription() {
		return description;
	}

	public Set<DataInput> getPanels() {
		return inputs;
	}

	public void setKeyword(String keyword, String source) {
		for(DataInput input : inputs) {
			input.setKeyword(keyword, source);
		}
	}
	public void setInputVisibility(boolean visible) {
		for(DataInput input : inputs) {
			input.setVisibility(visible);
		}		
	}
	
	@UiHandler("submitdata")
	void onSubmitData(ClickEvent e) {
		if(description.keywordEntered()) {
			DescriptionDataData descrdata = new DescriptionDataData(
					description.getKeyWord(),
					description.getOneLineDescription(),
					description.getDescription(), 
					description.getSourceDate(), description.getSource(), 
					description.getInputKey(),
					dataType,
					description.getKeywords());
			ArrayList<DataSetReference> references = getReferences();
			setKeyword(description.getKeyWord(), description.getSource());
			RegisterDataDescriptionCallback callback = 
					new RegisterDataDescriptionCallback(dataType,descrdata,references,modal,modalcontent);
			TextToDatabaseAsync async = TextToDatabase.Util.getInstance();
			MaterialToast.fireToast("onSubmitData 6");
			async.checkSubmitInputData(descrdata, callback);
		} else {
			Window.alert("It is important to enter a keyword and the source");
		}
	};
	private ArrayList<DataSetReference> getReferences() {
		ArrayList<DataSetReference> reflist = new ArrayList<DataSetReference>();
		for(ReferenceDescriptions ref : referenceset.getReferences()) {
			MaterialToast.fireToast("getReferences(): " + ref.getDOI());
			DataSetReference references = new DataSetReference(description.createObjectKeyword(),
					ref.getDOI(),ref.getTitle(),ref.getReference(),
					ref.getAuthors(),ref.getLastNames());
			reflist.add(references);
		}
		return reflist;
	}
}
