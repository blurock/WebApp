package info.esblurock.reaction.data.chemical.elements;

import java.util.ArrayList;

import javax.jdo.annotations.Index;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unindexed;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class ChemicalElementListData extends DatabaseObject {

	private static final long serialVersionUID = 1L;
	
	@Persistent
	String mechanismKeyword;
	
	@Persistent
	@Unindexed
	ArrayList<String> elementList;

	public ChemicalElementListData() {
		super();
	}
	
	public ChemicalElementListData(String mechanismKeyword, ArrayList<String> elementList) {
		super();
		this.mechanismKeyword = mechanismKeyword;
		this.elementList = elementList;
	}

	public ArrayList<String> getElementList() {
		return elementList;
	}
	
	
}
