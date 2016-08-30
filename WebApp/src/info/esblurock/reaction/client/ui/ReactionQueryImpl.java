package info.esblurock.reaction.client.ui;

import info.esblurock.reaction.client.panel.QueryAndResultPanel;
import info.esblurock.reaction.client.panel.QueryNavBar;
import info.esblurock.reaction.client.ui.login.AsyncGetUserData;
import info.esblurock.reaction.client.ui.login.UserDTO;
import info.esblurock.reaction.client.ui.modal.QueryLinks;
import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialToast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class ReactionQueryImpl extends UiImplementationBase implements ReactionQueryView {

	private static ReactionQueryImplUiBinder uiBinder = GWT
			.create(ReactionQueryImplUiBinder.class);

	interface ReactionQueryImplUiBinder extends
			UiBinder<Widget, ReactionQueryImpl> {
	}


	
	Presenter listener;
	String name;

	@UiField
	HTMLPanel wholepanel;
	@UiField
	MaterialWindow linkwindow;

	
	MaterialContainer content;
	QueryAndResultPanel query;
	QueryNavBar navbar;
	QueryLinks links;
	
	public ReactionQueryImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		init("Search");
	}

	public ReactionQueryImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		init(firstName);
	}

	private void init(String firstName) {
		query = new QueryAndResultPanel();
		content = new MaterialContainer();
		content.add(query);
		wholepanel.add(content);
		navbar = new QueryNavBar(firstName,query,this);
		wholepanel.add(navbar);
		content = new MaterialContainer();
		content.add(query);
		wholepanel.add(navbar);
		wholepanel.add(content);
		links = new QueryLinks();
		linkwindow.add(links);		
	}

	@Override
	public void setName(String helloName) {
		this.name = helloName;

	}
	public void linkWindow() {
		linkwindow.openWindow();
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
		navbar.setPresenter(listener);
		query.setPresenter(listener);
		links.setPresenter(listener);
		AsyncGetUserData async = new AsyncGetUserData(this);
	}
	@Override
	public void setUser(UserDTO user) {
		super.setUser(user);
		navbar.setUser(user);
		query.setUser(user);
		links.setUser(user);
	}

}
