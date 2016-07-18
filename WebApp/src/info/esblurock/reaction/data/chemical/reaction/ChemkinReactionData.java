package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unindexed;

import info.esblurock.react.mechanisms.chemkin.ThirdBodyMolecules;
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
	
    @Persistent(dependent = "true")
	@Unindexed
	public ChemkinCoefficientsData forwardCoefficients;
    @Persistent(dependent = "true")
	@Unindexed
	public ChemkinCoefficientsData reverseCoefficients;
    @Persistent(dependent = "true")
	@Unindexed
	public ChemkinCoefficientsData lowCoefficients;
    @Persistent(dependent = "true")
	@Unindexed
	public ChemkinCoefficientsData highCoefficients;

    @Persistent(dependent = "true")
	@Unindexed
	public ChemkinCoefficientsData troeCoefficients;
    @Persistent(dependent = "true")
	@Unindexed
	public ChemkinCoefficientsData sriCoefficients;
    
	@Persistent
	@Element(dependent = "true")
	@Unindexed
    ArrayList<ChemkinCoefficientsData> plogCoefficients;
    
    @Persistent(dependent = "true")
	public ThirdBodyMoleculesData thirdBodyMolecules;
    
    public ChemkinReactionData() {
    	
    }
    
	public ChemkinReactionData(String mechanismKeyword, String reactionName, 
			ArrayList<String> reactantNames, ArrayList<String> productNames,
			ChemkinCoefficientsData forwardCoefficients, ChemkinCoefficientsData reverseCoefficients,
			ChemkinCoefficientsData lowCoefficients, ChemkinCoefficientsData highCoefficients,
			ChemkinCoefficientsData troeCoefficients, ChemkinCoefficientsData sriCoefficients,
			ArrayList<ChemkinCoefficientsData> plogCoefficients,
			ThirdBodyMoleculesData thirdBodyMolecules) {
		super();
		this.mechanismKeyword = mechanismKeyword;
		this.reactionName = reactionName;
		this.ReactantNames = reactantNames;
		this.ProductNames = productNames;
		this.forwardCoefficients = forwardCoefficients;
		this.reverseCoefficients = reverseCoefficients;
		this.lowCoefficients = lowCoefficients;
		this.highCoefficients = highCoefficients;
		this.troeCoefficients = troeCoefficients;
		this.sriCoefficients = sriCoefficients;
		this.plogCoefficients = plogCoefficients;
		this.thirdBodyMolecules = thirdBodyMolecules;
	}
	public String getMechanismKeyword() {
		return mechanismKeyword;
	}
	public ArrayList<String> getReactantKeys() {
		return ReactantNames;
	}
	public ArrayList<String> getProductKeys() {
		return ProductNames;
	}
	public ChemkinCoefficientsData getForwardCoefficients() {
		return forwardCoefficients;
	}
	public ChemkinCoefficientsData getReverseCoefficients() {
		return reverseCoefficients;
	}
	public ChemkinCoefficientsData getLowCoefficients() {
		return lowCoefficients;
	}
	public ChemkinCoefficientsData getHighCoefficients() {
		return highCoefficients;
	}
	public ChemkinCoefficientsData getTroeCoefficients() {
		return troeCoefficients;
	}
	public ChemkinCoefficientsData getSriCoefficients() {
		return sriCoefficients;
	}
	public ArrayList<ChemkinCoefficientsData> getPlogCoefficients() {
		return plogCoefficients;
	}
	public ThirdBodyMoleculesData getThirdBodyMolecules() {
		return thirdBodyMolecules;
	}

	public String getReactionName() {
		return reactionName;
	}
    
}
