package info.esblurock.reaction.data.contact.entities;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.description.StoreDescriptionData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreUserDescriptionData  extends StoreObject {
	static String userrole = "UserRole";

	public StoreUserDescriptionData(String keyword, DatabaseObject object,
			TransactionInfo transaction) {
		super(keyword, object, transaction);
	}
	protected void storeObject() {
		super.storeObject();
		UserDescriptionData data = (UserDescriptionData) object;
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
		UserDescriptionData data = (UserDescriptionData) object;
		storeStringRDF(userrole,data.getUserRole());
	}

}
