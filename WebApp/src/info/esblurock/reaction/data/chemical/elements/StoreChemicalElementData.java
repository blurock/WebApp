package info.esblurock.reaction.data.chemical.elements;

import info.esblurock.react.mechanisms.chemkin.ChemkinElementList;
import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreChemicalElementData extends StoreObject {
	static public String hasElementS = "hasElement";

	public StoreChemicalElementData(String keyword, DatabaseObject object, TransactionInfo transaction, boolean storeObject) {
		super(keyword, object, transaction,storeObject);
	}

	protected void storeRDF() {
		ChemicalElementListData data = (ChemicalElementListData) object;
		for(String element : data.getElementList()) {
			storeStringRDF(hasElementS, element);
		}
	}
	protected void storeObject() {
		super.storeObject();
	}

}
