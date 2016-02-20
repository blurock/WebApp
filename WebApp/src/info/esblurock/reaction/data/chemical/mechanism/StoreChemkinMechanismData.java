package info.esblurock.reaction.data.chemical.mechanism;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.chemical.reaction.ThirdBodyWeightsData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreChemkinMechanismData  extends StoreObject  {
	
	

	public StoreChemkinMechanismData(String keyword, DatabaseObject object, TransactionInfo transaction,boolean storeObject) {
		super(keyword, object, transaction, storeObject);
	}
	protected void storeRDF() {
		super.storeRDF();		
	}
	
	protected void storeObject() {
	}

	
}
