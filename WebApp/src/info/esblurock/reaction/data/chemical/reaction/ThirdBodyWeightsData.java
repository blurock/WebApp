package info.esblurock.reaction.data.chemical.reaction;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unindexed;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class ThirdBodyWeightsData extends DatabaseObject  {
	
	private static final long serialVersionUID = 1L;
	@Persistent
	@Unindexed
	String moleculeKey;
	@Persistent
	@Unindexed
	double weight;
	
	public ThirdBodyWeightsData() {
		super();
	}

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
