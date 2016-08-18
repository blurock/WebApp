package info.esblurock.reaction.client.ui;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialSlideItem;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialModal.TYPE;
import info.esblurock.reaction.client.StoreDescriptionData;
import info.esblurock.reaction.client.StoreDescriptionDataAsync;
import info.esblurock.reaction.client.panel.contact.UserContactInput;
import info.esblurock.reaction.client.ui.login.CheckSessionLoginCallback;
import info.esblurock.reaction.client.ui.login.ClientLogout;
import info.esblurock.reaction.client.ui.login.LoginModal;
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

	@UiField
	MaterialTitle firstdescription;

	@UiField
	MaterialTitle seconddescription;
		
	@UiField
	MaterialNavBar navbar;
	@UiField
	MaterialLink linkmenu;
	@UiField
	MaterialLink profile;
	
	@UiField
	MaterialSlideItem item1;
	@UiField
	MaterialSlideItem item2;
	@UiField
	MaterialSlideItem item3;
	@UiField
	MaterialSlideItem item4;
	
	@UiField
	MaterialLink toplogin;
	
	@UiField
	MaterialLink toplogout;
	
	MaterialPanel mpanel;

	private Presenter listener;
	private ClientLogout logout = new ClientLogout();
	private LoginModal popup;
	TopPageLinks links;
	
	public ReactionTopImpl() {
		initWidget(uiBinder.createAndBindUi(this));

		String item1text = ReactionTopViewResources.INSTANCE.exampleItem1().getText();
		item1.setDescription(item1text);
		String item2text = ReactionTopViewResources.INSTANCE.exampleItem2().getText();
		item2.setDescription(item2text);
		String item3text = ReactionTopViewResources.INSTANCE.exampleItem3().getText();
		item3.setDescription(item3text);
		String item4text = ReactionTopViewResources.INSTANCE.exampleItem4().getText();
		item4.setDescription(item4text);
		
		item1.setTextColor("black");
		item2.setTextColor("white");
		item3.setTextColor("white");
		item4.setTextColor("green");
		
		item1.setTitle("");
		item2.setTitle("");
		item3.setTitle("");
		item4.setTitle("");
		
		popup = new LoginModal(this);
		links = new TopPageLinks();
		String sessionid = Cookies.getCookie("sid");
		String username = Cookies.getCookie("user");
		
		profile.setVisible(false);
		
		if(username != null) {
			LoginServiceAsync async = LoginService.Util.getInstance();
			CheckSessionLoginCallback callback = new CheckSessionLoginCallback(username, this);
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
		
	}
	public void setLoggedIn() {
		toplogout.setVisible(true);
		toplogin.setVisible(false);
		linkmenu.setVisible(true);
		profile.setVisible(true);
	}
	@Override
	public void setLoggedOut() {
		super.setLoggedOut();
		toplogout.setVisible(false);
		toplogin.setVisible(true);
		linkmenu.setVisible(false);	
		profile.setVisible(false);
		Cookies.removeCookie("user");
		Cookies.removeCookie("sid");

	}
	@UiHandler("toplogin")
	void onTopLoginClick(ClickEvent e) {
		MaterialModal.showModal(popup, TYPE.BOTTOM_SHEET);
	}

	@UiHandler("toplogout")
	void onTopLogoutClick(ClickEvent e) {
		logout.logout(this);
		setLoggedOut();
	}

	@UiHandler("linkmenu")
	void onLinkClick(ClickEvent e) {
		MaterialModal.showModal(links, TYPE.DEFAULT);
	}
	@UiHandler("profile")
	void onProfileClick(ClickEvent e) {
		String name = Cookies.getCookie("user");
		UserContactInput user = new UserContactInput(name);
		MaterialModal.showModal(user, TYPE.FIXED_FOOTER);
		FillInUserContactInputCallback callback = new FillInUserContactInputCallback(user);
		StoreDescriptionDataAsync async = StoreDescriptionData.Util.getInstance();
		async.getUserDescriptionData(name, callback);
	}


	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
		links.setPresenter(listener);
		popup.setPresenter(listener);
	}
	public void setUser(UserDTO user) {
		super.setUser(user);
		links.setUser(user);
		setLoggedIn();
	}
}
