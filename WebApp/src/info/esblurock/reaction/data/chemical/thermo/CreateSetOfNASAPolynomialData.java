package info.esblurock.reaction.data.chemical.thermo;

import java.util.ArrayList;

import info.esblurock.reaction.data.chemical.molecule.CreateMechanismMoleculeData;
import info.esblurock.reaction.data.chemical.molecule.isomer.CreateIsomerData;
import info.esblurock.reaction.data.chemical.molecule.isomer.IsomerData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import thermo.data.benson.NASAPolynomial;
import thermo.data.benson.SetOfThermodynamicInformation;
import thermo.data.benson.ThermodynamicInformation;

public class CreateSetOfNASAPolynomialData {
	
	String setBaseName;

	CreateSetOfNASAPolynomialData() {
		this.setBaseName = setBaseName;
	}
	
	public SetOfNASAPolynomialData create(String setBaseName, SetOfThermodynamicInformation set,  TransactionInfo transaction) {
		SetOfNASAPolynomialData thermoset = new SetOfNASAPolynomialData();
		System.out.println("SetOfNASAPolynomialData create start");
		for(ThermodynamicInformation thermo : set) {
			NASAPolynomial nasa = (NASAPolynomial) thermo;
			NASAPolynomialData nasadata = create(nasa);
			thermoset.addThermo(nasadata);
			String molname = CreateMechanismMoleculeData.createMoleculeKey(setBaseName, nasadata.getMoleculeName());
			System.out.println("StoreNASAPolynomialData: Store");
			System.out.println("StoreNASAPolynomialData: " + nasadata.getMoleculeComposition().getAtomCounts().toString());
			StoreNASAPolynomialData store = new StoreNASAPolynomialData(molname, nasadata, transaction, false);
			System.out.println("StoreNASAPolynomialData: " + nasadata.getMoleculeComposition().getAtomCounts().toString());
			System.out.println("StoreNASAPolynomialData: Done Store");
		}
		System.out.println("SetOfNASAPolynomialData create start");
		return thermoset;
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
		System.out.println("NASAPolynomialData create: " + moleculeName);
		String phase = nasa.phase;
		Double lowerT = new Double(nasa.lowerT);
		Double middleT = new Double(nasa.middleT);
		Double upperT = new Double(nasa.upperT);
		System.out.println("NASAPolynomialData create: " + isomer.getAtomCounts().toString());
		
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
		System.out.println("NASAPolynomialData create: " + isomer.getAtomCounts().toString());
		
		NASAPolynomialData NASA = new NASAPolynomialData(moleculeName, isomer, 
				lowerT, middleT, upperT,
				enthalpy, entropy,
				upper, lower, phase);
		System.out.println("NASAPolynomialData create: " + NASA.getMoleculeComposition().toString());
		System.out.println("NASAPolynomialData create: " + NASA.getMoleculeComposition().getAtomCounts().toString());
		return NASA;	
	}
}
