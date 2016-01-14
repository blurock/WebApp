package info.esblurock.reaction.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface OrganizationDescriptionServiceAsync {
	public void createSingleOrganizationDescription(
			String keyword, String oneline, String full,
			String email, String phone, String homepage,
			String name, String address, 
			String city, String country, String postcode,
			String gpsLatitude, String gpsLongitude,
			AsyncCallback<String> callback);


}
