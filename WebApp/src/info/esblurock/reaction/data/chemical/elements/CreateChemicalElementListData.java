package info.esblurock.reaction.data.chemical.elements;

import java.util.ArrayList;
import java.util.Set;

import info.esblurock.react.mechanisms.chemkin.ChemkinElementList;

public class CreateChemicalElementListData {

	public CreateChemicalElementListData() {
		
	}
	
	public ChemicalElementListData create(ChemkinElementList elementList) {
		ArrayList<String> lst = new ArrayList<String>();
		Set<String> keys = elementList.keySet();
		for(String key : keys) {
			lst.add(key);
		}
		ChemicalElementListData data = new ChemicalElementListData(lst);
		return data;
	}

}