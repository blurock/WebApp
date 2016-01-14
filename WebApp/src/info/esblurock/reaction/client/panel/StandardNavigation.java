package info.esblurock.reaction.client.panel;

import info.esblurock.reaction.client.panel.inputs.place.Place;
import info.esblurock.reaction.client.resources.DescriptionConstants;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.MaterialTopNav;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
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
	MaterialTopNav topNav;
	@UiField
	MaterialLink inputquery;
	@UiField
	MaterialLink inputorganization;
	@UiField
	MaterialLink inputcontact;
	@UiField
	MaterialLink chemkin;
	@UiField
	MaterialLink sdfmolecules;
	@UiField
	MaterialLink sdfsubstructures;
	@UiField
	MaterialLink thergasmolecules;
	@UiField
	MaterialLink nasapolynomials;
	
	DescriptionConstants descriptionConstants = GWT.create(DescriptionConstants.class);
	MaterialContainer content;
	
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
		chemkin.setText(descriptionConstants.chemkinsettitle());
		chemkin.setTooltip(descriptionConstants.chemkinsettooltip());
		sdfmolecules.setText(descriptionConstants.sdfmoleculessettitle());
		sdfmolecules.setTooltip(descriptionConstants.sdfmoleculessettooltip());
		sdfsubstructures.setText(descriptionConstants.sdfsubstructuressettitle());
		sdfsubstructures.setTooltip(descriptionConstants.sdfsubstructuressettooltip());
		thergasmolecules.setText(descriptionConstants.thergasmoleculessettitle());
		thergasmolecules.setTooltip(descriptionConstants.thergasmoleculessettooltip());
		nasapolynomials.setText(descriptionConstants.nasapolynomialssettitle());
		nasapolynomials.setTooltip(descriptionConstants.nasapolynomialssettooltip());
		
	}
	
	
	private void handleHistoryToken(String token) {
		topNav.setProfileName(token);
		Place place = Place.organization;
		if (!"".equals(token)) {
			place = Place.valueOf(token);
		}
		changeNav(place);
	}

	
	private void changeNav(Place place) {
		MaterialToast.alert(place.getTitle());
		// navBar.hide();
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
		MaterialToast.alert("inputorganization");
		handleHistoryToken("organization");
	}
	@UiHandler("inputcontact")
	void onOnInputContact(ClickEvent e) {
		MaterialToast.alert("inputcontact");
		handleHistoryToken("usercontact");
	}
	@UiHandler("chemkin")
	void onOnInputChemkin(ClickEvent e) {
		MaterialToast.alert("chemkin");
		handleHistoryToken("chemkin");
	}
	@UiHandler("sdfmolecules")
	void onOnInputMolecules(ClickEvent e) {
		MaterialToast.alert("sdfmolecules");
		handleHistoryToken("sdfmolecules");
	}
	@UiHandler("sdfsubstructures")
	void onOnInputSubstructures(ClickEvent e) {
		MaterialToast.alert("sdfsubstructures");
		handleHistoryToken("sdfsubstructures");
	}
	@UiHandler("thergasmolecules")
	void onOnTHERGASMolecules(ClickEvent e) {
		MaterialToast.alert("thergasmolecules");
		handleHistoryToken("thergasmolecules");
	}
	@UiHandler("nasapolynomials")
	void onOnNASAPolynomials(ClickEvent e) {
		MaterialToast.alert("nasapolynomials");
		handleHistoryToken("nasapolynomials");
	}
	@UiHandler("toprocess")
	void onToProcess(ClickEvent e) {
		MaterialToast.alert("toprocess");
		handleHistoryToken("toprocess");
	}
	@UiHandler("transactions")
	void onTransaction(ClickEvent e) {
		MaterialToast.alert("transactions");
		handleHistoryToken("transactions");
	}

}
