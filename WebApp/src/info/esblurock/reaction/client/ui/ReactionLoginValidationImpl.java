package info.esblurock.reaction.client.ui;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.ModalType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.StoreDescriptionData;
import info.esblurock.reaction.client.StoreDescriptionDataAsync;
import info.esblurock.reaction.client.activity.place.ReactionTopPlace;
import info.esblurock.reaction.client.panel.contact.UserContactInput;
import info.esblurock.reaction.client.resources.LoginInterface;
import info.esblurock.reaction.client.ui.login.LoginService;
import info.esblurock.reaction.client.ui.login.LoginServiceAsync;
import info.esblurock.reaction.client.ui.login.LoginVerificationCallback;

public class ReactionLoginValidationImpl extends Composite implements ReactionLoginValidationView {

	private static ReactionLoginValidationImplUiBinder uiBinder = GWT.create(ReactionLoginValidationImplUiBinder.class);

	interface ReactionLoginValidationImplUiBinder extends UiBinder<Widget, ReactionLoginValidationImpl> {
	}

	Presenter listener;
	String helloName;

	@UiField
	MaterialCard card;

	@UiField
	MaterialLink activate;

	@UiField
	MaterialLink login;

	@UiField
	MaterialLink profile;
	
	@UiField
	MaterialLink mainPage;

	@UiField
	MaterialCardTitle cardtitle;
	@UiField
	MaterialLabel cardlabel;
	
	String userID;
	String userCode;
	MaterialModal usermodal;
	
	LoginInterface loginConstants = GWT.create(LoginInterface.class);

	public ReactionLoginValidationImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public ReactionLoginValidationImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		this.helloName = firstName;
		init();
	}

	private void init() {
		Map<String,List<String>> parameters = Window.Location.getParameterMap();
		List<String> emaillist = parameters.get("id");
		List<String> codelist = parameters.get("name");
		userID = "UserID";
		
		String congrats = loginConstants.LoginValidationTitle();
		
		if(emaillist != null ) {
			userID = emaillist.get(0);
		}
		userCode = "Unknown";
		if(codelist != null) {
			userCode = codelist.get(0);
			congrats += " " + userCode;
		}
		cardtitle.setTitle(congrats);
		cardtitle.setText(loginConstants.LoginValidationTitle());
		cardlabel.setText(loginConstants.LoginValidationText());
		activate.setText(loginConstants.LoginValidationActivate());
		profile.setVisible(false);
		login.setVisible(false);
		
		usermodal = new MaterialModal();
	}
	
	@UiHandler("activate")
	void onActivateButton(ClickEvent e) {
		MaterialToast.fireToast("Activate email: " + userID);
		LoginServiceAsync async = LoginService.Util.getInstance();
		LoginVerificationCallback callback = new LoginVerificationCallback(this);
		async.loginVerification(userCode, userID, callback);
	}
	@UiHandler("profile")
	void onProfileButton(ClickEvent e) {
		UserContactInput user = contactModal(userCode);
		MaterialToast.fireToast("Profile .... Activate email: " + userCode);
		FillInUserContactInputCallback callback = new FillInUserContactInputCallback(user);
		StoreDescriptionDataAsync async = StoreDescriptionData.Util.getInstance();
		async.getUserDescriptionData(userCode, callback);
	}
	@UiHandler("login")
	void onLoginButton(ClickEvent e) {
		LoginServiceAsync async = LoginService.Util.getInstance();
		FirstLoginCallback callback = new FirstLoginCallback(this);
		async.firstLoginToServer(userCode, callback);
	}
	@UiHandler("mainPage") 
	void onMainPage(ClickEvent e) {
		String username = Cookies.getCookie("user");
		listener.goTo(new ReactionTopPlace(username));
	}
	public void deActivate() {
		activate.setVisible(false);
		profile.setVisible(false);
		login.setVisible(true);
	}
	public void firstLogin() {
		activate.setVisible(false);
		profile.setVisible(true);
		login.setVisible(false);		
	}
	private UserContactInput contactModal(String usercode) {
		
		UserContactInput user = new UserContactInput(userCode);
		MaterialModal usermodal = new MaterialModal();
		usermodal.add(user);
		usermodal.setType(ModalType.FIXED_FOOTER);
		usermodal.openModal();

		return user;
	}
	public void setText(String text) {
		helloName = text;
	}
	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	@Override
	public void setName(String helloName) {
		this.helloName = helloName;
	}

}
