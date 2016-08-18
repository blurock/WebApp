package info.esblurock.reaction.client.panel.contact;

import java.io.Serializable;

import info.esblurock.reaction.client.resources.InputConstants;
import info.esblurock.reaction.data.contact.entities.ContactInfoData;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ContactInfoPanel extends Composite implements HasText {

	private static final long serialVersionUID = 1L;
	private static ContactInfoPanelUiBinder uiBinder = GWT
			.create(ContactInfoPanelUiBinder.class);

	interface ContactInfoPanelUiBinder extends
			UiBinder<Widget, ContactInfoPanel> {
	}

	InputConstants constants = GWT.create(InputConstants.class);

	@UiField
	MaterialLink organizationcontact;
	@UiField
	MaterialTextBox email;
	@UiField
	MaterialTextBox phone;
	@UiField
	MaterialTextBox mainhomepage;

	String parentKey = null;
	String title; 

	private void setText() {
		email.setPlaceholder(constants.contactemail());
		phone.setPlaceholder(constants.contactphone());
		mainhomepage.setPlaceholder(constants.contactwebpage());
		
		email.setText(constants.contactemailsample());
		phone.setText(constants.contactphonesample());
		mainhomepage.setText(constants.contactwebpagesample());		
	}
	public ContactInfoPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		organizationcontact.setText(title);
		setText();
	}
	public ContactInfoPanel(String title) {
		initWidget(uiBinder.createAndBindUi(this));
		organizationcontact.setText(title);
		this.title = title;
		setText();
	}
	public ContactInfoPanel(ContactInfoData contact) {
		this.fill(contact);
	}
	public void fill(ContactInfoData contact) {
		email.setText(contact.getEmail());
		phone.setText(contact.getPhone());
		mainhomepage.setText(contact.getWebpage());
	}
	
	public void fill(String emailS) {
		email.setText(emailS);
		email.setEnabled(false);
	}
	public void fill(String parent, String emailS, String phoneS, String mainhomepageS) {
		parentKey = parent;
		email.setText(emailS);
		phone.setText(phoneS);
		mainhomepage.setText(mainhomepageS);
	}
	public void setText(String text) {
	}

	public String getText() {
		return "ContactInfo";
	}

	public String getEmail() {
		return email.getText();
	}
	public String getPhone() {
		return phone.getText();
	}
	public String getMainhomepage() {
		return mainhomepage.getText();
	}
	
	
}
