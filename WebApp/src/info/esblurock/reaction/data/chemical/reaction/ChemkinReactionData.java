package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unindexed;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class ChemkinReactionData extends DatabaseObject  {

	private static final long serialVersionUID = 1L;
	@Persistent
	String mechanismKeyword;
	@Persistent
	String reactionName;
	@Persistent
	@Unindexed
	public ArrayList<String> ReactantNames;
	@Persistent
	@Unindexed
	public ArrayList<String> ProductNames;

	@Persistent
	public boolean forward;
	@Persistent
	public boolean reverse;
	@Persistent
	public boolean low;
	@Persistent
	public boolean troe;
	@Persistent
	public boolean high;
	@Persistent
	public boolean plog;
	@Persistent
	public boolean sri;
	@Persistent(defaultFetchGroup="true")
	@Unindexed
	ArrayList<String> thirdBodyolecules;
	@Persistent(defaultFetchGroup="true")
	@Unindexed
	ArrayList<Double> thirdBodyWeights;

   
    public ChemkinReactionData() {
    }
    
	public ChemkinReactionData(String mechanismKeyword, String reactionName, 
			ArrayList<String> reactantNames, ArrayList<String> productNames,
			boolean forward, boolean reverse, boolean low, 
			boolean troe, boolean high, boolean plog, boolean sri,
			ArrayList<String> thirdBodyMolecules, ArrayList<Double> thirdBodyWeights) {
		super();
		this.mechanismKeyword = mechanismKeyword;
		this.reactionName = reactionName;
		this.ReactantNames = reactantNames;
		this.ProductNames = productNames;
		this.forward = forward;
		this.reverse = reverse;
		this.low = low;
		this.troe = troe;
		this.plog = plog;
		this.high = high;
		this.sri = sri;
		this.thirdBodyolecules = thirdBodyMolecules;
		this.thirdBodyWeights = thirdBodyWeights;
	}
	public ChemkinReactionData(String mechanismKeyword, String reactionName, 
			ArrayList<String> reactantNames, ArrayList<String> productNames,
			boolean forward, boolean reverse, boolean low, boolean high,
			boolean troe, boolean sri, boolean plog) {
		super();
		this.mechanismKeyword = mechanismKeyword;
		this.reactionName = reactionName;
		this.ReactantNames = reactantNames;
		this.ProductNames = productNames;
		this.forward = forward;
		this.reverse = reverse;
		this.low = low;
		this.troe = troe;
		this.plog = plog;
		this.high = high;
		this.sri = sri;
		this.thirdBodyolecules = null;
		this.thirdBodyWeights = null;
	}
	public String getMechanismKeyword() {
		return mechanismKeyword;
	}
	public String getReactionName() {
		return reactionName;
	}
	public ArrayList<String> getReactantKeys() {
		return ReactantNames;
	}
	public ArrayList<String> getProductKeys() {
		return ProductNames;
	}
	public void setThirdBody(ArrayList<String> molecules, ArrayList<Double> weights) {
		this.thirdBodyolecules = molecules;
		this.thirdBodyWeights = weights;
	}
	public ArrayList<String> getThirdBodyMoleculeLabels() {
		return this.thirdBodyolecules;
	}
	public ArrayList<Double> getThirdBodyMoleculeWeights() {
		return this.thirdBodyWeights;
	}
}
