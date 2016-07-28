package info.esblurock.reaction.data.chemical.molecule.isomer;

import java.util.Set;

import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreIsomerData  extends StoreObject {
	
	static public String isomer = "Isomer";
	
	public StoreIsomerData(String keyword, DatabaseObject object, TransactionInfo transaction) {
		super(keyword, object, transaction);
	}
	protected void storeObject() {
		super.storeObject();
	}
	
	protected void storeRDF() {
		IsomerData data = (IsomerData) object;
		
		String atomcount = "atomCount(";
		String finish = ")";
		Set<String> set = data.getAtomCounts().keySet();
		for(String name : set) {
			Integer aC = data.getAtomCount(name);
			String predicate = atomcount + name + finish;
			storeStringRDF(predicate, aC.toString());
		}
		storeStringRDF(isomer, CreateIsomerData.standardIsomerName(data));
	}	
}
