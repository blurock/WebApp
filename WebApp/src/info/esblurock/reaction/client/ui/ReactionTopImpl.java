package info.esblurock.reaction.client.ui;

import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.constants.ModalType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.StoreDescriptionData;
import info.esblurock.reaction.client.StoreDescriptionDataAsync;
import info.esblurock.reaction.client.panel.contact.UserContactInput;
import info.esblurock.reaction.client.resources.InputConstants;
import info.esblurock.reaction.client.resources.InterfaceConstants;
import info.esblurock.reaction.client.resources.LoginInterface;
import info.esblurock.reaction.client.ui.login.CheckSessionLoginCallback;
import info.esblurock.reaction.client.ui.login.ClientLogout;
import info.esblurock.reaction.client.ui.login.CreateNewUser;
import info.esblurock.reaction.client.ui.login.LoginCallback;
import info.esblurock.reaction.client.ui.login.LoginService;
import info.esblurock.reaction.client.ui.login.LoginServiceAsync;
import info.esblurock.reaction.client.ui.login.UserDTO;
import info.esblurock.reaction.client.ui.modal.TopPageLinks;
import info.esblurock.reaction.client.ui.resource.ReactionTopViewResources;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Widget;

public class ReactionTopImpl extends UiImplementationBase implements ReactionTopView {

	private static ReactionTopImplUiBinder uiBinder = GWT
			.create(ReactionTopImplUiBinder.class);

	interface ReactionTopImplUiBinder extends UiBinder<Widget, ReactionTopImpl> {
	}
	
	InputConstants inputConstants = GWT.create(InputConstants.class);
	InterfaceConstants interfaceconstants = GWT.create(InterfaceConstants.class);
	@UiField
	MaterialTitle firstdescription;

	@UiField
	MaterialTitle seconddescription;

	@UiField
	MaterialLink profile;
	@UiField
	MaterialLabel username;
	@UiField
	MaterialLink linkmenu;
	
	/*
	@UiField
	MaterialLink toplogin;
	*/
	@UiField
	MaterialLink toplogout;

	@UiField
	MaterialTitle logintitle;
	@UiField
	MaterialTextBox accountname;
	@UiField
	MaterialTextBox userpassword;
	@UiField
	MaterialButton btnCreate;
	@UiField
	MaterialButton btnLogin;

	
	@UiField
	MaterialTitle item1caption;
	@UiField
	MaterialTitle item2caption;
	@UiField
	MaterialTitle item3caption;
	@UiField
	MaterialTitle item4caption;

	@UiField
	MaterialWindow linkWindow;
	@UiField
	MaterialWindow userCreateWindow;
	
	@UiField 
	MaterialRow loginrow;
	@UiField
	MaterialPanel createLogin;
	
	MaterialPanel mpanel;

	private Presenter listener;
	private ClientLogout logout = new ClientLogout();
	TopPageLinks links;
	boolean create = true;
	CreateNewUser newuser;
	
	public ReactionTopImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		String item1text = ReactionTopViewResources.INSTANCE.exampleItem1().getText();
		item1caption.setDescription(item1text);
		String item2text = ReactionTopViewResources.INSTANCE.exampleItem2().getText();
		item2caption.setDescription(item2text);
		String item3text = ReactionTopViewResources.INSTANCE.exampleItem3().getText();
		item3caption.setDescription(item3text);
		String item4text = ReactionTopViewResources.INSTANCE.exampleItem4().getText();
		item4caption.setDescription(item4text);
		
		item1caption.setTextColor("black");
		item2caption.setTextColor("white");
		item3caption.setTextColor("white");
		item4caption.setTextColor("green");
		
		item1caption.setTitle("");
		item2caption.setTitle("");
		item3caption.setTitle("");
		item4caption.setTitle("");
		
		links = new TopPageLinks();
		String sessionid = Cookies.getCookie("sid");
		String user = Cookies.getCookie("user");
		
		profile.setVisible(false);
		username.setVisible(false);
		
		if(username != null) {
			LoginServiceAsync async = LoginService.Util.getInstance();
			CheckSessionLoginCallback callback = new CheckSessionLoginCallback(user, this);
			async.loginFromSessionServer(callback);
		} else {
			setLoggedOut();
		}

		String descriptiontext = ReactionTopViewResources.INSTANCE.chemconnectdescription().getText();
		String title = "ChemConnect: Interconnecting Chemical Data";
		firstdescription.setTitle(title);
		firstdescription.setDescription(descriptiontext);

		String description2text = ReactionTopViewResources.INSTANCE.firstdescription().getText();
		String title2 = "The Very Open Data Project";
		seconddescription.setDescription(description2text);
		seconddescription.setTitle(title2);
		
		linkWindow.setTitle(interfaceconstants.linkwindowtitle());
		
		//userCreateWindow.setTitle(interfaceconstants.createlogintitle());
		accountname.setPlaceholder(interfaceconstants.loginaccountnameplaceholder());
		btnCreate.setText(interfaceconstants.createbuttontext());
		userpassword.setPlaceholder(interfaceconstants.loginpasswordplaceholder());
		btnLogin.setText(interfaceconstants.loginbutton());
		logintitle.setDescription(interfaceconstants.createlogintitle());
		
		links = new TopPageLinks(username.getText());
		links.setPresenter(listener);
		linkWindow.add(links);
		
		newuser = new CreateNewUser();
		userCreateWindow.add(newuser);
	}
	public void setLoggedIn() {

		toplogout.setVisible(true);
		//toplogin.setVisible(false);
		
		profile.setVisible(true);
		String name = Cookies.getCookie("user");
		if(name != null) {
			username.setVisible(true);
			username.setText(name);
		}
		loginrow.setVisible(false);
		createLogin.setVisible(false);
		linkmenu.setVisible(true);
	}
	
	@Override
	public void setLoggedOut() {
		super.setLoggedOut();
		toplogout.setVisible(false);
		//toplogin.setVisible(true);
		profile.setVisible(false);
		Cookies.removeCookie("user");
		Cookies.removeCookie("sid");
		username.setVisible(false);
		loginrow.setVisible(true);
		linkmenu.setVisible(false);
	}

	@UiHandler("toplogout")
	void onTopLogoutClick(ClickEvent e) {
		logout.logout(this);
		setLoggedOut();
	
	}
	@UiHandler("profile")
	void onProfileClick(ClickEvent e) {
		String name = Cookies.getCookie("user");
		UserContactInput user = new UserContactInput(name);
		MaterialModal modal = new MaterialModal();
		modal.add(user);
		modal.setType(ModalType.FIXED_FOOTER);
		modal.openModal();
		FillInUserContactInputCallback callback = new FillInUserContactInputCallback(user);
		StoreDescriptionDataAsync async = StoreDescriptionData.Util.getInstance();
		async.getUserDescriptionData(name, callback);
	}
	@UiHandler("btnLogin")
	void onLoginClick(ClickEvent e) {

		LoginServiceAsync async = LoginService.Util.getInstance();
		//((ServiceDefTarget) async).setServiceEntryPoint("loginservice");
		String msg = "Username: " + accountname.getText() + ", password: " + userpassword.getText();
		LoginCallback callback = new LoginCallback(this);
		MaterialToast.fireToast(msg);
		async.loginServer(accountname.getText(), userpassword.getText(),callback);
	}
	@UiHandler("btnCreate")
	void onCreateLoginClick(ClickEvent e) {
		if (create) {
			userCreateWindow.openWindow();
		}
	}

	@UiHandler("linkmenu")
	void onLinkClick(ClickEvent e) {
		MaterialToast.fireToast("Links:");
		linkWindow.openWindow();
		MaterialToast.fireToast("Links");		
	}
	
	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
		links.setPresenter(listener);
	}
	public void setUser(UserDTO user) {
		super.setUser(user);
		links.setUser(user);
		setLoggedIn();
	}
}
