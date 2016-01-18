package info.esblurock.reaction.client.panel;

import info.esblurock.reaction.client.ui.UiImplementationBase;
import info.esblurock.reaction.client.ui.ReactionQueryView.Presenter;
import info.esblurock.reaction.client.ui.login.UserDTO;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLabel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class QueryAndResultPanel extends UiImplementationBase implements HasText {
	

	private static QueryAndResultPanelUiBinder uiBinder = GWT
			.create(QueryAndResultPanelUiBinder.class);

	interface QueryAndResultPanelUiBinder extends
			UiBinder<Widget, QueryAndResultPanel> {
	}
	Presenter listener;
	
	public QueryAndResultPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	@UiField
	MaterialCollapsibleItem search0;
	@UiField
	MaterialLabel searchlabel;
	
	public QueryAndResultPanel(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public MaterialCollapsibleItem getQueryTop() {
		return search0;
	}
	
	
	@Override
	public void setText(String text) {
		//submittopquery.setText(text);
	}

	@Override
	public String getText() {
		//return submittopquery.getText();
		return null;
	}
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}
	@Override
	public void setUser(UserDTO user) {
		super.setUser(user);
	}

}
