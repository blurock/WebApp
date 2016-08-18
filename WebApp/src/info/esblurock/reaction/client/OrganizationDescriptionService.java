package info.esblurock.reaction.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("organizationdescriptionservice")
public interface OrganizationDescriptionService extends RemoteService {

	   public static class Util
	   {
	       private static OrganizationDescriptionServiceAsync instance;

	       public static OrganizationDescriptionServiceAsync getInstance()
	       {
	           if (instance == null)
	           {
	               instance = GWT.create(OrganizationDescriptionService.class);
	           }
	           return instance;
	       }
	   }
	String createSingleOrganizationDescription(String keyword, String oneline,
			String full, String email, String phone, String homepage,
			String name, String address, String city, String country,
			String postcode, String gpsLatitude, String gpsLongitude);
	
}
