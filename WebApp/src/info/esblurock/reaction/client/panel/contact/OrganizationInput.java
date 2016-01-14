package info.esblurock.reaction.client.panel.contact;

import java.io.Serializable;

import info.esblurock.reaction.client.StoreDescriptionData;
import info.esblurock.reaction.client.StoreDescriptionDataAsync;
import info.esblurock.reaction.client.panel.description.DataDescription;
import info.esblurock.reaction.client.resources.DescriptionConstants;
import info.esblurock.reaction.client.resources.InputConstants;
import info.esblurock.reaction.data.contact.entities.ContactInfoData;
import info.esblurock.reaction.data.contact.entities.ContactLocationData;
import info.esblurock.reaction.data.contact.entities.OrganizationDescriptionData;
import info.esblurock.reaction.data.description.DescriptionDataData;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialToast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class OrganizationInput extends Composite implements HasText,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static OrganizationInputUiBinder uiBinder = GWT
			.create(OrganizationInputUiBinder.class);

	DescriptionConstants descriptionconstants = GWT
			.create(DescriptionConstants.class);

	@UiField
	MaterialLabel title;
	@UiField
	MaterialButton submit;
	@UiField
	MaterialDropDown orgtype;
	@UiField
	MaterialLink unichoice;
	@UiField
	MaterialLink indchoice;
	@UiField
	MaterialLink otherchoice;
	@UiField
	MaterialLink govchoice;
	@UiField
	MaterialLink instchoice;
	@UiField
	MaterialCollapsible datasets;

	DataDescription description;
	ContactInfoPanel contactinfo;
	ContactLocationPanel locationinfo;
	String organizationtype;

	interface OrganizationInputUiBinder extends
			UiBinder<Widget, OrganizationInput> {
	}

	InputConstants constants = GWT.create(InputConstants.class);

	private void setText() {
		title.setText(constants.organizationcontacttitle());
		submit.setText(constants.submit());

		unichoice.setText(constants.university());
		indchoice.setText(constants.industry());
		instchoice.setText(constants.institute());
		govchoice.setText(constants.government());
		otherchoice.setText(constants.other());
		
		orgtype.setText(constants.university());
		organizationtype = "University";
	}

	private void init() {
		description = new DataDescription(
				descriptionconstants.organizationinputtitle(),
				descriptionconstants.keywordtext(),
				descriptionconstants.onelinetext(),
				descriptionconstants.descriptiontext());
		datasets.addItem(description);
		String title = constants.organizationcontacttitle() + ": "
				+ constants.contacttype();
		contactinfo = new ContactInfoPanel(title);
		datasets.addItem(contactinfo);
		locationinfo = new ContactLocationPanel(title);
		datasets.addItem(locationinfo);
	}

	public OrganizationInput() {
		initWidget(uiBinder.createAndBindUi(this));
		setText();
		init();
	}

	public OrganizationInput(String name) {
		initWidget(uiBinder.createAndBindUi(this));
		setText();
		init();
	}

	@UiHandler("submit")
	void onSubmit(ClickEvent e) {
		
		StoreDescriptionDataAsync async = StoreDescriptionData.Util.getInstance();
		AsyncCallback<String> callback = new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				MaterialToast.alert(result.toString());
			}

			@Override
			public void onFailure(Throwable caught) {
				MaterialToast.alert("onFailure: " + caught.toString());

			}
		};
		
		ContactInfoData contact = new ContactInfoData(contactinfo.getEmail(), 
				contactinfo.getPhone(), 
				contactinfo.getMainhomepage());
		ContactLocationData location = new ContactLocationData(locationinfo.getAddressName(), 
				locationinfo.getAddressAddress(), locationinfo.getCity(), 
				locationinfo.getCountry(), locationinfo.getPostcode(),
				locationinfo.getGpslatitude(), locationinfo.getGpslongitude());
		DescriptionDataData descrdata = new DescriptionDataData(
				description.getKeyWord(),
				description.getOneLineDescription(),
				description.getDescription(), 
				description.getSourceDate(), description.getSource(), description.getInputKey());
		
		OrganizationDescriptionData org = new OrganizationDescriptionData(orgtype.getText(), 
				descrdata, contact, location);
		async.storeOrganizationDescriptionData(org, callback);
		
		/*
		async.createSingleOrganizationDescription(description.getKeyWord(),
				description.getOneLineDescription(),
				description.getDescription(), contactinfo.getEmail(),
				contactinfo.getPhone(), contactinfo.getMainhomepage(),
				locationinfo.getAddressName(),
				locationinfo.getAddressAddress(), locationinfo.getCity(),
				locationinfo.getCountry(), locationinfo.getPostcode(),
				locationinfo.getGpslatitude(), locationinfo.getGpslongitude(),
				callback);
				*/

	}

	@UiHandler("unichoice")
	void onUniClick(ClickEvent e) {
		orgtype.setText(constants.university());
		organizationtype = "University";
	}

	@UiHandler("indchoice")
	void onIndClick(ClickEvent e) {
		orgtype.setText(constants.industry());
		organizationtype = "Industry";
	}

	@UiHandler("otherchoice")
	void onOtherClick(ClickEvent e) {
		orgtype.setText(constants.other());
		organizationtype = "Other";
	}

	@UiHandler("govchoice")
	void onGovClick(ClickEvent e) {
		orgtype.setText(constants.government());
		organizationtype = "Goverment";
	}

	@UiHandler("instchoice")
	void onInstClick(ClickEvent e) {
		orgtype.setText(constants.institute());
		organizationtype = "Institute";
	}

	@Override
	public void setText(String text) {
		// button.setText(text);
	}

	@Override
	public String getText() {
		return new String("Organization");
	}

	public DataDescription getDescription() {
		return description;
	}

	public ContactInfoPanel getContactinfo() {
		return contactinfo;
	}

	public ContactLocationPanel getLocationinfo() {
		return locationinfo;
	}

	public void setDescription(DataDescription description) {
		this.description = description;
	}

	public void setContactinfo(ContactInfoPanel contactinfo) {
		this.contactinfo = contactinfo;
	}

	public void setLocationinfo(ContactLocationPanel locationinfo) {
		this.locationinfo = locationinfo;
	}

	public String getOrgtype() {
		return orgtype.getText();
	}

	public void setOrgtype(String type) {
		organizationtype = type;
	}
}
