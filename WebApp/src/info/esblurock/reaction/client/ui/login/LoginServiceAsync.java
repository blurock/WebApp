package info.esblurock.reaction.client.ui.login;

import info.esblurock.reaction.data.user.UserAccount;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
	public void loginServer(String name, String password,
			AsyncCallback<UserDTO> callback);

	public void loginFromSessionServer(AsyncCallback<UserDTO> callback);
	public void changePassword(String name, String newPassword,
			AsyncCallback<Boolean> callback);

	public void logout(AsyncCallback<Void> callback);

	void storeUserAccount(UserAccount account, AsyncCallback<String> callback);

	void removeUser(String key, AsyncCallback<String> callback);

	void getAccount(String key, AsyncCallback<UserAccount> callback);

	void loginVerification(String username, String key, AsyncCallback<String> callback);

	void firstLoginToServer(String name, AsyncCallback<String> callback);
}

