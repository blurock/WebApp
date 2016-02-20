package info.esblurock.reaction.client.ui.login;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.client.ui.UiImplementationBase;

public class LogoutCallback implements AsyncCallback<Void> {

	UiImplementationBase top;
	
	LogoutCallback(UiImplementationBase top) {
		this.top = top;
	}
	@Override
	public void onFailure(Throwable caught) {
	}

	@Override
	public void onSuccess(Void result) {
		top.setLoggedOut();
	}

}
