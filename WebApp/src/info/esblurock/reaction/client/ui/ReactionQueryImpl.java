package info.esblurock.reaction.client.ui;

import info.esblurock.reaction.client.panel.QueryAndResultPanel;
import info.esblurock.reaction.client.panel.QueryNavBar;
import gwt.material.design.client.ui.MaterialContainer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class ReactionQueryImpl extends Composite implements ReactionQueryView {

	private static ReactionQueryImplUiBinder uiBinder = GWT
			.create(ReactionQueryImplUiBinder.class);

	interface ReactionQueryImplUiBinder extends
			UiBinder<Widget, ReactionQueryImpl> {
	}


	
	Presenter listener;
	String name;

	@UiField
	HTMLPanel wholepanel;

	MaterialContainer content;
	QueryAndResultPanel query;
	QueryNavBar navbar;
	
	public ReactionQueryImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		query = new QueryAndResultPanel();
		navbar = new QueryNavBar(query.getQueryTop());
		init(query,navbar);
	}

	public ReactionQueryImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		QueryAndResultPanel query = new QueryAndResultPanel(firstName);
		QueryNavBar navbar = new QueryNavBar(firstName, query.getQueryTop());
		init(query,navbar);
	}

	private void init(QueryAndResultPanel query, QueryNavBar navbar) {
		content = new MaterialContainer();
		content.add(query);
		wholepanel.add(navbar);
		wholepanel.add(content);
	}

	@Override
	public void setName(String helloName) {
		this.name = helloName;

	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
		navbar.setPresenter(listener);
		query.setPresenter(listener);
	}


}
