package info.esblurock.reaction.client.panel.contact;

import info.esblurock.reaction.client.StoreDescriptionData;
import info.esblurock.reaction.client.StoreDescriptionDataAsync;
import info.esblurock.reaction.client.resources.InputConstants;
import info.esblurock.reaction.data.contact.entities.ContactLocationData;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ContactLocationPanel extends Composite implements HasText {

	private static ContactLocationPanelUiBinder uiBinder = GWT
			.create(ContactLocationPanelUiBinder.class);

	interface ContactLocationPanelUiBinder extends
			UiBinder<Widget, ContactLocationPanel> {
	}

	InputConstants constants = GWT.create(InputConstants.class);
	
	@UiField
	MaterialLink adresscontact;
	@UiField
	MaterialTextArea addressAddress;
	@UiField
	MaterialTextBox city;
	@UiField
	MaterialTextBox country;
	@UiField
	MaterialTextBox postcode;
	@UiField
	MaterialButton getCoordinates;
	@UiField
	MaterialTextBox gpslatitude;
	@UiField
	MaterialTextBox gpslongitude;

	String parentKey = null;

	private void setText() {
		addressAddress.setPlaceholder(constants.contactaddressadress());
		city.setPlaceholder(constants.contactcity());
		country.setPlaceholder(constants.contactcountry());
		postcode.setPlaceholder(constants.contactpostcode());
		gpslatitude.setPlaceholder(constants.contactgpslatitude());
		gpslongitude.setPlaceholder(constants.contactgpslongitude());
		
		addressAddress.setText(constants.contactaddressadresssample());
		city.setText(constants.contactcitysample());
		country.setText(constants.contactcountrysample());
		postcode.setText(constants.contactpostcodesample());
		gpslatitude.setText(constants.contactgpslatitudesample());
		gpslongitude.setText(constants.contactgpslongitudesample());
		
	}
	public ContactLocationPanel(String title) {
		initWidget(uiBinder.createAndBindUi(this));
		adresscontact.setText(title);
		setText();
	}
	public ContactLocationPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		setText();
	}

	public void fill(ContactLocationData data) {
		addressAddress.setText(data.getAddressAddress());
		city.setText(data.getCity());
		country.setText(data.getCountry());
		postcode.setText(data.getPostcode());
		gpslatitude.setText(data.getGpslatitute());
		gpslongitude.setText(data.getGpslongitude());
		
	}
	public void fillInCoordinates(String latitude, String longitude) {
		gpslatitude.setText(latitude);
		gpslongitude.setText(longitude);
	}
	public void fill(String parentkey,
			String nameS, String addressS, 
			String cityS, String countryS, String postcodeS, 
			String latitude, String longitude) {
		parentKey = parentkey;
		addressAddress.setText(addressS);
		city.setText(cityS);
		country.setText(countryS);
		postcode.setText(postcodeS);
		gpslatitude.setText(latitude);
		gpslongitude.setText(longitude);
	}
	
	@UiHandler("getCoordinates")
	void onClick(ClickEvent e) {
		String cityS = city.getText();
		String countryS = country.getText();
		ContactCoordinatesCallback callback = new ContactCoordinatesCallback(this);
		StoreDescriptionDataAsync async = StoreDescriptionData.Util.getInstance();
		async.getCoordinates(cityS, countryS, callback);
		
	}
	
	
	@Override
	public String getText() {
		return null;
	}
	@Override
	public void setText(String text) {
	}
	public String getAddressAddress() {
		return addressAddress.getText();
	}
	public String getCity() {
		return city.getText();
	}
	public String getCountry() {
		return country.getText();
	}
	public String getPostcode() {
		return postcode.getText();
	}
	public String getGpslatitude() {
		return gpslatitude.getText();
	}
	public String getGpslongitude() {
		return gpslongitude.getText();
	}

}
