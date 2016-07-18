package info.esblurock.reaction.data.chemical.molecule.isomer;

import java.util.Map;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unindexed;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class IsomerData extends DatabaseObject {

	@Persistent
	String isomerName;
	
	@Persistent
	@Unindexed
	Map<String,Integer> atomCounts;

	
	public IsomerData() {
		super();
	}
	public IsomerData(String isomerName, Map<String, Integer> atomCounts) {
		super();
		this.atomCounts = atomCounts;
		this.isomerName = isomerName;
	}

	public Map<String, Integer> getAtomCounts() {
		return atomCounts;
	}
	
	public Integer getAtomCount(String atomS) {
		return atomCounts.get(atomS);
	}
}
