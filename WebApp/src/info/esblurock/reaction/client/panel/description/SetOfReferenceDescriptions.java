package info.esblurock.reaction.client.panel.description;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import info.esblurock.reaction.client.StoreDescriptionData;
import info.esblurock.reaction.client.StoreDescriptionDataAsync;
import info.esblurock.reaction.client.panel.repository.data.FillInDataSetReferencesCallback;

public class SetOfReferenceDescriptions extends Composite implements HasText {

	private static SetOfReferenceDescriptionsUiBinder uiBinder = GWT.create(SetOfReferenceDescriptionsUiBinder.class);

	interface SetOfReferenceDescriptionsUiBinder extends UiBinder<Widget, SetOfReferenceDescriptions> {
	}

	@UiField
	MaterialLink addreference;
	@UiField
	MaterialCollapsible references;
	@UiField
	MaterialLink referenceheader;
	
	ArrayList<ReferenceDescriptions> refdescriptions;
	
	public SetOfReferenceDescriptions() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}


	public SetOfReferenceDescriptions(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		addreference.setText(firstName);
		init();
	}

	void init() {
		referenceheader.setText("References");
		addreference.setText("Add Reference");
	}
	
	public void setUnenabled() {
		addreference.setVisible(false);
	}
	
	@UiHandler("addreference")
	void onClick(ClickEvent e) {
		ReferenceDescriptions reference = new ReferenceDescriptions();
		references.add(reference);
	}

	public void addReference(ReferenceDescriptions reference) {
		references.add(reference);
	}
	public void setText(String text) {
		addreference.setText(text);
	}

	public String getText() {
		return addreference.getText();
	}

	public ArrayList<ReferenceDescriptions> getReferences() {
		refdescriptions = new ArrayList<ReferenceDescriptions>();
		Iterator<Widget> iter = references.iterator();
		while(iter.hasNext()) {
			Widget widget = iter.next();
			String type = widget.getClass().getSimpleName();
			if(type.matches("ReferenceDescriptions")) {
				ReferenceDescriptions ref = (ReferenceDescriptions) widget;
				refdescriptions.add(ref);
			}
		}
		return refdescriptions;
	}
	public void fillInReferences(String datasetkeyword) {
		StoreDescriptionDataAsync async = StoreDescriptionData.Util.getInstance();
		FillInDataSetReferencesCallback callback = new FillInDataSetReferencesCallback(this);
		async.getDataSetReferences(datasetkeyword, callback);
	}
}
