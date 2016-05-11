package info.esblurock.reaction.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import info.esblurock.reaction.client.StoreDescriptionData;
import info.esblurock.reaction.client.panel.contact.ContactInfoPanel;
import info.esblurock.reaction.client.panel.contact.ContactLocationPanel;
import info.esblurock.reaction.client.panel.contact.OrganizationInput;
import info.esblurock.reaction.client.panel.description.DataDescription;
import info.esblurock.reaction.data.contact.entities.ContactInfoData;
import info.esblurock.reaction.data.contact.entities.ContactLocationData;
import info.esblurock.reaction.data.contact.entities.OrganizationDescriptionData;
import info.esblurock.reaction.data.contact.entities.StoreOrganizationDescriptionData;
import info.esblurock.reaction.data.contact.entities.StoreUserDescriptionData;
import info.esblurock.reaction.data.contact.entities.UserDescriptionData;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.server.datastore.PMF;
import info.esblurock.reaction.server.utilities.ManageDataSourceIdentification;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// TODO: Auto-generated Javadoc
/**
 * The Class StoreDescriptionDataImpl.
 */
public class StoreDescriptionDataImpl extends RemoteServiceServlet implements
		StoreDescriptionData {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The organization description. */
	static String organizationDescription = "OrganizationDescription";
	
	/** The user description. */
	static String userDescription = "UserDescription";

	/** The pm. */
	PersistenceManager pm = PMF.get().getPersistenceManager();
	
	/** The conversion. */
	StringToKeyConversion conversion = new StringToKeyConversion();

	/**
	 * Gets the organization description data.
	 *
	 * @param input the input
	 * @return the organization description data
	 */
	private OrganizationDescriptionData getOrganizationDescriptionData(
			OrganizationInput input) {
		DescriptionDataData description = getDescriptionDataData(input
				.getDescription());
		ContactInfoData contactinfodata = getContactInfoData(input
				.getContactinfo());
		ContactLocationData locationdata = getContactLocationData(input
				.getLocationinfo());
		OrganizationDescriptionData organization = new OrganizationDescriptionData(
				input.getOrgtype(), description, contactinfodata, locationdata);
		return organization;
	}

	/**
	 * Gets the contact info data.
	 *
	 * @param contact the contact
	 * @return the contact info data
	 */
	private ContactInfoData getContactInfoData(ContactInfoPanel contact) {
		ContactInfoData data = new ContactInfoData(contact.getEmail(),
				contact.getPhone(), contact.getMainhomepage());
		return data;
	}

	/**
	 * Gets the contact location data.
	 *
	 * @param input the input
	 * @return the contact location data
	 */
	private ContactLocationData getContactLocationData(
			ContactLocationPanel input) {
		ContactLocationData location = new ContactLocationData(
				input.getAddressName(), input.getAddressAddress(),
				input.getCity(), input.getCountry(), input.getPostcode(),
				input.getGpslatitude(), input.getGpslongitude());
		return location;
	}

	/**
	 * Gets the description data data.
	 *
	 * @param input the input
	 * @return the description data data
	 */
	private DescriptionDataData getDescriptionDataData(DataDescription input) {
		DescriptionDataData data = new DescriptionDataData(input.getKeyWord(),
				input.getOneLineDescription(), input.getDescription(),
				input.getSourceDate(), input.getSource(), 
				input.getInputKey(),
				userDescription);
		return data;
	}
/*
	@Override
	public OrganizationInput getOrganizationDescriptionData(String keyS, OrganizationInput panel) {
		Key key = conversion.convertStringToKey(keyS);
		OrganizationDescriptionData organization = pm.getObjectById(
				OrganizationDescriptionData.class, key);
		OrganizationDescriptionData detached = pm.detachCopy(organization);
		
		DescriptionDataData descdata = detached.getDescription();
		DataDescription data = new DataDescription();
		data.fill(descdata.getKeyword(), descdata.getOnlinedescription(), descdata.getFulldescription());

		ContactInfoData contactdata = detached.getContactinfo();
		ContactInfoPanel contactpanel = new ContactInfoPanel();
		contactpanel.fill(descdata.getKeyword(), contactdata.getEmail(), contactdata.getPhone(), contactdata.getWebpage());

		ContactLocationData locdata = detached.getLocation();
		ContactLocationPanel locpanel = new ContactLocationPanel();
		locpanel.fill(descdata.getKeyword(),
				locdata.getAddressName(), locdata.getAddressAddress(), 
				locdata.getCity(), locdata.getCountry(), locdata.getPostcode(), 
				locdata.getGpslatitute(), locdata.getGpslongitude());

		panel.setDescription(data);
		panel.setContactinfo(contactpanel);
		panel.setLocationinfo(locpanel);
		//panel.setOrgtype(detached.get);
		return panel;
	}
*/
/* (non-Javadoc)
 * @see info.esblurock.reaction.client.StoreDescriptionData#removeOrganizationDescriptionData(java.lang.String)
 */
/*
	@Override
	public ContactInfoPanel getContactInfoData(String keyS, ContactInfoPanel panel) {
		Key key = conversion.convertStringToKey(keyS);
		ContactInfoData contact = pm.getObjectById(ContactInfoData.class, key);
		ContactInfoData detached = pm.detachCopy(contact);
		
		panel.fill(null, detached.getEmail(), detached.getPhone(), detached.getWebpage());
		return panel;
	}

	@Override
	public ContactLocationPanel getContactLocationData(String keyS, ContactLocationPanel panel) {
		Key key = conversion.convertStringToKey(keyS);
		ContactLocationData location = pm.getObjectById(
				ContactLocationData.class, key);
		ContactLocationData detached = pm.detachCopy(location);
		
		panel.fill(null, detached.getAddressName(), detached.getAddressAddress(), 
				detached.getCity(), detached.getCountry(), detached.getPostcode(), 
				detached.getGpslatitute(), detached.getGpslongitude());
		return panel;
	}

	@Override
	public DataDescription getDescriptionDataData(String keyS, DataDescription data) {
		Key key = conversion.convertStringToKey(keyS);
		DescriptionDataData description = pm.getObjectById(
				DescriptionDataData.class, key);
		DescriptionDataData detached = pm.detachCopy(description);
		data.fill(detached.getKeyword(), detached.getOnlinedescription(), detached.getFulldescription());
		return data;
	}
*/
	@Override
	public String removeOrganizationDescriptionData(String key) {
		String ans = "";
		try {
			Object u = pm.getObjectById(OrganizationDescriptionData.class, key);
			pm.deletePersistent(u);
			ans = "SUCCESS: Deleted an id: " + key;
		} catch (Exception e) {
			ans = "ERROR: Unable to delete id: " + key;
			throw e;
		}
		return ans;
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.StoreDescriptionData#removeContactInfoData(java.lang.String)
	 */
	@Override
	public String removeContactInfoData(String key) {
		String ans = "";
		try {
			Object u = pm.getObjectById(ContactInfoData.class, key);
			pm.deletePersistent(u);
			ans = "SUCCESS: Deleted id: " + key;
		} catch (Exception e) {
			ans = "ERROR: Unable to delete id: "+ key;
			throw e;
		}
		return ans;
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.StoreDescriptionData#removeContactLocationData(java.lang.String)
	 */
	@Override
	public String removeContactLocationData(String key) {
		String ans = "";
		try {
			Object u = pm.getObjectById(ContactLocationData.class, key);
			pm.deletePersistent(u);
			ans = "SUCCESS: Deleted id: " + key;
		} catch (Exception e) {
			ans = "ERROR: Unable to delete id: " + key;
			throw e;
		}
		return ans;
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.StoreDescriptionData#removeDescriptionDataData(java.lang.String)
	 */
	@Override
	public String removeDescriptionDataData(String key) {
		String ans = "";
		try {
			Object u = pm.getObjectById(DescriptionDataData.class, key);
			pm.deletePersistent(u);
			ans = "SUCCESS: Deleted id: " + key;
		} catch (Exception e) {
			ans = "ERROR: Unable to delete id: " + key;
			throw e;
		}
		return ans;
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.StoreDescriptionData#getListOfOrganizationsKeywords()
	 */
	@Override
	public List<String> getListOfOrganizationsKeywords() {
		javax.jdo.Query q = pm.newQuery(OrganizationDescriptionData.class);
		List<OrganizationDescriptionData> results = (List<OrganizationDescriptionData>) q.execute();
		ArrayList<String> names = new ArrayList<String>();
		if (!results.isEmpty()) {
			for (OrganizationDescriptionData data : results) {
				DescriptionDataData description = data.getDescription();
				String keyword = description.getKeyword();
				names.add(keyword);
			}
		}
		return names;
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.StoreDescriptionData#getListOfContactsKeywords()
	 */
	@Override
	public List<String> getListOfContactsKeywords() {
		/*
		javax.jdo.Query q = pm.newQuery(ContactData.class);
		List<OrganizationDescriptionData> results = (List<OrganizationDescriptionData>) q.execute();
		ArrayList<String> names = new ArrayList<String>();
		if (!results.isEmpty()) {
			for (OrganizationDescriptionData data : results) {
				DescriptionDataData description = data.getDescription();
				String keyword = description.getKeyword();
				names.add(keyword);
			}
		}
		return names;
		*/
		return null;
	}
/*
	@Override
	public String storeOrganizationDescription(OrganizationInput organization) {
		OrganizationDescriptionData data = getOrganizationDescriptionData(organization);
		return storeOrganizationDescriptionData(data);
	}

	@Override
	public String storeContactInfo(String key, ContactInfoPanel contact) {
		ContactInfoData data = getContactInfoData(contact);
		return storeContactInfoData(data);
	}

	@Override
	public String storeContactLocation(String key, ContactLocationPanel location) {
		ContactLocationData data = getContactLocationData(location);
		return storeContactLocationData(data);
	}

	@Override
	public String storeDescriptionData(String key, DataDescription input) {
		DescriptionDataData data = getDescriptionDataData(input);
		return storeDescriptionDataData(data);
	}
*/
	/*
	@Override
	public String storeOrganizationDescriptionData(OrganizationDescriptionData organization) {
		
		return null;
	}
	*/
	/*
	private String storeOrganizationDescriptionData(
			OrganizationDescriptionData organization) {
		pm.makePersistent(organization);
		pm.detachCopy(organization);
		Key organizationKey = organization.getKey();
		String organizationKeyS = conversion
				.convertKeyToString(organizationKey);
		return organizationKeyS;
	}
*/

	
	/**
 * Store contact info data.
 *
 * @param contact the contact
 * @return the string
 */
private String storeContactInfoData(ContactInfoData contact) {
		pm.makePersistent(contact);
		pm.detachCopy(contact);
		return contact.getKey();
	}

	/**
	 * Store contact location data.
	 *
	 * @param location the location
	 * @return the string
	 */
	private String storeContactLocationData(ContactLocationData location) {
		pm.makePersistent(location);
		pm.detachCopy(location);
		return location.getKey();
	}

	/**
	 * Store description data data.
	 *
	 * @param data the data
	 * @return the string
	 */
	private String storeDescriptionDataData(DescriptionDataData data) {
		pm.makePersistent(data);
		pm.detachCopy(data);
		return data.getKey();
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.StoreDescriptionData#storeOrganizationDescriptionData(info.esblurock.reaction.data.contact.entities.OrganizationDescriptionData)
	 */
	@Override
	public String storeOrganizationDescriptionData(
			OrganizationDescriptionData organization) {
		String keyword = organization.getDescription().getKeyword();
		String user = organization.getDescription().getInputkey();
		String idCode = ManageDataSourceIdentification.getDataSourceIdentification(user);
		TransactionInfo transaction = new TransactionInfo(user, keyword, organization.getClass().getName(),idCode);
		StoreOrganizationDescriptionData store 
			= new StoreOrganizationDescriptionData(keyword, organization, transaction);
		store.finish();
		return transaction.getKey();
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.StoreDescriptionData#storeContactInfo(java.lang.String, info.esblurock.reaction.data.contact.entities.ContactInfoData)
	 */
	@Override
	public String storeContactInfo(String key, ContactInfoData contact) {
		contact.setParentKey(key);
		String id = storeContactInfoData(contact);
		return id;
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.StoreDescriptionData#storeContactLocation(java.lang.String, info.esblurock.reaction.data.contact.entities.ContactLocationData)
	 */
	@Override
	public String storeContactLocation(String key, ContactLocationData location) {
		location.setParentKey(key);
		String id = storeContactLocationData(location);
		return id;
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.StoreDescriptionData#storeDescriptionDataData(java.lang.String, info.esblurock.reaction.data.description.DescriptionDataData)
	 */
	@Override
	public String storeDescriptionDataData(String key, DescriptionDataData data) {
		data.setParentKey(key);
		String id = storeDescriptionDataData(data);
		return id;
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.StoreDescriptionData#removeUserDescriptionData(java.lang.String)
	 */
	@Override
	public String removeUserDescriptionData(String key) {
		String ans = "";
		try {
			Object u = pm.getObjectById(UserDescriptionData.class, key);
			pm.deletePersistent(u);
			ans = "SUCCESS: Deleted an id: " + key;
		} catch (Exception e) {
			ans = "ERROR: Unable to delete id: " + key;
			throw e;
		}
		return ans;
	}

	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.StoreDescriptionData#storeUserDescriptionData(info.esblurock.reaction.data.contact.entities.UserDescriptionData)
	 */
	@Override
	public String storeUserDescriptionData(
			UserDescriptionData user) {
		String keyword = user.getDescription().getKeyword();
		String userS = user.getDescription().getInputkey();
		String idCode = ManageDataSourceIdentification.getDataSourceIdentification(userS);
		TransactionInfo transaction = new TransactionInfo(userS, keyword, user.getClass().getName(),idCode);
		StoreUserDescriptionData store 
			= new StoreUserDescriptionData(keyword, user, transaction);
		store.finish();
		return transaction.getKey();
	}
}
