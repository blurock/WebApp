package info.esblurock.reaction.client.panel.contact;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import gwt.material.design.client.constants.ModalType;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.StoreDescriptionData;
import info.esblurock.reaction.client.StoreDescriptionDataAsync;
import info.esblurock.reaction.client.panel.modal.TextMessagePopup;
import info.esblurock.reaction.data.contact.entities.ContactInfoData;
import info.esblurock.reaction.data.contact.entities.ContactLocationData;
import info.esblurock.reaction.data.contact.entities.OrganizationDescriptionData;
import info.esblurock.reaction.data.description.DescriptionDataData;

public class ClientStoreOrganizationInput {

	StoreDescriptionDataAsync async = StoreDescriptionData.Util.getInstance();

	public void storeOrganizationDescriptionData(
			OrganizationDescriptionData input) {
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				TextMessagePopup popup = new TextMessagePopup("ERROR",
						caught.toString());
				popup.openModal(ModalType.FIXED_FOOTER);
			}

			@Override
			public void onSuccess(String result) {
				MaterialToast.fireToast(result.toString());
			}
		};
		async.storeOrganizationDescriptionData(input, callback);
	}

	public void storeContactInfoData(String parent, ContactInfoData input) {
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				TextMessagePopup popup = new TextMessagePopup("ERROR",
						caught.toString());
				popup.openModal(ModalType.FIXED_FOOTER);
			}

			@Override
			public void onSuccess(String result) {
				MaterialToast.fireToast(result.toString());
			}
		};
		async.storeContactInfo(parent, input, callback);
	}

	public void storeContactLocationData(String parent, ContactLocationData input) {
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				TextMessagePopup popup = new TextMessagePopup("ERROR",
						caught.toString());
				popup.openModal(ModalType.FIXED_FOOTER);
			}

			@Override
			public void onSuccess(String result) {
				MaterialToast.fireToast(result.toString());
			}
		};
		async.storeContactLocation(parent, input, callback);
	}

	public void storeContactLocationData(String parent, DescriptionDataData input) {
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				TextMessagePopup popup = new TextMessagePopup("ERROR",
						caught.toString());
				popup.openModal(ModalType.FIXED_FOOTER);
			}

			@Override
			public void onSuccess(String result) {
				MaterialToast.fireToast(result.toString());
			}
		};
		async.storeDescriptionDataData(parent, input, callback);
	}

	public void getListOfOrganizationsKeywords(List<String> names) {
		AsyncCallback<List<String>> callback = new AsyncCallback<List<String>>() {
			@Override
			public void onFailure(Throwable caught) {
				TextMessagePopup popup = new TextMessagePopup("ERROR",
						caught.toString());
				popup.openModal(ModalType.FIXED_FOOTER);
			}

			@Override
			public void onSuccess(List<String> orgnames) {
				StringBuilder build = new StringBuilder();
				for (String name : orgnames) {
					build.append(name);
					build.append("\t  \n");
				}
				MaterialToast.fireToast(build.toString());
			}
		};
		async.getListOfOrganizationsKeywords(callback);
	}
	
	
	public void removeOrganizationDescriptionData(String id) {
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				TextMessagePopup popup = new TextMessagePopup("ERROR",
						caught.toString());
				popup.openModal(ModalType.FIXED_FOOTER);
			}

			@Override
			public void onSuccess(String result) {
			}
		};
		async.removeOrganizationDescriptionData(id,callback);
	}
	
}
