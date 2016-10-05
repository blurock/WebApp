package info.esblurock.reaction.server;

import info.esblurock.reaction.client.OrganizationDescriptionService;
import info.esblurock.reaction.data.description.DescriptionData;
import info.esblurock.reaction.server.datastore.contact.ContactInfo;
import info.esblurock.reaction.server.datastore.contact.ContactLocation;
import info.esblurock.reaction.server.datastore.contact.OrganizationDescription;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class OrganizationDescriptionServiceImpl extends RemoteServiceServlet implements OrganizationDescriptionService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String attribute = "Main";

	@Override
	public String createSingleOrganizationDescription(
			String keyword, String oneline, String full, 
			String email, String phone, String homepage, 
			String name, String address,
			String city, String country, String postcode, 
			String gpsLatitude, String gpsLongitude) {
			String owner = "Administration";

		DescriptionData data = new DescriptionData(OrganizationDescriptionServiceImpl.attribute, keyword, oneline, full);
		ContactInfo c = new ContactInfo(OrganizationDescriptionServiceImpl.attribute, email, phone, homepage);
		ContactLocation location = new ContactLocation(OrganizationDescriptionServiceImpl.attribute, name, address, city, country, postcode, gpsLatitude, gpsLongitude);

		OrganizationDescription description = new OrganizationDescription(data, c, location);
		description.store(owner, keyword);
		
		String descriptionS = description.toString();
		return descriptionS;
	}

}
