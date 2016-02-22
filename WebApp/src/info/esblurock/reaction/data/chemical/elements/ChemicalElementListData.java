package info.esblurock.reaction.data.chemical.elements;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class ChemicalElementListData extends DatabaseObject {

	private static final long serialVersionUID = 1L;
	
	@Persistent
	ArrayList<String> elementList;

	public ChemicalElementListData(ArrayList<String> elementList) {
		super();
		this.elementList = elementList;
	}

	public ArrayList<String> getElementList() {
		return elementList;
	}
	
	
}
