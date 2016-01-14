package info.esblurock.reaction.client.panel;

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

public class SecondPanelExample extends Composite implements HasText {

	private static SecondPanelExampleUiBinder uiBinder = GWT
			.create(SecondPanelExampleUiBinder.class);

	interface SecondPanelExampleUiBinder extends
			UiBinder<Widget, SecondPanelExample> {
	}

	public SecondPanelExample() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button button;

	public SecondPanelExample(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		button.setText(firstName);
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}

	@Override
	public void setText(String text) {
		button.setText(text);
	}

	@Override
	public String getText() {
		return button.getText();
	}

}
