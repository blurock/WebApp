package info.esblurock.reaction.data.chemical.reaction;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class ThirdBodyWeightsData extends DatabaseObject  {
	
	@Persistent
	String moleculeKey;
	@Persistent
	double weight;

	public ThirdBodyWeightsData(String moleculeKey, double weight) {
		super();
		this.moleculeKey = moleculeKey;
		this.weight = weight;
	}

	public String getMolecule() {
		return moleculeKey;
	}
	public double getWeight() {
		return weight;
	}

}
