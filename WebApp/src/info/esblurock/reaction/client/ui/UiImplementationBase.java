package info.esblurock.reaction.client.ui;

import info.esblurock.reaction.client.ui.login.UserDTO;

import com.google.gwt.user.client.ui.Composite;

public class UiImplementationBase  extends Composite {
	
	protected UserDTO user = null;
	String name;
	
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public UserDTO getUser() {
		UserDTO user = null;
		return user;
	}
	public void setName(String name) {
		this.name = name;
	}


}
