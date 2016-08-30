package info.esblurock.reaction.client.ui.login;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;

public class CreateLoginAccountCallback implements AsyncCallback<String> {

	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
	}

	@Override
	public void onSuccess(String result) {
		if (result != null) {
			String message = "Account activation email sent to result";
			MaterialToast.fireToast(message);
			//MaterialModal.closeModal();
		} else {
			String message = "Username and/or email already exists";
			MaterialToast.fireToast(message);
		}
	}

}
