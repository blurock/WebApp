package info.esblurock.reaction.client.ui.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class LoginVerification extends Composite implements HasText {

	private static LoginVerificationUiBinder uiBinder = GWT.create(LoginVerificationUiBinder.class);

	interface LoginVerificationUiBinder extends UiBinder<Widget, LoginVerification> {
	}

	public LoginVerification() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button button;

	public LoginVerification(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		button.setText(firstName);
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		Map<String,List<String>> parameters = Window.Location.getParameterMap();
		Window.alert(parameters.keySet().toString());
		Window.alert(parameters.toString());
	}

	public void setText(String text) {
		button.setText(text);
	}

	public String getText() {
		return button.getText();
	}

}
