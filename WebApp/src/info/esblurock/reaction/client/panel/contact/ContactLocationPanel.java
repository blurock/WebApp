package info.esblurock.reaction.client.panel.contact;

import java.io.Serializable;

import info.esblurock.reaction.client.resources.InputConstants;
import info.esblurock.reaction.data.contact.entities.ContactLocationData;
import info.esblurock.reaction.server.datastore.contact.ContactLocation;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
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
	
	private void setText() {
		addressName.setPlaceholder(constants.contactaddressadress());
		addressAddress.setPlaceholder(constants.contactaddressadress());
		city.setPlaceholder(constants.contactcity());
		country.setPlaceholder(constants.contactcountry());
		postcode.setPlaceholder(constants.contactpostcode());
		gpslatitude.setPlaceholder(constants.contactgpslatitude());
		gpslongitude.setPlaceholder(constants.contactgpslongitude());
		
		addressName.setText(constants.contactaddressadresssample());
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

	@UiField
	MaterialLink adresscontact;
	@UiField
	MaterialTextArea addressName;
	@UiField
	MaterialTextArea addressAddress;
	@UiField
	MaterialTextBox city;
	@UiField
	MaterialTextBox country;
	@UiField
	MaterialTextBox postcode;
	@UiField
	MaterialTextBox gpslatitude;
	@UiField
	MaterialTextBox gpslongitude;

	String parentKey = null;
	
	public void fill(String parentkey,
			String nameS, String addressS, 
			String cityS, String countryS, String postcodeS, 
			String latitude, String longitude) {
		parentKey = parentkey;
		addressName.setText(nameS);
		addressAddress.setText(addressS);
		city.setText(cityS);
		country.setText(countryS);
		postcode.setText(postcodeS);
		gpslatitude.setText(latitude);
		gpslongitude.setText(longitude);
	}
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
		
	}
	public String getAddressName() {
		return addressName.getText();
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
