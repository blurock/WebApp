package info.esblurock.reaction.data.chemical.elements;

import java.util.ArrayList;

import info.esblurock.reaction.client.data.DatabaseObject;

public class ChemicalElementListData extends DatabaseObject {

	private static final long serialVersionUID = 1L;
	
	ArrayList<String> elementList;

	public ChemicalElementListData(ArrayList<String> elementList) {
		super();
		this.elementList = elementList;
	}

	public ArrayList<String> getElementList() {
		return elementList;
	}
	
	
}
