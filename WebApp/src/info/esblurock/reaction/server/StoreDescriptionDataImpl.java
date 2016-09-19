package info.esblurock.reaction.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import info.esblurock.reaction.client.StoreDescriptionData;
import info.esblurock.reaction.client.panel.contact.ContactInfoPanel;
import info.esblurock.reaction.client.panel.contact.ContactLocationPanel;
import info.esblurock.reaction.client.panel.contact.OrganizationInput;
import info.esblurock.reaction.client.panel.description.DataDescription;
import info.esblurock.reaction.client.ui.login.UserDTO;
import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.contact.entities.ContactInfoData;
import info.esblurock.reaction.data.contact.entities.ContactLocationData;
import info.esblurock.reaction.data.contact.entities.OrganizationDescriptionData;
import info.esblurock.reaction.data.contact.entities.StoreOrganizationDescriptionData;
import info.esblurock.reaction.data.contact.entities.StoreUserDescriptionData;
import info.esblurock.reaction.data.contact.entities.UserDescriptionData;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.user.UnverifiedUserAccount;
import info.esblurock.reaction.server.authorization.TaskTypes;
import info.esblurock.reaction.server.datastore.contact.GeocodingLatituteAndLongitude;
import info.esblurock.reaction.server.event.RegisterTransaction;
import info.esblurock.reaction.server.process.description.RegisterDataDescription;
import info.esblurock.reaction.server.queries.QueryBase;
import info.esblurock.reaction.server.utilities.ContextAndSessionUtilities;
import info.esblurock.reaction.server.utilities.ManageDataSourceIdentification;
import info.esblurock.reaction.server.utilities.WriteObjectTransactionToDatabase;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The Class StoreDescriptionDataImpl.
 */
public class StoreDescriptionDataImpl extends ServerBase implements
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
	
	public UserDescriptionData getUserDescriptionData(String keyword) throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		UserDescriptionData userdata = null;
		try {
			System.out.println("Get User Description, keyword='" + keyword + "'");
			List<DatabaseObject> objs = 
					QueryBase.getDatabaseObjectsFromSingleProperty(
							UserDescriptionData.class.getName(), 
							"keyword", keyword);
			if(objs.size() > 0) {
				userdata = (UserDescriptionData) objs.get(0);
				DescriptionDataData descr = userdata.getDescription();
			}
		} catch (Exception e) {
			throw new IOException(e.toString());
		}
		return userdata;
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
				input.getAddressAddress(),
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
				userDescription,
				input.getKeywords());
		return data;
	}
	
	@Override
	public String getCoordinates(String city, String country) throws IOException {
		GeocodingLatituteAndLongitude geo = new GeocodingLatituteAndLongitude();
		geo.coordinates(city, country);
		String coordinates = geo.getLatitude() + " " + geo.getLongitude();
		return coordinates;
	}

	
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
		javax.jdo.Query q = pm.newQuery(UserDescriptionData.class);
		List<UserDescriptionData> results = (List<UserDescriptionData>) q.execute();
		ArrayList<String> names = new ArrayList<String>();
		if (!results.isEmpty()) {
			for (UserDescriptionData data : results) {
				DescriptionDataData description = data.getDescription();
				if(description != null) {
					String keyword = description.getKeyword();
					names.add(keyword);
				}
			}
		}
		return names;
	}

	
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
	public String storeUserDescriptionData(UserDescriptionData user) throws IOException {
		String keyword = user.getDescription().getKeyword();
		String userS = keyword;
		boolean update = true;
		try {
			UserDescriptionData databaseUser = getUserDescriptionData(userS);
			QueryBase.deleteFromIdentificationCode(UserDescriptionData.class, "keyword", userS);
		} catch(IOException ex) {
			update = false;
		}		
		
		String idCode = "0";
		if(userS == null) {
			userS = user.getDescription().getInputkey();
			idCode = ManageDataSourceIdentification.getDataSourceIdentification(userS);
		}
		WriteObjectTransactionToDatabase.writeObjectWithTransaction(userS, keyword, idCode, user);
		if(update) {
			ContextAndSessionUtilities util = getUtilities();
			UserDTO userdto = util.getUserInfo();
			if(userdto != null) {
				String transaction = keyword + ":ProfileUpdate";
				RegisterTransaction.register(userdto,TaskTypes.login, 
						transaction, RegisterTransaction.checkLevel0);
			}
		}
		return user.getKey();
	}
}
