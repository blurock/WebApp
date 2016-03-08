package info.esblurock.reaction.client.panel;

import info.esblurock.reaction.client.panel.query.BasicObjectSearchCallback;
import info.esblurock.reaction.client.panel.query.BasicSearchCallback;
import info.esblurock.reaction.client.panel.query.QueryPath;
import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.client.panel.query.ReactionSearchServiceAsync;
import info.esblurock.reaction.client.ui.ReactionQueryView.Presenter;
import info.esblurock.reaction.client.ui.login.UserDTO;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialModal.TYPE;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class QueryNavBar extends Composite implements HasText {

	private static QueryNavBarUiBinder uiBinder = GWT
			.create(QueryNavBarUiBinder.class);

	interface QueryNavBarUiBinder extends UiBinder<Widget, QueryNavBar> {
	}


	@UiField
	MaterialNavBar navbar;
	@UiField
	TextBox search;
	@UiField
	MaterialButton submittopquery;
	@UiField
	MaterialLink linkmenu;
	@UiField
	MaterialLink navuser;
	
	MaterialCollapsibleItem search0;
	QueryAndResultPanel qrpanel;
	Presenter listener;
	QueryLinks links;
	String name;
	UserDTO user;
	
	
	public QueryNavBar(QueryAndResultPanel qrpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.search0 = qrpanel.getQueryTop();
		this.qrpanel = qrpanel;
		links = new QueryLinks();
		
	}
	public QueryNavBar(String firstName, QueryAndResultPanel qrpanel) {
		initWidget(uiBinder.createAndBindUi(this));
		name = firstName;
		this.search0 = qrpanel.getQueryTop();
		this.qrpanel = qrpanel;
		links = new QueryLinks();
	}

	@Override
	public String getText() {
		return name;
	}

	@Override
	public void setText(String text) {
		name = text;
	}
	@UiHandler("submittopquery")
	void onClick(ClickEvent e) {
		/*
		ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
		String title = "Keyword Search: " + search.getText();
		search0.setTitle(title);
		QueryPath path = new QueryPath(search.getText());
		async.basicSearch(search.getText(), new BasicSearchCallback(path, search0));
		*/
		
		Window.alert("Query");
		
		qrpanel.setText(search.getText());
		QueryPath path = new QueryPath(search.getText());
		BasicObjectSearchCallback callback = new BasicObjectSearchCallback(path, search0);
		ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
		async.singleKeyQuery(search.getText(), callback);

	}

	@UiHandler("linkmenu")
	void onLinkClick(ClickEvent e) {
		MaterialModal.showModal(links,  TYPE.BOTTOM_SHEET);
	}
	public void setPresenter(Presenter listener) {
		this.listener = listener;
		links.setPresenter(listener);
	}
	public void setUser(UserDTO user) {
		this.user = user;
		links.setUser(user);
		String umsg = user.getName() + " @Host=" + user.getIP();
		navuser.setText(umsg);
	}
	
}
