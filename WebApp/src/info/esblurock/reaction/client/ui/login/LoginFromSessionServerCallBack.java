package info.esblurock.reaction.client.ui.login;

import info.esblurock.reaction.client.ui.UiImplementationBase;
import gwt.material.design.client.ui.MaterialToast;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginFromSessionServerCallBack implements AsyncCallback<UserDTO> {

	UiImplementationBase uiBase;
	public LoginFromSessionServerCallBack(UiImplementationBase uibase) {
		this.uiBase = uibase;
	}
	@Override
	public void onFailure(Throwable caught) {
		MaterialToast.alert("failure: " + caught.toString());
	}

	@Override
	public void onSuccess(UserDTO result) {
		if (result == null) {
			MaterialToast.alert("No User information");
		} else {
			if (result.getLoggedIn()) {
				uiBase.setUser(result);
				
			} else {
				MaterialToast.alert("No User information from getLoggedIn()");
			}
		}
	}
	

}
