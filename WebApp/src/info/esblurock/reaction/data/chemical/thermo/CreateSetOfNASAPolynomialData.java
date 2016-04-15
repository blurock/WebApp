package info.esblurock.reaction.data.chemical.thermo;

import java.util.ArrayList;
import java.util.logging.Logger;

import info.esblurock.reaction.data.CreateData;
import info.esblurock.reaction.data.chemical.molecule.CreateMechanismMoleculeData;
import info.esblurock.reaction.data.chemical.molecule.isomer.CreateIsomerData;
import info.esblurock.reaction.data.chemical.molecule.isomer.IsomerData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.server.TextToDatabaseImpl;
import thermo.data.benson.NASAPolynomial;
import thermo.data.benson.SetOfThermodynamicInformation;
import thermo.data.benson.ThermodynamicInformation;

public class CreateSetOfNASAPolynomialData extends CreateData {

	private static Logger log = Logger.getLogger(TextToDatabaseImpl.class.getName());

	String setBaseName;

	CreateSetOfNASAPolynomialData() {
	}
	
	public SetOfNASAPolynomialData create(SetOfThermodynamicInformation set) {
		SetOfNASAPolynomialData thermoset = new SetOfNASAPolynomialData();
		for(ThermodynamicInformation thermo : set) {
			NASAPolynomial nasa = (NASAPolynomial) thermo;
			NASAPolynomialData nasadata = create(nasa);
			thermoset.addThermo(nasadata);
		}
		return thermoset;		
	}
		public void create(String setBaseName, SetOfNASAPolynomialData set,  TransactionInfo transaction) {
		for(NASAPolynomialData nasadata : set.getNasaSet()) {
			String molname = CreateMechanismMoleculeData.createMoleculeKey(setBaseName, nasadata.getMoleculeName());
			StoreNASAPolynomialData store = new StoreNASAPolynomialData(molname, nasadata, transaction, false);
			this.addStore(store);
		}
	}
	
	/**
	 * Creates the.
	 *
	 * @param nasa the NASA polynomial that 
	 * @return the NASA polynomial data
	 */
	public NASAPolynomialData create(NASAPolynomial nasa) {
		
		CreateIsomerData createisomer = new CreateIsomerData();
		IsomerData isomer = createisomer.create(nasa);
		
		
		Double enthalpy = new Double(nasa.getStandardEnthalpy298());
		Double entropy = new Double(nasa.getStandardEntropy298());
		
		
		String moleculeName = nasa.getName();
		String phase = nasa.phase;
		Double lowerT = new Double(nasa.lowerT);
		Double middleT = new Double(nasa.middleT);
		Double upperT = new Double(nasa.upperT);
		
		ArrayList<Double> upper = new ArrayList<Double>();
		for(int i=0;i<7;i++) {
			Double c = new Double(nasa.upper[i]);
			upper.add(c);
		}
		ArrayList<Double> lower = new ArrayList<Double>();
		for(int i=0;i<7;i++) {
			Double c = new Double(nasa.lower[i]);
			lower.add(c);
		}
		
		NASAPolynomialData NASA = new NASAPolynomialData(moleculeName, isomer, 
				lowerT, middleT, upperT,
				enthalpy, entropy,
				upper, lower, phase);
		return NASA;	
	}
}
