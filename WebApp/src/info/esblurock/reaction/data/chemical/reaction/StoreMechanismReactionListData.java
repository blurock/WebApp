package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreMechanismReactionListData  extends StoreObject  {

	ArrayList<ChemkinReactionData> reactionset;

	public StoreMechanismReactionListData(String keyword, DatabaseObject object, TransactionInfo transaction,
			boolean storeObject) {
		super(keyword, object, transaction, storeObject);
	}
	
	protected void storeObject() {
		super.storeObject();
	}
	
	protected void storeRDF() {
	}
	
	public void setListOfChemkinReactionData(ArrayList<ChemkinReactionData> set) {
		reactionset = set;
	}
}
