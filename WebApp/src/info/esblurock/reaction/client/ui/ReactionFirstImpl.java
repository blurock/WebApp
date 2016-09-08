package info.esblurock.reaction.client.ui;

import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialHeader;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.activity.place.ReactionTopPlace;
import info.esblurock.reaction.client.panel.StandardNavigation;
import info.esblurock.reaction.client.panel.inputs.place.Place;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.util.DefaultTextOutput;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class ReactionFirstImpl extends Composite implements ReactionFirstView {

	private static ReactionFirstImplUiBinder uiBinder = GWT
			.create(ReactionFirstImplUiBinder.class);

	interface ReactionFirstImplUiBinder extends
			UiBinder<Widget, ReactionFirstImpl> {
	}

	Presenter listener;
	String name;

	@UiField
	HTMLPanel wholepanel;
	@UiField
	MaterialContainer content;
	@UiField
	MaterialHeader header;

	public ReactionFirstImpl() {
		setName("First");
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		StandardNavigation nav = new StandardNavigation();
		nav.setPresenter(listener);
		nav.setContent(content);
		header.add(nav);
	}

	private void createNext() {
		Widget widget = uiBinder.createAndBindUi(this);
		init();
		initWidget(widget);
		
	}
	private void checkWithServerIfSessionIdIsStillLegal() {
		String sessionID = Cookies.getCookie("sid");
		if (sessionID == null) {
			listener.goTo(new ReactionTopPlace(sessionID));
		} else {
		}
	}

	private void displayLoginWindow() {
		MaterialToast.fireToast("failed login");
	}
	
	private void handleHistoryToken(String token) {
		if (!"".equals(token)) {
			Place place = Place.valueOf(token);
			changeNav(place);
		} else {
			Window.alert("No token found ");
		}
		
	}

	private void changeNav(Place place) {
		Window.scrollTo(0, 0);
		content.clear();
		content.add(place.getContent());
	}
	@Override
	public void setName(String helloName) {
		this.name = helloName;

	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}
}
