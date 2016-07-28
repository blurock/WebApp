package info.esblurock.reaction.data.contact.entities;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreContactLocationData extends StoreObject {

	static String countryPredicate = "Country";
	static String cityPredicate = "City";

	public StoreContactLocationData(String keyword, DatabaseObject object,
			TransactionInfo transaction) {
		super(keyword, object, transaction);
	}
	protected void storeObject() {
		super.storeObject();
	}
	protected void storeRDF() {
		super.storeRDF();
		ContactLocationData data = (ContactLocationData) object;
		storeStringRDF(countryPredicate,data.getCountry());
		storeStringRDF(cityPredicate,data.getCity());
	}
}
