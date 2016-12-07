package info.esblurock.reaction.client.panel;

import info.esblurock.reaction.client.panel.query.BasicObjectSearchAsTreeCallback;
import info.esblurock.reaction.client.panel.query.BasicObjectSearchCallback;
import info.esblurock.reaction.client.panel.query.QueryPath;
import info.esblurock.reaction.client.panel.query.QueryPathElement;
import info.esblurock.reaction.client.panel.query.ReactionSearchService;
import info.esblurock.reaction.client.panel.query.ReactionSearchServiceAsync;
import info.esblurock.reaction.client.panel.query.SearchPanel;
import info.esblurock.reaction.client.panel.repository.actions.RetrieveDataSetPathCallback;
import info.esblurock.reaction.client.ui.ReactionQueryImpl;
import info.esblurock.reaction.client.ui.ReactionQueryView.Presenter;
import info.esblurock.reaction.client.ui.login.UserDTO;
import gwt.material.design.client.constants.RadioButtonType;
import gwt.material.design.client.constants.SideNavType;
import gwt.material.design.client.events.SearchFinishEvent;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialSearch;
import gwt.material.design.client.ui.MaterialSideNav;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialRadioButton;

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
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class QueryNavBar extends Composite implements HasText {

	private static QueryNavBarUiBinder uiBinder = GWT.create(QueryNavBarUiBinder.class);

	interface QueryNavBarUiBinder extends UiBinder<Widget, QueryNavBar> {
	}

	//@UiField
	//MaterialNavBar navBar;
	@UiField
	MaterialLink btnSearch;

	@UiField
	MaterialSearch txtSearch;
	@UiField
	MaterialCheckBox ashierarchy;
	@UiField
	MaterialCheckBox astree;
	@UiField
	MaterialLink info;
	@UiField
	MaterialLink linkButton;

	String repositoryLabel = "repository";
	
	//boolean asTree;
	
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

	class SearchCloseHandler implements CloseHandler<String> {
		//MaterialCollapsible topsearch;
		public SearchCloseHandler() {
			//this.topsearch = topsearch;
		}
		@Override
		public void onClose(CloseEvent<String> event) {
			txtSearch.setText("");
			qrpanel.getTopPanel().clear();
			topsearch.clear();
		}
		
	}
	private void init(String firstName, QueryAndResultPanel qrpanel, ReactionQueryImpl top) {
		this.top = top;
		name = firstName;
		this.qrpanel = qrpanel;
		btnSearch.setVisible(false);
		txtSearch.setVisible(true);
		//sidenav.setType(SideNavType.PUSH);
		astree.setValue(true);
		ashierarchy.setValue(false);
		SearchCloseHandler handler = new SearchCloseHandler();
		// Add Close Handler
		txtSearch.addCloseHandler(handler);
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
			ReactionSearchServiceAsync async = ReactionSearchService.Util.getInstance();
			if(astree.getValue()) {
				HTMLPanel toppanel = qrpanel.getTopPanel();
				SearchPanel search = new SearchPanel(text,toppanel);
				toppanel.add(search);
				BasicObjectSearchAsTreeCallback callback = new BasicObjectSearchAsTreeCallback(path,search);
				async.searchedRegisteredQueries(text, "TopSearch",callback);				
			} else {
				BasicObjectSearchCallback callback = new BasicObjectSearchCallback(path, topsearch);
				async.searchedRegisteredQueries(text, "TopSearch",callback);				
			}
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
		btnSearch.setVisible(false);
		linkButton.setVisible(false);
		info.setVisible(false);
		txtSearch.setVisible(true);
		top.hideButtonInfoPanel();
	}

	@UiHandler("info")
	void infoClicked(ClickEvent e) {
		top.showSearchHelpInformation();
	}
	@UiHandler("linkButton")
	void onLinkClick(ClickEvent e) {
		top.linkWindow();
	}
	
	@UiHandler("ashierarchy")
	public void setAsHierarchy(ClickEvent e) {
		astree.setValue(false);
		ashierarchy.setValue(true);
		
	}
	@UiHandler("astree")
	public void setAsTree(ClickEvent e) {
		astree.setValue(true);
		ashierarchy.setValue(false);
		
	}
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	public void setUser(UserDTO user) {
		this.user = user;
		String umsg = user.getName() + " @Host=" + user.getIP();
	}

}
