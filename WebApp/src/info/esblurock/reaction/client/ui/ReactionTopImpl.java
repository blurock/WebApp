package info.esblurock.reaction.client.ui;

import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.StoreDescriptionData;
import info.esblurock.reaction.client.StoreDescriptionDataAsync;
import info.esblurock.reaction.client.activity.place.ReactionInformationPlace;
import info.esblurock.reaction.client.panel.TopInformationModal;
import info.esblurock.reaction.client.panel.contact.UserContactInput;
import info.esblurock.reaction.client.resources.InputConstants;
import info.esblurock.reaction.client.resources.InterfaceConstants;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ReactionTopImpl extends UiImplementationBase implements ReactionTopView {

	private static ReactionTopImplUiBinder uiBinder = GWT
			.create(ReactionTopImplUiBinder.class);

	interface ReactionTopImplUiBinder extends UiBinder<Widget, ReactionTopImpl> {
	}
	
	InputConstants inputConstants = GWT.create(InputConstants.class);
	InterfaceConstants interfaceconstants = GWT.create(InterfaceConstants.class);
	@UiField
	HTMLPanel toppanel;
	@UiField
	HorizontalPanel firstdescription;
	@UiField
	HorizontalPanel seconddescription;
	@UiField
	HorizontalPanel datarelations;
	@UiField
	HorizontalPanel topsummary;

	@UiField
	MaterialLink profile;
	@UiField
	MaterialLabel username;
	@UiField
	MaterialLink linkmenu;
	@UiField
	MaterialLink toplogout;
	@UiField
	MaterialLink sideprofile;
	@UiField
	MaterialLink sidelinkmenu;
	@UiField
	MaterialLink sidetoplogout;
	@UiField
	MaterialLink topinformation;
	@UiField
	MaterialLink information;

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
	MaterialButton getstarted;
	
	@UiField
	MaterialTitle item1caption;
	@UiField
	MaterialTitle item2caption;
	@UiField
	MaterialTitle item3caption;
	@UiField
	MaterialTitle item4caption;

	@UiField
	HorizontalPanel rdfdescription;

	@UiField
	MaterialWindow linkWindow;
	@UiField
	MaterialWindow userCreateWindow;
	
	@UiField
	MaterialWindow loginWindow;
	
	@UiField 
	MaterialRow loginrow;
	@UiField
	MaterialPanel createLogin;
	
	@UiField
	MaterialModalContent usermodalcontent;
	@UiField
	MaterialModal usermodal;
	@UiField
	MaterialButton userclose;
	
	MaterialPanel mpanel;

	private Presenter listener;
	private ClientLogout logout = new ClientLogout();
	TopPageLinks links;
	boolean create = true;
	CreateNewUser newuser;
	TopInformationModal topinfomodal;

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
		
		item1caption.setTitle("");
		item2caption.setTitle("");
		item3caption.setTitle("");
		item4caption.setTitle("");
		
		topinformation.setText(interfaceconstants.help());
		
		links = new TopPageLinks();
		//String sessionid = Cookies.getCookie("sid");
		String user = Cookies.getCookie("user");
		
		profile.setVisible(false);
		username.setVisible(false);
		sideprofile.setVisible(false);
		
		topinfomodal = new TopInformationModal(this);
		toppanel.add(topinfomodal);
		
		if(username != null) {
			LoginServiceAsync async = LoginService.Util.getInstance();
			CheckSessionLoginCallback callback = new CheckSessionLoginCallback(user, this);
			async.loginFromSessionServer(callback);
		} else {
			setLoggedOut();
		}

		String descriptiontext = ReactionTopViewResources.INSTANCE.chemconnectdescription().getText();
		String title = "<h1>ChemConnect: Interconnecting Chemical Data</h1>";
		HTML connecthtml = new HTML(title + descriptiontext);
		firstdescription.add(connecthtml);

		String topsummarytext = ReactionTopViewResources.INSTANCE.topsummary().getText();
		HTML topsummaryhtml = new HTML(topsummarytext);
		topsummary.add(topsummaryhtml);
		
		String description2text = ReactionTopViewResources.INSTANCE.firstdescription().getText();
		HTML descr2html = new HTML(description2text);
		seconddescription.add(descr2html);
		
		String datarelationstext = ReactionTopViewResources.INSTANCE.datarelations().getText();
		HTML datarelationshtml = new HTML(datarelationstext);
		datarelations.add(datarelationshtml);
		
		String searchtext = ReactionTopViewResources.INSTANCE.usersearching().getText();
		HTML searchhtml = new HTML(searchtext);
		rdfdescription.add(searchhtml);
		
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
		linkmenu.setVisible(true);
		sidetoplogout.setVisible(true);
		sidelinkmenu.setVisible(true);
		
		profile.setVisible(true);
		sideprofile.setVisible(true);
		String name = Cookies.getCookie("user");
		if(name != null) {
			username.setVisible(true);
			username.setText(name);
		}
		loginrow.setVisible(false);
		createLogin.setVisible(false);
		getstarted.setVisible(false);
		getstarted.setEnabled(false);
		linkWindow.openWindow();
	}
	
	@Override
	public void setLoggedOut() {
		super.setLoggedOut();
		Cookies.removeCookie("user");
		Cookies.removeCookie("sid");

		toplogout.setVisible(false);
		profile.setVisible(false);
		username.setVisible(false);
		linkmenu.setVisible(false);
		sidetoplogout.setVisible(false);
		sideprofile.setVisible(false);
		sidelinkmenu.setVisible(false);

		loginrow.setVisible(true);

		getstarted.setVisible(true);
		getstarted.setEnabled(true);
	}

	@UiHandler("getstarted")
	void onGetStarted(ClickEvent e) {
		loginWindow.openWindow();
	}
	@UiHandler("toplogout")
	void onTopLogoutClick(ClickEvent e) {
		logout.logout(this);
		setLoggedOut();
	
	}
	@UiHandler("sidetoplogout")
	void onSideTopLogoutClick(ClickEvent e) {
		logout.logout(this);
		setLoggedOut();
	
	}
	void profile() {
		String name = Cookies.getCookie("user");
		UserContactInput user = new UserContactInput(name);
		usermodalcontent.add(user);
		usermodal.openModal();
		FillInUserContactInputCallback callback = new FillInUserContactInputCallback(user);
		StoreDescriptionDataAsync async = StoreDescriptionData.Util.getInstance();
		async.getUserDescriptionData(name, callback);
		
	}
	@UiHandler("profile")
	void onProfileClick(ClickEvent e) {
		profile();
	}
	@UiHandler("sideprofile")
	void onSideProfileClick(ClickEvent e) {
		profile();
	}
	@UiHandler("userclose")
	void onUserProfileClose(ClickEvent e) {
		usermodalcontent.clear();
		usermodal.closeModal();
	}
	@UiHandler("btnLogin")
	void onLoginClick(ClickEvent e) {

		LoginServiceAsync async = LoginService.Util.getInstance();
		String msg = "Login with username: " + accountname.getText();
		LoginCallback callback = new LoginCallback(this);
		MaterialToast.fireToast(msg);
		async.loginServer(accountname.getText(), userpassword.getText(),callback);
		loginWindow.closeWindow();
	}
	@UiHandler("btnCreate")
	void onCreateLoginClick(ClickEvent e) {
		if (create) {
			loginWindow.closeWindow();
			userCreateWindow.openWindow();
		}
	}

	@UiHandler("linkmenu")
	void onLinkClick(ClickEvent e) {
		linkWindow.openWindow();
	}
	@UiHandler("sidelinkmenu")
	void onSideLinkClick(ClickEvent e) {
		linkWindow.openWindow();
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
	@UiHandler("information")
	void InformationClick(ClickEvent e) {
		topinfomodal.openModal();
	}
	@UiHandler("topinformation")
	void onTopInformationClick(ClickEvent e) {
		topinfomodal.openModal();
	}
	public void openDemoInformationPage() {
		listener.goTo(new ReactionInformationPlace(user.getName()));
	}
	
}
