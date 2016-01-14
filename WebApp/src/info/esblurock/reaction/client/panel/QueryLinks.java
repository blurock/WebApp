package info.esblurock.reaction.client.panel;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import info.esblurock.reaction.client.activity.place.ReactionFirstPlace;
import info.esblurock.reaction.client.activity.place.ReactionTopPlace;
import info.esblurock.reaction.client.resources.InterfaceConstants;
import info.esblurock.reaction.client.ui.ReactionQueryView.Presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class QueryLinks extends Composite implements HasText {

	private static QueryLinksUiBinder uiBinder = GWT
			.create(QueryLinksUiBinder.class);

	interface QueryLinksUiBinder extends UiBinder<Widget, QueryLinks> {
	}

	@UiField
	MaterialLink top;
	@UiField
	MaterialLink dataentry;
	@UiField
	MaterialButton close;
	
	private Presenter listener;

	InterfaceConstants interfaceconstants = GWT.create(InterfaceConstants.class);

	public QueryLinks() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	public QueryLinks(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		top.setText(interfaceconstants.toplinksquery());
		dataentry.setText(interfaceconstants.toplinksentry());
	}
	
	@UiHandler("dataentry")
	void onQueryClick(ClickEvent e) {
		String username = Cookies.getCookie("user");
		Window.alert("dataentry");
		listener.goTo(new ReactionFirstPlace(username));
	}
	@UiHandler("top")
	void onEntryClick(ClickEvent e) {
		String username = Cookies.getCookie("user");
		Window.alert("top");
		listener.goTo(new ReactionTopPlace(username));
	}
	@UiHandler("close")
	void onClose(ClickEvent e) {
		MaterialModal.closeModal();
	}

	public void setText(String text) {
		
	}

	public String getText() {
		return null;
	}
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
