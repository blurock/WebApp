package info.esblurock.reaction.client.panel;

import info.esblurock.reaction.client.activity.place.ReactionQueryPlace;
import info.esblurock.reaction.client.activity.place.ReactionTopPlace;
import info.esblurock.reaction.client.panel.inputs.place.Place;
import info.esblurock.reaction.client.resources.DescriptionConstants;
import info.esblurock.reaction.client.ui.ReactionFirstView.Presenter;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialToast;

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

public class StandardNavigation extends Composite implements HasText {

	private static StandardNavigationUiBinder uiBinder = GWT
			.create(StandardNavigationUiBinder.class);

	interface StandardNavigationUiBinder extends
			UiBinder<Widget, StandardNavigation> {
	}
	@UiField
	MaterialNavBrand toptext;
	
	@UiField
	MaterialLink inputorganization;
	@UiField
	MaterialLink inputcontact;
	@UiField
	MaterialLink chemkin;
	@UiField
	MaterialLink nasapolynomials;
	@UiField
	MaterialLink sdfmolecules;
	@UiField
	MaterialLink sdfsubstructures;
	@UiField
	MaterialLink thergasmolecules;
	@UiField
	MaterialLink standardKeywords;
	
	@UiField
	MaterialLink toplink;
	@UiField
	MaterialLink querylink;
	
	DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
	MaterialContainer content;
	Presenter listener;
	
	public void setContent(MaterialContainer content) {
		this.content = content;
	}
	public StandardNavigation() {
		initWidget(uiBinder.createAndBindUi(this));
		initText();
	}

	public StandardNavigation(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		initText();
	}
	private void initText() {
		/*
		chemkin.setText(descriptionConstants.chemkinsettitle());
		chemkin.setTooltip(descriptionConstants.chemkinsettooltip());
		nasapolynomials.setText(descriptionConstants.nasapolynomialssettitle());
		nasapolynomials.setTooltip(descriptionConstants.nasapolynomialssettooltip());
		*/
		/*
		sdfmolecules.setText(descriptionConstants.sdfmoleculessettitle());
		sdfmolecules.setTooltip(descriptionConstants.sdfmoleculessettooltip());
		sdfsubstructures.setText(descriptionConstants.sdfsubstructuressettitle());
		sdfsubstructures.setTooltip(descriptionConstants.sdfsubstructuressettooltip());
		thergasmolecules.setText(descriptionConstants.thergasmoleculessettitle());
		thergasmolecules.setTooltip(descriptionConstants.thergasmoleculessettooltip());
		*/
	}
	
	private void handleHistoryToken(String token) {
		toptext.setText(token);
		Place place = Place.organization;
		if (!"".equals(token)) {
			place = Place.valueOf(token);
			toptext.setText(place.getTitle());
		}
		changeNav(place);
	}

	private void changeNav(Place place) {
		Window.scrollTo(0, 0);
		content.clear();
		Widget widget = place.getContent();
		content.add(widget);
	}

	@Override
	public void setText(String text) {
	}

	@Override
	public String getText() {
		return new String("Navigation");
	}

	@UiHandler("inputorganization")
	void onInputOrganization(ClickEvent e) {
		MaterialToast.fireToast("inputorganization");
		handleHistoryToken("organization");
	}
	@UiHandler("inputcontact")
	void onOnInputContact(ClickEvent e) {
		MaterialToast.fireToast("inputcontact");
		handleHistoryToken("usercontact");
	}
	@UiHandler("chemkin")
	void onOnInputChemkin(ClickEvent e) {
		MaterialToast.fireToast("chemkin");
		handleHistoryToken("chemkin");
	}
	@UiHandler("nasapolynomials")
	void onOnNASAPolynomials(ClickEvent e) {
		MaterialToast.fireToast("nasapolynomials");
		handleHistoryToken("nasapolynomials");
	}
	
	@UiHandler("toplink")
	void onTopClick(ClickEvent e) {
		String username = Cookies.getCookie("user");
		MaterialToast.fireToast("top: " + username);
		listener.goTo(new ReactionTopPlace(username));
	}
	@UiHandler("querylink")
	void onQueryClick(ClickEvent e) {
		String username = Cookies.getCookie("user");
		MaterialToast.fireToast("Query: " + username);
		listener.goTo(new ReactionQueryPlace(username));
	}

	@UiHandler("sdfmolecules")
	void onOnInputMolecules(ClickEvent e) {
		MaterialToast.fireToast("sdfmolecules");
		handleHistoryToken("sdfmolecules");
	}
	@UiHandler("sdfsubstructures")
	void onOnInputSubstructures(ClickEvent e) {
		MaterialToast.fireToast("sdfsubstructures");
		handleHistoryToken("sdfsubstructures");
	}
	@UiHandler("thergasmolecules")
	void onOnTHERGASMolecules(ClickEvent e) {
		MaterialToast.fireToast("thergasmolecules");
		handleHistoryToken("thergasmolecules");
	}
	@UiHandler("transactions")
	void onTransaction(ClickEvent e) {
		handleHistoryToken("transactions");
	}
	
	@UiHandler("standardKeywords")
	void onUploads(ClickEvent e) {
		MaterialToast.fireToast("standardKeywords");
		handleHistoryToken("standardKeywords");
	}
	public void setPresenter(Presenter presenter) {
		listener = presenter;
	}
}
