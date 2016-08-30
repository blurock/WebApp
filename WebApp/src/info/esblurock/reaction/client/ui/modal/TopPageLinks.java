package info.esblurock.reaction.client.ui.modal;

import info.esblurock.reaction.client.activity.place.ReactionFirstPlace;
import info.esblurock.reaction.client.activity.place.ReactionQueryPlace;
import info.esblurock.reaction.client.resources.InterfaceConstants;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.ui.ReactionTopView.Presenter;
import info.esblurock.reaction.client.ui.login.UserDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class TopPageLinks extends Composite implements HasText {

	private static TopPageLinksUiBinder uiBinder = GWT
			.create(TopPageLinksUiBinder.class);

	interface TopPageLinksUiBinder extends UiBinder<Widget, TopPageLinks> {
	}

	private String datainputS = "DataInput";
	private String queryS = "Query";
	@UiField
	MaterialLink query;
	@UiField
	MaterialLink dataentry;
	@UiField
	MaterialCollection collection;
	
	private Presenter listener;
	UserDTO user;
	
	InterfaceConstants interfaceconstants = GWT.create(InterfaceConstants.class);

	public TopPageLinks() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	public TopPageLinks(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	private void init() {
		collection.setHeader(interfaceconstants.linkwindowtitle());
		query.setText(interfaceconstants.toplinksquery());
		dataentry.setText(interfaceconstants.toplinksentry());
	}
	
	@UiHandler("dataentry")
	void onQueryClick(ClickEvent e) {
		listener.goTo(new ReactionFirstPlace(user.getName()));
	}
	@UiHandler("query")
	void onEntryClick(ClickEvent e) {
		MaterialToast.fireToast("TopPageLinks: goTo ReactionQueryPlace: " + user.getName());
		listener.goTo(new ReactionQueryPlace(user.getName()));
	}

	public void setText(String text) {
	}

	public String getText() {
		return null;
	}
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}
	public void setUser(UserDTO user) {
		this.user = user;
		dataentry.setVisible(user.checkLevel(datainputS));
		query.setVisible(user.checkLevel(queryS));
	}
}
