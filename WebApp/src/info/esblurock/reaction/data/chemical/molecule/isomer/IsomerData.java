package info.esblurock.reaction.data.chemical.molecule.isomer;

import java.util.Map;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class IsomerData extends DatabaseObject {

	@Persistent
	Map<String,Integer> atomCounts;

	
	public IsomerData() {
		super();
	}
	public IsomerData(Map<String, Integer> atomCounts) {
		super();
		this.atomCounts = atomCounts;
	}

	public Map<String, Integer> getAtomCounts() {
		return atomCounts;
	}
	
	public Integer getAtomCount(String atomS) {
		return atomCounts.get(atomS);
	}
}
