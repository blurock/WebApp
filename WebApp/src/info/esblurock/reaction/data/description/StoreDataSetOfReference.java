package info.esblurock.reaction.data.description;

import info.esblurock.reaction.client.panel.description.ReferenceDescriptions;
import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreDataSetOfReference extends StoreObject {

	static String authorLastName = "AuthorLastName";
	static String author = "Author";
	
	public StoreDataSetOfReference() {
		super();
	}

	public StoreDataSetOfReference(String keyword, DatabaseObject object, TransactionInfo transaction,
			boolean storeObject) {
		super(keyword, object, transaction, storeObject);
	}

	public StoreDataSetOfReference(String keyword, DatabaseObject object, TransactionInfo transaction) {
		super(keyword, object, transaction);
	}
	protected void storeObject() {
		super.storeObject();
	}

	protected void storeRDF() {
		DataSetReference reference = (DataSetReference) object;
		storeObjectRDF(reference);
		
		for(String name : reference.getAuthors()) {
			storeStringRDF(author,name);
		}
		for(String name : reference.getAuthorLastNames()) {
			storeStringRDF(authorLastName,name);
		}
		
	}

}
