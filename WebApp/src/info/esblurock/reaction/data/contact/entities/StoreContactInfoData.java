package info.esblurock.reaction.data.contact.entities;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreContactInfoData extends StoreObject {

	static String emailPredicate = "email";
	static String phonePredicate = "Phone";
	static String webpagePredicate = "Website";

	public StoreContactInfoData(String keyword, DatabaseObject object,
			TransactionInfo transaction) {
		super(keyword, object, transaction);
	}

	protected void storeObject() {
		super.storeObject();
	}

	protected void storeRDF() {
		super.storeRDF();//
		ContactInfoData data = (ContactInfoData) object;
		storeObjectRDF(data);

		storeStringRDF(emailPredicate, data.getEmail());
		storeStringRDF(phonePredicate, data.getPhone());
		storeStringRDF(webpagePredicate, data.getWebpage());

	}

}
