package info.esblurock.reaction.client.panel.contact;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialDropDown;
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
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class UserContactInput extends Composite implements HasText {

	private static UserContactInputUiBinder uiBinder = GWT
			.create(UserContactInputUiBinder.class);

	interface UserContactInputUiBinder extends
			UiBinder<Widget, UserContactInput> {
	}

	private static final long serialVersionUID = 1L;

	
	private String descriptionKey = "User";
	
	DescriptionConstants descriptionconstants = GWT.create(DescriptionConstants.class);
	InputConstants constants = GWT.create(InputConstants.class);
	
	@UiField
	MaterialLabel title;
	@UiField
	MaterialButton submit;
	@UiField
	MaterialDropDown userrolelist;
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

		 userrole = "Researcher";
		 userrolelist.setText(constants.researcher());
	 }
		public UserContactInput() {
			initWidget(uiBinder.createAndBindUi(this));
			setText();
			description = new DataDescription(
					descriptionconstants.contactinputtitle(),
					descriptionconstants.keywordtext(),
					descriptionconstants.onelinetext(),
					descriptionconstants.descriptiontext());
			datasets.addItem(description);
			String title = constants.userrole() + ": " + constants.contacttype();
			contactinfo = new ContactInfoPanel(title);
			datasets.addItem(contactinfo);
			locationinfo = new ContactLocationPanel(title);
			datasets.addItem(locationinfo);
			userrolelist.setText(constants.researcher());
			userrole = "Researcher";
		}
	public UserContactInput(String name) {
		initWidget(uiBinder.createAndBindUi(this));
		setText();
		description = new DataDescription(
				descriptionconstants.contactinputtitle(),
				descriptionconstants.keywordtext(),
				descriptionconstants.onelinetext(),
				descriptionconstants.descriptiontext());
		datasets.addItem(description);
		String title = constants.userrole() + ": " + constants.contacttype();
		contactinfo = new ContactInfoPanel(title);
		datasets.addItem(contactinfo);
		locationinfo = new ContactLocationPanel(title);
		datasets.addItem(locationinfo);
		userrolelist.setText(constants.researcher());
		userrole = "Researcher";
	}

	@UiHandler("submit")
	void onSubmit(ClickEvent e) {
		StoreDescriptionDataAsync async = StoreDescriptionData.Util.getInstance();
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				MaterialToast.alert(result);
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
				description.getSourceDate(), description.getSource(), 
				description.getInputKey(),
				descriptionKey);
		
		UserDescriptionData user = new UserDescriptionData(userrole, 
				descrdata, contact, location);
		async.storeUserDescriptionData(user, callback);
		
	}
	
	
	@UiHandler("professorchoice")
	void onProfessorClick(ClickEvent e) {
		userrolelist.setText(constants.professor());
		userrole = "Professor";
	}

	@UiHandler("lecturerchoice")
	void onLecturerClick(ClickEvent e) {
		userrolelist.setText(constants.lecturer());
		userrole = "Lecturer";
	}

	@UiHandler("postdocchoice")
	void onPostDocrClick(ClickEvent e) {
		userrolelist.setText(constants.postdoc());
		userrole = "PostDoc";
	}

	@UiHandler("studentchoice")
	void onStudentClick(ClickEvent e) {
		userrolelist.setText(constants.student());
		userrole = "Student";
	}

	@UiHandler("managerchoice")
	void onManagerClick(ClickEvent e) {
		userrolelist.setText(constants.manager());
		userrole = "Manager";
	}
	
	@UiHandler("researcherchoice")
	void onResearcherClick(ClickEvent e) {
		userrolelist.setText(constants.researcher());
		userrole = "Researcher";
	}
	
	@UiHandler("otherchoice")
	void onOtherClick(ClickEvent e) {
		userrolelist.setText(constants.other());
		userrole = "Other";
	}
	

	@Override
	public void setText(String text) {
		//button.setText(text);
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
