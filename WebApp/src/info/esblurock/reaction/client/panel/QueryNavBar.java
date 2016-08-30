package info.esblurock.reaction.client.panel;

import info.esblurock.reaction.client.panel.query.BasicObjectSearchCallback;
import info.esblurock.reaction.client.panel.query.BasicSearchCallback;
import info.esblurock.reaction.client.panel.query.QueryPath;
import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.client.panel.query.ReactionSearchServiceAsync;
import info.esblurock.reaction.client.ui.ReactionQueryImpl;
import info.esblurock.reaction.client.ui.ReactionQueryView.Presenter;
import info.esblurock.reaction.client.ui.login.UserDTO;
import gwt.material.design.addins.client.window.MaterialWindow;
//import gwt.material.design.addins.client.window.MaterialWindow;
import gwt.material.design.client.events.SearchFinishEvent;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class QueryNavBar extends Composite implements HasText {

	private static QueryNavBarUiBinder uiBinder = GWT.create(QueryNavBarUiBinder.class);

	interface QueryNavBarUiBinder extends UiBinder<Widget, QueryNavBar> {
	}

	@UiField
	MaterialNavBar navBar;
	@UiField
	MaterialLink btnSearch;

	@UiField
	MaterialNavBar navBarSearch;
	@UiField
	MaterialSearch txtSearch;
	
	@UiField
	MaterialLink linkButton;


	
	/*
	 * @UiField TextBox search;
	 * 
	 * @UiField MaterialButton submittopquery;
	 * 
	 * @UiField MaterialLink linkmenu;
	 */
	MaterialCollapsibleItem search0;
	QueryAndResultPanel qrpanel;
	Presenter listener;
	String name;
	UserDTO user;
	final int ASCIICR = 13;
	ReactionQueryImpl top;

	public QueryNavBar(QueryAndResultPanel qrpanel, ReactionQueryImpl top) {
		initWidget(uiBinder.createAndBindUi(this));
		init("Search", qrpanel,top);
	}

	public QueryNavBar(String firstName, QueryAndResultPanel qrpanel, ReactionQueryImpl top) {
		initWidget(uiBinder.createAndBindUi(this));
		init(firstName, qrpanel,top);
	}

	private void init(String firstName, QueryAndResultPanel qrpanel, ReactionQueryImpl top) {
		this.top = top;
		name = firstName;
		this.qrpanel = qrpanel;
		navBar.setVisible(true);
		navBarSearch.setVisible(false);
		// Add Close Handler
		txtSearch.addCloseHandler(new CloseHandler<String>() {
			@Override
			public void onClose(CloseEvent<String> event) {
				navBar.setVisible(true);
				navBarSearch.setVisible(false);
			}
		});
		// Add Finish Handler
		txtSearch.addSearchFinishHandler(new SearchFinishEvent.SearchFinishHandler() {
			@Override
			public void onSearchFinish(SearchFinishEvent event) {
				MaterialToast.fireToast("Search Finish");
				String searchText = txtSearch.getText();
				MaterialToast.fireToast("Search: '" + searchText);
			}
		});
		txtSearch.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == ASCIICR) {
					doSearch(txtSearch.getText());
				}
			}

		});
	}

	private void doSearch(String text) {
		String searchText = txtSearch.getText();
		MaterialToast.fireToast("Start Search: '" + searchText + "'");
		this.search0 = qrpanel.getQueryTop(text);
		//qrpanel.setText(text);
		QueryPath path = new QueryPath(text);
		BasicObjectSearchCallback callback = new BasicObjectSearchCallback(path, search0);
		ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
		async.singleKeyQuery(text, callback);
	}

	@Override
	public String getText() {
		return name;
	}

	@Override
	public void setText(String text) {
		name = text;
	}

	@UiHandler("btnSearch")
	void onSearch(ClickEvent e) {
		navBar.setVisible(false);
		navBarSearch.setVisible(true);
	}

	@UiHandler("linkButton")
	void onLinkClick(ClickEvent e) {
		top.linkWindow();
	}
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	public void setUser(UserDTO user) {
		this.user = user;
		String umsg = user.getName() + " @Host=" + user.getIP();
		// navuser.setText(umsg);
	}

}
