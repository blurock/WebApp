package info.esblurock.reaction.client.ui.login;

import info.esblurock.reaction.client.ui.UiImplementationBase;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class ClientLogout {
	
	public void logout(UiImplementationBase top) {
		Cookies.removeCookie("user");
		Cookies.removeCookie("sid");
		LoginServiceAsync async = LoginService.Util.getInstance();
		((ServiceDefTarget) async).setServiceEntryPoint("loginservice");
		async.logout(new LogoutCallback(top));
		
	}
}
