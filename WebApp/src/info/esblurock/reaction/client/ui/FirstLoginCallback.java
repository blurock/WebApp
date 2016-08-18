package info.esblurock.reaction.client.ui;

import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;


public class FirstLoginCallback implements AsyncCallback<String> {
	ReactionLoginValidationImpl validate;
	public FirstLoginCallback(ReactionLoginValidationImpl validate) {
		this.validate = validate;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		MaterialToast.alert("Login unsuccessful: Registration error");
		MaterialModal.closeModal();
	}

	@Override
	public void onSuccess(String result) {
		MaterialToast.alert("Login Successful: " + result);
		MaterialToast.alert("Please fill in user profile");
		validate.firstLogin();
	}

}
