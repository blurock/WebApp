package info.esblurock.reaction.client.ui.login;

import gwt.material.design.client.ui.MaterialLink;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class ClientLogout {
	public void logout(MaterialLink toplogin,MaterialLink toplogout) {
		Cookies.removeCookie("user");
		Cookies.removeCookie("sid");
		LoginServiceAsync async = LoginService.Util.getInstance();
		((ServiceDefTarget) async).setServiceEntryPoint("loginservice");
		async.logout(new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Void result) {
			}
				});
		
	}
}
