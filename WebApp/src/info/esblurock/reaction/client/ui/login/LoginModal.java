package info.esblurock.reaction.client.ui.login;

import java.util.Date;

import info.esblurock.reaction.client.activity.place.ReactionFirstPlace;
import info.esblurock.reaction.client.activity.place.ReactionQueryPlace;
import info.esblurock.reaction.client.ui.ReactionTopImpl;
import info.esblurock.reaction.client.ui.ReactionTopView.Presenter;
import info.esblurock.reaction.client.ui.resource.LoginResources;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialModal.TYPE;

import com.google.appengine.api.search.Results;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.thirdparty.javascript.jscomp.Result;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class LoginModal extends Composite implements HasText {

	private String query = "Query";
	private String input = "DataInput";

	private static LoginModalUiBinder uiBinder = GWT
			.create(LoginModalUiBinder.class);

	interface LoginModalUiBinder extends UiBinder<Widget, LoginModal> {
	}

	LoginResources resources = GWT.create(LoginResources.class);

	@UiField
	MaterialTextBox username;
	@UiField
	MaterialTextBox userpassword;
	@UiField
	MaterialCheckBox keepme;
	@UiField
	MaterialButton btnCreate;

	private String name;
	ReactionTopImpl toplevel;
	boolean create = true;

	public LoginModal(ReactionTopImpl toplevel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.toplevel = toplevel;
		username.setText("");
		userpassword.setText("");
	}

	private Presenter listener;

	@Override
	public String getText() {
		return null;
	}

	@Override
	public void setText(String text) {
	}

	@UiHandler("btnLogin")
	void onLoginClick(ClickEvent e) {

		LoginServiceAsync async = LoginService.Util.getInstance();
		((ServiceDefTarget) async).setServiceEntryPoint("loginservice");
		async.loginServer(username.getText(), userpassword.getText(),
				new AsyncCallback<UserDTO>() {
					@Override
					public void onSuccess(UserDTO result) {
						if (result.getLoggedIn()) {

							String sessionID = result.getSessionId();

							final long DURATION = 1000 * 60 * 60 * 24 * 1;
							Date expires = new Date(System.currentTimeMillis()
									+ DURATION);
							Cookies.setCookie("sid", sessionID, expires, null,
									"/", false);
							Cookies.setCookie("user", result.getName(),
									expires, null, "/", false);
							String level = result.getUserLevel();
							Cookies.setCookie("level", result.getUserLevel(),
									expires, null, "/", false);
							toplevel.setLoggedIn();
							toplevel.setUser(result);
							MaterialModal.closeModal();
						} else {
							Window.alert("Access Denied. Check your user-name and password.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Access Denied (exception). Check your user-name and password: "
								+ caught.getMessage());
					}
				});

	}

	@UiHandler("btnCreate")
	void onCreateLoginClick(ClickEvent e) {
		if (create) {
			CreateNewUser newuser = new CreateNewUser();
			MaterialModal.showModal(newuser, TYPE.BOTTOM_SHEET);
		}
	}

	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}
}
