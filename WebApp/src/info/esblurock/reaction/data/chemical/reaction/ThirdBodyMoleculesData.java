package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class ThirdBodyMoleculesData extends DatabaseObject  {

	@Persistent
	public ArrayList<ThirdBodyWeightsData> thirdBodyMoleculeKeys;

	public ThirdBodyMoleculesData(ArrayList<ThirdBodyWeightsData> thirdBodyMoleculeKeys) {
		super();
		this.thirdBodyMoleculeKeys = thirdBodyMoleculeKeys;
	}

	public ArrayList<ThirdBodyWeightsData> getThirdBodyMoleculeKeys() {
		return thirdBodyMoleculeKeys;
	}
		
}
