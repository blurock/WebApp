package info.esblurock.reaction.client.panel.inputs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.TextToDatabase;
import info.esblurock.reaction.client.TextToDatabaseAsync;
import info.esblurock.reaction.client.panel.description.DataDescription;
import info.esblurock.reaction.client.resources.DescriptionConstants;
import info.esblurock.reaction.client.resources.InputConstants;
import info.esblurock.reaction.client.resources.InterfaceConstants;
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
	MaterialCollapsible collapsible;

	@UiField
	MaterialButton submitdata;

	DescriptionConstants descriptionConstants = GWT
			.create(DescriptionConstants.class);

	private DataDescription description;
	private HashSet<DataInput> inputs = new HashSet<DataInput>();
	
	String dataType;

	public SetOfInputs(InputSet set, String dataType) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dataType = dataType;
		submitdata.setText(descriptionConstants.submit());
		description = set.getDescription();
		collapsible.addItem(description);
		

		List<DataInput> panels = set.getSet(description);
		for (DataInput panel : panels) {
			collapsible.addItem(panel);
			inputs.add(panel);
		}
	}

	public DataDescription getDescription() {
		return description;
	}

	public Set<DataInput> getPanels() {
		return inputs;
	}

	@UiHandler("submitdata")
	void onSubmitData(ClickEvent e) {

		StringBuilder filesS = new StringBuilder();
		boolean toprocess = true;
		for (DataInput input : inputs) {

			if (input.requiredInput()) {
				filesS.append("Required(");
			} else {
				filesS.append("Optional(");
			}
			if (input.fileUploaded()) {
				filesS.append("uploaded):" + input.getFileSourceType() + ": ");
				filesS.append("file: ");
				filesS.append(input.getUploadfileText());
				filesS.append("ID: ");
				filesS.append(input.getUploadIDText());
				filesS.append("\n");
			} else {
				filesS.append("missing)\n");
			}

			if (input.requiredInput() && !input.fileUploaded())
				toprocess = false;
		}

		filesS.append("To Process: " + toprocess);

		if (toprocess) {
			DescriptionDataData descrdata = new DescriptionDataData(
					description.getKeyWord(),
					description.getOneLineDescription(),
					description.getDescription(), 
					description.getSourceDate(), description.getSource(), 
					description.getInputKey(),
					dataType);

			SubmitSetOfInputsCheckCallback callback = new SubmitSetOfInputsCheckCallback(dataType,descrdata,inputs);
			TextToDatabaseAsync async = TextToDatabase.Util.getInstance();
			async.checkSubmitInputData(descrdata, callback);
		} else {
			Window.alert("Inputs Missing\n" + filesS.toString());
		}
		
	};

}
