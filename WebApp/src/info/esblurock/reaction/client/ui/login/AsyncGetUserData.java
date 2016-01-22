package info.esblurock.reaction.client.ui.login;

import info.esblurock.reaction.client.ui.UiImplementationBase;

public class AsyncGetUserData {
	LoginFromSessionServerCallBack callback;
	public AsyncGetUserData(UiImplementationBase uibase) {
		callback = new LoginFromSessionServerCallBack(uibase);
		LoginServiceAsync async = LoginService.Util.getInstance();
		async.loginFromSessionServer(callback);
	}
}
