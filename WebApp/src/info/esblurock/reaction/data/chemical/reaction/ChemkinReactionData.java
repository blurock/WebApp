package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.react.mechanisms.chemkin.ThirdBodyMolecules;
import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class ChemkinReactionData extends DatabaseObject  {

	@Persistent
	public ArrayList<String> ReactantNames;
	@Persistent
	public ArrayList<String> ProductNames;
    @Persistent(dependent = "true")
	public ChemkinCoefficientsData forwardCoefficients;
    @Persistent(dependent = "true")
	public ChemkinCoefficientsData reverseCoefficients;
    @Persistent(dependent = "true")
	public ChemkinCoefficientsData lowCoefficients;
    @Persistent(dependent = "true")
	public ChemkinCoefficientsData troeCoefficients;
    @Persistent(dependent = "true")
	public ThirdBodyMoleculesData thirdBodyMolecules;
    

	public ChemkinReactionData(ArrayList<String> reactantNames, ArrayList<String> productNames,
			ChemkinCoefficientsData forwardCoefficients, ChemkinCoefficientsData reverseCoefficients,
			ChemkinCoefficientsData lowCoefficients, ChemkinCoefficientsData troeCoefficients,
			ThirdBodyMoleculesData thirdBodyMolecules) {
		super();
		this.ReactantNames = reactantNames;
		this.ProductNames = productNames;
		this.forwardCoefficients = forwardCoefficients;
		this.reverseCoefficients = reverseCoefficients;
		this.lowCoefficients = lowCoefficients;
		this.troeCoefficients = troeCoefficients;
		this.thirdBodyMolecules = thirdBodyMolecules;
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
	public ChemkinCoefficientsData getTroeCoefficients() {
		return troeCoefficients;
	}
	public ThirdBodyMoleculesData getThirdBodyMolecules() {
		return thirdBodyMolecules;
	}
    
}
