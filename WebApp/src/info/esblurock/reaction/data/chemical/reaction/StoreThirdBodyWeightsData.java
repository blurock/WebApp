package info.esblurock.reaction.data.chemical.reaction;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreThirdBodyWeightsData  extends StoreObject {
	final String delimiter = ":";
	final String mechanismMoleculeWeight = "ThirdBodyMoleculeWeight";

	public StoreThirdBodyWeightsData(String keyword, DatabaseObject object, TransactionInfo transaction) {
		super(keyword, object, transaction);
	}
	protected void storeRDF() {
		super.storeRDF();		
	}
	
	protected void storeObject() {
		ThirdBodyWeightsData data = (ThirdBodyWeightsData) object;
		
		String weightS = data.getMolecule() + delimiter + data.getWeight();
		storeStringRDF(mechanismMoleculeWeight,weightS);
		
	}

}
