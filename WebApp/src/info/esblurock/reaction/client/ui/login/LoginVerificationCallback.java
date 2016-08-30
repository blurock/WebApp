package info.esblurock.reaction.client.ui.login;

import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.contact.UserContactInput;
import info.esblurock.reaction.client.ui.ReactionLoginValidationImpl;

public class LoginVerificationCallback implements AsyncCallback<String> {
	ReactionLoginValidationImpl verify;
	public LoginVerificationCallback(ReactionLoginValidationImpl verify) {
		this.verify = verify;
	}
	@Override
	public void onFailure(Throwable caught) {
		MaterialToast.fireToast("failure to verify (or verification already completed)");
		verify.firstLogin();
	}

	@Override
	public void onSuccess(String email) {
		MaterialToast.fireToast("Verification email has been sent to " + email);
		verify.deActivate();
	}

}
