package info.esblurock.reaction.client.ui.login;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.ui.UiImplementationBase;

public class CheckSessionLoginCallback implements AsyncCallback<UserDTO> {

	UiImplementationBase base;
	String user; 
	String sessionID;
	
	public CheckSessionLoginCallback(String user, UiImplementationBase base) {
		this.base = base;
		this.user = user;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
	}

	@Override
	public void onSuccess(UserDTO result) {
		if(result == null) {
			base.setLoggedOut();
			LoginServiceAsync async = LoginService.Util.getInstance();
			LogoutCallback callback = new LogoutCallback(base);
			async.logout(callback);			
		} else if(result.getName().equals(user)) {
			MaterialToast.fireToast("Valid user session: ");
			base.setUser(result);
		} else {
			base.setLoggedOut();
			LoginServiceAsync async = LoginService.Util.getInstance();
			LogoutCallback callback = new LogoutCallback(base);
			async.logout(callback);
		}
		
	}

}
