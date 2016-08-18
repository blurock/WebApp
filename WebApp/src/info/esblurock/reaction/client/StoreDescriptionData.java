package info.esblurock.reaction.client;

import java.io.IOException;
import java.util.List;

import info.esblurock.reaction.data.contact.entities.ContactInfoData;
import info.esblurock.reaction.data.contact.entities.ContactLocationData;
import info.esblurock.reaction.data.contact.entities.OrganizationDescriptionData;
import info.esblurock.reaction.data.contact.entities.UserDescriptionData;
import info.esblurock.reaction.data.description.DescriptionDataData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("storedescriptiondata")
public interface StoreDescriptionData extends RemoteService {

	public static class Util {
		private static StoreDescriptionDataAsync instance;

		public static StoreDescriptionDataAsync getInstance() {
			if (instance == null) {
				instance = GWT.create(StoreDescriptionData.class);
			}
			return instance;
		}
	}

	String removeUserDescriptionData(String key);

	String removeOrganizationDescriptionData(String key);

	String removeContactInfoData(String key);

	String removeContactLocationData(String key);

	String removeDescriptionDataData(String key);

	String storeUserDescriptionData(UserDescriptionData organization) throws IOException;

	String storeOrganizationDescriptionData(OrganizationDescriptionData organization);

	String storeDescriptionDataData(String key, DescriptionDataData data);

	String storeContactInfo(String key, ContactInfoData contact);

	String storeContactLocation(String key, ContactLocationData location);

	List<String> getListOfContactsKeywords();

	List<String> getListOfOrganizationsKeywords();

	String getCoordinates(String city, String country) throws IOException;

	UserDescriptionData getUserDescriptionData(String keyword) throws IOException;

}
