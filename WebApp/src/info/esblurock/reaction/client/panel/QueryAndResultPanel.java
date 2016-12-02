package info.esblurock.reaction.client.panel;

import info.esblurock.reaction.client.ui.UiImplementationBase;
import info.esblurock.reaction.client.ui.ReactionQueryView.Presenter;
import info.esblurock.reaction.client.ui.login.UserDTO;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLabel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class QueryAndResultPanel extends UiImplementationBase implements HasText {
	

	private static QueryAndResultPanelUiBinder uiBinder = GWT
			.create(QueryAndResultPanelUiBinder.class);

	interface QueryAndResultPanelUiBinder extends
			UiBinder<Widget, QueryAndResultPanel> {
	}
	@UiField
	MaterialCollapsible topoftree;
	
	@UiField
	HTMLPanel toppanel;
	
	String text;
	//MaterialCollapsibleItem search0;
	Presenter listener;
	
	public QueryAndResultPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		init("Search");
	}
	public QueryAndResultPanel(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		init(firstName);
	}
	private void init(String text) {
		/*
		search0 = new MaterialCollapsibleItem();
		search0.setText(text);
		*/
	}
	public MaterialCollapsible getQueryTop() {
		return topoftree;
	}
	public HTMLPanel getTopPanel() {
		return toppanel;
	}
/*	
	public MaterialCollapsibleItem getQueryTop(String text) {
		search0 = new MaterialCollapsibleItem();
		search0.setText(text);
		topoftree.add(search0);
		return search0;
	}
*/
	
	@Override
	public void setText(String text) {
		this.text = text;
	}

	
	@Override
	public String getText() {
		return this.text;
	}
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}
	@Override
	public void setUser(UserDTO user) {
		super.setUser(user);
	}

}
