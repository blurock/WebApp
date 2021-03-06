package info.esblurock.reaction.client.panel.contact;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.StoreDescriptionData;
import info.esblurock.reaction.client.StoreDescriptionDataAsync;
import info.esblurock.reaction.client.panel.description.DataDescription;
import info.esblurock.reaction.client.resources.DescriptionConstants;
import info.esblurock.reaction.client.resources.InputConstants;
import info.esblurock.reaction.data.contact.entities.ContactInfoData;
import info.esblurock.reaction.data.contact.entities.ContactLocationData;
import info.esblurock.reaction.data.contact.entities.UserDescriptionData;
import info.esblurock.reaction.data.description.DescriptionDataData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class UserContactInput extends Composite implements HasText {

	private static UserContactInputUiBinder uiBinder = GWT.create(UserContactInputUiBinder.class);

	interface UserContactInputUiBinder extends UiBinder<Widget, UserContactInput> {
	}

	private static final long serialVersionUID = 1L;

	private String descriptionKey = "User";

	DescriptionConstants descriptionconstants = GWT.create(DescriptionConstants.class);
	InputConstants constants = GWT.create(InputConstants.class);

	@UiField
	MaterialLabel title;
	@UiField
	MaterialLink submit;
	
	@UiField
	MaterialLink userrolelink;
	@UiField
	MaterialLink professorchoice;
	@UiField
	MaterialLink researcherchoice;
	@UiField
	MaterialLink lecturerchoice;
	@UiField
	MaterialLink studentchoice;
	@UiField
	MaterialLink postdocchoice;
	@UiField
	MaterialLink managerchoice;
	@UiField
	MaterialLink otherchoice;
	
	@UiField
	MaterialCollapsible datasets;

	DataDescription description;
	ContactInfoPanel contactinfo;
	ContactLocationPanel locationinfo;
	String userrole;

	
	public UserContactInput() {
		initWidget(uiBinder.createAndBindUi(this));
		setText();
		init();
	}

	public UserContactInput(String name) {
		initWidget(uiBinder.createAndBindUi(this));
		setText();
		init();
		description.setTextAsUserDescription(name);
	}

	public UserContactInput(UserDescriptionData userdescr) {
		initWidget(uiBinder.createAndBindUi(this));
		setText();
		init();
		fill(userdescr);
	}

	private void setText() {
		title.setText(constants.individualcontacttitle());
		submit.setText(constants.submit());

		professorchoice.setText(constants.professor());
		researcherchoice.setText(constants.researcher());
		lecturerchoice.setText(constants.lecturer());
		postdocchoice.setText(constants.postdoc());
		studentchoice.setText(constants.student());
		managerchoice.setText(constants.manager());
		otherchoice.setText(constants.other());
		userrole = "";
	}

	private void init() {
		description = new DataDescription(descriptionconstants.contactinputtitle(), descriptionconstants.keywordtext(),
				descriptionconstants.onelinetext(), descriptionconstants.descriptiontext());
		datasets.add(description);
		contactinfo = new ContactInfoPanel(descriptionconstants.usercontact());
		datasets.add(contactinfo);
		locationinfo = new ContactLocationPanel(descriptionconstants.userlocation());
		datasets.add(locationinfo);
		userrole = "";
	}
	public void fill(UserDescriptionData userdescr) {
		userrolelink.setText(userdescr.getUserRole());
		contactinfo.fill(userdescr.getContactinfo());
		description.fill(userdescr.getDescription());
		locationinfo.fill(userdescr.getLocation());		
	}
	
	@UiHandler("submit")
	void onSubmit(ClickEvent e) {
		StoreDescriptionDataAsync async = StoreDescriptionData.Util.getInstance();
		AsyncCallback<String> callback = new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				MaterialToast.fireToast("Profile Submitted " + result);
			}

			@Override
			public void onFailure(Throwable caught) {
				MaterialToast.fireToast("onFailure: " + caught.toString());

			}
		};
		ContactInfoData contact = new ContactInfoData(contactinfo.getEmail(), contactinfo.getPhone(),
				contactinfo.getMainhomepage());
		ContactLocationData location = new ContactLocationData(
				locationinfo.getAddressAddress(), 
				locationinfo.getCity(), locationinfo.getCountry(),locationinfo.getPostcode(), 
				locationinfo.getGpslatitude(), locationinfo.getGpslongitude());
		DescriptionDataData descrdata = new DescriptionDataData(description.getKeyWord(),
				description.getOneLineDescription(), description.getDescription(), description.getSourceDate(),
				description.getSource(), description.getInputKey(), 
				descriptionKey,description.getKeywords());

		UserDescriptionData user = new UserDescriptionData(userrole, descrdata, contact, location);
		async.storeUserDescriptionData(user, callback);

	}

	@UiHandler("professorchoice")
	void onProfessorClick(ClickEvent e) {
		userrolelink.setText(constants.professor());
		userrole = "Professor";
	}

	@UiHandler("lecturerchoice")
	void onLecturerClick(ClickEvent e) {
		userrolelink.setText(constants.lecturer());
		userrole = "Lecturer";
	}

	@UiHandler("postdocchoice")
	void onPostDocrClick(ClickEvent e) {
		userrolelink.setText(constants.postdoc());
		userrole = "PostDoc";
	}

	@UiHandler("studentchoice")
	void onStudentClick(ClickEvent e) {
		userrolelink.setText(constants.student());
		userrole = "Student";
	}

	@UiHandler("managerchoice")
	void onManagerClick(ClickEvent e) {
		userrolelink.setText(constants.manager());
		userrole = "Manager";
	}

	@UiHandler("researcherchoice")
	void onResearcherClick(ClickEvent e) {
		userrolelink.setText(constants.researcher());
		userrole = "Researcher";
	}

	@UiHandler("otherchoice")
	void onOtherClick(ClickEvent e) {
		userrolelink.setText(constants.other());
		userrole = "Other";
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

	public String getUserRole() {
		return userrole;
	}

	public void setUserRole(String type) {
		userrole = type;
	}
}
