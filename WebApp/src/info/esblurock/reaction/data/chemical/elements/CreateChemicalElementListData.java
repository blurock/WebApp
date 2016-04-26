package info.esblurock.reaction.data.chemical.elements;

import java.util.ArrayList;
import java.util.Set;

import info.esblurock.react.mechanisms.chemkin.ChemkinElementList;
import info.esblurock.reaction.data.CreateData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class CreateChemicalElementListData extends CreateData {
	String keywordBase;

	public CreateChemicalElementListData(String keywordBase) {
		this.keywordBase = keywordBase;
	}
	
	public ChemicalElementListData create(ChemkinElementList elementList) {
		ArrayList<String> lst = new ArrayList<String>();
		Set<String> keys = elementList.keySet();
		for(String key : keys) {
			lst.add(key);
		}
		ChemicalElementListData data = new ChemicalElementListData(keywordBase,lst);
		return data;
	}

	public void create(ChemicalElementListData data, TransactionInfo transaction) {
		StoreChemicalElementData store = new StoreChemicalElementData(keywordBase, data, transaction, true);
	}
}
