package info.esblurock.reaction.client.panel;

import info.esblurock.reaction.client.panel.query.BasicObjectSearchCallback;
import info.esblurock.reaction.client.panel.query.QueryPath;
import info.esblurock.reaction.client.panel.query.QueryPathElement;
import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.client.panel.query.ReactionSearchServiceAsync;
import info.esblurock.reaction.client.panel.repository.RepositoryTopNode;
import info.esblurock.reaction.client.panel.repository.actions.RetrieveDataSetPathCallback;
import info.esblurock.reaction.client.ui.ReactionQueryImpl;
import info.esblurock.reaction.client.ui.ReactionQueryView.Presenter;
import info.esblurock.reaction.client.ui.login.UserDTO;
import gwt.material.design.client.events.SearchFinishEvent;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialSearch;
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

	String repositoryLabel = "repository";
	
	/*
	 * @UiField TextBox search;
	 * 
	 * @UiField MaterialButton submittopquery;
	 * 
	 * @UiField MaterialLink linkmenu;
	 */
	QueryAndResultPanel qrpanel;
	MaterialCollapsible topsearch;
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
				String searchText = txtSearch.getText();
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
		this.topsearch = qrpanel.getQueryTop();
		
		QueryPath path = new QueryPath(QueryPathElement.SEARCHSTRING,text);
		if(searchText.trim().toLowerCase().startsWith(repositoryLabel)) {
			ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
			RetrieveDataSetPathCallback callback = new RetrieveDataSetPathCallback(topsearch);
			async.getRepositoryDataSources(callback);
		} else {
			BasicObjectSearchCallback callback = new BasicObjectSearchCallback(path, topsearch);
			ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
			async.searchedRegisteredQueries(text, callback);
		}
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

	@UiHandler("info")
	void infoClicked(ClickEvent e) {
		top.showSearchHelpInformation();
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
	}

}
