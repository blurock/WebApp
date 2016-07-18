package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unindexed;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class ThirdBodyMoleculesData extends DatabaseObject  {

	private static final long serialVersionUID = 1L;
	@Persistent
	@Element(dependent = "true")
	@Unindexed
	public ArrayList<ThirdBodyWeightsData> thirdBodyMoleculeKeys;

	
	
	public ThirdBodyMoleculesData() {
		super();
	}

	public ThirdBodyMoleculesData(ArrayList<ThirdBodyWeightsData> thirdBodyMoleculeKeys) {
		super();
		this.thirdBodyMoleculeKeys = thirdBodyMoleculeKeys;
	}

	public ArrayList<ThirdBodyWeightsData> getThirdBodyMoleculeKeys() {
		return thirdBodyMoleculeKeys;
	}
		
}
