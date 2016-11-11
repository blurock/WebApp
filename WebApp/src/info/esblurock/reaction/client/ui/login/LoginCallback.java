package info.esblurock.reaction.client.ui.login;

import java.util.Date;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.ui.ReactionTopImpl;

public class LoginCallback implements AsyncCallback<UserDTO> {

	ReactionTopImpl toplevel;
	
	public LoginCallback(ReactionTopImpl toplevel) {
		super();
		this.toplevel = toplevel;
	}

	@Override
	public void onSuccess(UserDTO result) {
		if (result.getLoggedIn()) {
			String sessionID = result.getSessionId();
			final long DURATION = 1000 * 60 * 60;
			Date expires = new Date(System.currentTimeMillis()
					+ DURATION);
			Cookies.setCookie("sid", sessionID, expires, null,
					"/", false);
			Cookies.setCookie("user", result.getName(),
					expires, null, "/", false);
			Cookies.setCookie("level", result.getUserLevel(),
					expires, null, "/", false);
			toplevel.setUser(result);
			toplevel.setLoggedIn();
		} else {
			Window.alert("Access Denied. Check your user-name and password.");
		}
	}

	@Override
	public void onFailure(Throwable caught) {
		Window.alert("Access Denied (exception). Check your user-name and password: "
				+ caught.getMessage());
	}

}
