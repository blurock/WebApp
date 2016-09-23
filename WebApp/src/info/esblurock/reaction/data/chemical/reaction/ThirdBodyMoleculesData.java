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
	@Persistent(defaultFetchGroup="true")
	@Element(dependent = "true")
	public ArrayList<ThirdBodyWeightsData> thirdBodyMoleculeKeys;

	@Persistent(defaultFetchGroup="true")
	ArrayList<String> molecules;
	@Persistent(defaultFetchGroup="true")
	ArrayList<Double> weights;
	
	public ThirdBodyMoleculesData() {
		super();
	}

	public ThirdBodyMoleculesData(ArrayList<ThirdBodyWeightsData> thirdBodyMoleculeKeys) {
		super();
		this.thirdBodyMoleculeKeys = thirdBodyMoleculeKeys;
		molecules = new ArrayList<String>();
		weights = new ArrayList<Double>();
		for(ThirdBodyWeightsData weight : thirdBodyMoleculeKeys) {
			molecules.add(weight.getMolecule());
			weights.add(weight.getWeight());
		}
	}

	public ArrayList<ThirdBodyWeightsData> getThirdBodyMoleculeKeys() {
		if(thirdBodyMoleculeKeys == null) {
			if(molecules != null && weights != null) {
				thirdBodyMoleculeKeys = new ArrayList<ThirdBodyWeightsData>();
				for(int i=0; i< molecules.size(); i++) {
					ThirdBodyWeightsData data = new ThirdBodyWeightsData(molecules.get(i),weights.get(i).doubleValue() );
					thirdBodyMoleculeKeys.add(data);
				}
			} else {
				System.out.println("Weights not retrieved properly");
			}
		}
		return thirdBodyMoleculeKeys;
	}
		
}
