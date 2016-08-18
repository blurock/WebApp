package info.esblurock.reaction.client;

import info.esblurock.reaction.data.contact.entities.ContactInfoData;
import info.esblurock.reaction.data.contact.entities.ContactLocationData;
import info.esblurock.reaction.data.contact.entities.OrganizationDescriptionData;
import info.esblurock.reaction.data.contact.entities.UserDescriptionData;
import info.esblurock.reaction.data.description.DescriptionDataData;

import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StoreDescriptionDataAsync {
	
	void removeOrganizationDescriptionData(String key,
			AsyncCallback<String> callback);

	void removeContactInfoData(String key, AsyncCallback<String> callback);

	void removeContactLocationData(String key, AsyncCallback<String> callback);

	void removeDescriptionDataData(String key, AsyncCallback<String> callback);

	void getListOfContactsKeywords(AsyncCallback<List<String>> callback);

	void getListOfOrganizationsKeywords(
			AsyncCallback<List<String>> callback);

	void storeDescriptionDataData(String key, DescriptionDataData data,
			AsyncCallback<String> callback);

	void storeContactInfo(String key, ContactInfoData contact,
			AsyncCallback<String> callback);

	void storeContactLocation(String key, ContactLocationData location,
			AsyncCallback<String> callback);

	void storeOrganizationDescriptionData(
			OrganizationDescriptionData organization,
			AsyncCallback<String> callback);

	void removeUserDescriptionData(String key, AsyncCallback<String> callback);

	void storeUserDescriptionData(UserDescriptionData organization,
			AsyncCallback<String> callback);

	void getCoordinates(String city, String country, AsyncCallback<String> callback);

	void getUserDescriptionData(String keyword, AsyncCallback<UserDescriptionData> callback);

}
