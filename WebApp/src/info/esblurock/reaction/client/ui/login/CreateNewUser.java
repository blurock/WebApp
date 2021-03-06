package info.esblurock.reaction.client.ui.login;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.description.DataDescription;
import info.esblurock.reaction.client.resources.DescriptionConstants;
import info.esblurock.reaction.client.resources.InputConstants;
import info.esblurock.reaction.client.resources.InterfaceConstants;
import info.esblurock.reaction.data.user.UserAccount;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class CreateNewUser extends Composite implements HasText {

	private static CreateNewUserUiBinder uiBinder = GWT.create(CreateNewUserUiBinder.class);

	interface CreateNewUserUiBinder extends UiBinder<Widget, CreateNewUser> {
	}

	InputConstants inputConstants = GWT.create(InputConstants.class);
	InterfaceConstants interfaceConstants = GWT.create(InterfaceConstants.class);
	DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);

	private DataDescription description;

	private String standarduser = "StandardUser";

	@UiField
	MaterialTextBox username;
	@UiField
	MaterialTextBox password;
	@UiField
	MaterialTextBox repeatpassword;
	@UiField
	MaterialButton submitdata;
	@UiField
	MaterialTextBox emailaddress;

	public CreateNewUser() {
		initWidget(uiBinder.createAndBindUi(this));
		username.setTitle(inputConstants.usernameplaceholder());
		username.setPlaceholder(inputConstants.username());
		password.setTitle(inputConstants.passwordplaceholder());
		password.setPlaceholder(inputConstants.password());
		emailaddress.setTitle(inputConstants.emailaddress());
		emailaddress.setPlaceholder(inputConstants.emailaddressplaceholder());
		repeatpassword.setTitle(inputConstants.repeatpasswordplaceholder());
		repeatpassword.setPlaceholder(inputConstants.repeatpassword());
		submitdata.setTitle(inputConstants.submit());
		submitdata.setText(inputConstants.submit());
	}

	public CreateNewUser(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("submitdata")
	void submitClick(ClickEvent e) {
		if (emailaddress.getText().contains("@")) {

			if (password.getText().equals(repeatpassword.getText())) {
				UserAccount account = new UserAccount(username.getText(), password.getText(), 
						standarduser,emailaddress.getText());

				LoginServiceAsync async = LoginService.Util.getInstance();
				((ServiceDefTarget) async).setServiceEntryPoint("loginservice");
				CreateLoginAccountCallback callback = new CreateLoginAccountCallback();
				async.storeUserAccount(account, callback);

			} else {
				password.setText("");
				repeatpassword.setText("");
				String message = "Passwords do not match... retype";
				MaterialToast.fireToast(message);
			}
		} else {
			String message = "Not a valid email address";
			MaterialToast.fireToast(message);
		}

	}

	public void setText(String text) {
	}

	public String getText() {
		return null;
	}

}
