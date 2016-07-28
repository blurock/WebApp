package info.esblurock.reaction.data.contact.entities;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.description.StoreDescriptionData;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreOrganizationDescriptionData extends StoreObject {
	static String organizationType = "OrganizationType";
	public StoreOrganizationDescriptionData(String keyword,
			DatabaseObject object, TransactionInfo transaction) {
		super(keyword, object, transaction);
	}
	protected void storeObject() {
		super.storeObject();
		OrganizationDescriptionData data = (OrganizationDescriptionData) object;
		data.getDescription().setParentKey(data.getKey());
		data.getContactinfo().setParentKey(data.getKey());
		data.getLocation().setParentKey(data.getKey());
		StoreDescriptionData description = new StoreDescriptionData(data.getDescription().getKeyword(),
				data.getDescription(), transaction);
		StoreContactInfoData contact = new StoreContactInfoData(data.getDescription().getKeyword(), 
				data.getContactinfo(),transaction);
		StoreContactLocationData location = new StoreContactLocationData(data.getDescription().getKeyword(), 
				data.getLocation(),transaction);
	}

	protected void storeRDF() {
		OrganizationDescriptionData data = (OrganizationDescriptionData) object;
		storeObjectRDF(data);
		storeStringRDF(organizationType,data.getOrganizationtype());
	}

}
