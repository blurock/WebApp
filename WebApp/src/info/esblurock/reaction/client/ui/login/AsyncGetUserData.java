package info.esblurock.reaction.client.ui.login;

import com.google.gwt.user.client.Window;

import info.esblurock.reaction.client.ui.UiImplementationBase;

public class AsyncGetUserData {
	LoginFromSessionServerCallBack callback;
	public AsyncGetUserData(UiImplementationBase uibase) {
		callback = new LoginFromSessionServerCallBack(uibase);
		LoginServiceAsync async = LoginService.Util.getInstance();
		Window.alert("AsyncGetUserData calling");
		async.loginFromSessionServer(callback);
	}
}
