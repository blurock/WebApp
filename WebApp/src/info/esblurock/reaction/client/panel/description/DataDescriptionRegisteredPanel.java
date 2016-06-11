package info.esblurock.reaction.client.panel.description;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTextBox;

public class DataDescriptionRegisteredPanel extends Composite implements HasText {

	private static DataDescriptionRegisteredPanelUiBinder uiBinder = GWT
			.create(DataDescriptionRegisteredPanelUiBinder.class);

	interface DataDescriptionRegisteredPanelUiBinder extends UiBinder<Widget, DataDescriptionRegisteredPanel> {
	}

	public DataDescriptionRegisteredPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialTextBox titleText;
	
	@UiField
	SimplePanel descriptionPanel;
	
	@UiField
	MaterialButton close;

	public DataDescriptionRegisteredPanel(String title, DataDescriptionAsRows description) {
		initWidget(uiBinder.createAndBindUi(this));
		titleText.setText(title);
		descriptionPanel.add(description);
	}

	public void setText(String text) {
		titleText.setText(text);
	}

	public String getText() {
		return titleText.getText();
	}

}
