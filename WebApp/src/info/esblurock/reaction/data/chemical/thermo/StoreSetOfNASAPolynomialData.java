package info.esblurock.reaction.data.chemical.thermo;

import java.util.ArrayList;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.chemical.molecule.CreateMechanismMoleculeData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreSetOfNASAPolynomialData extends StoreObject  {
	static public String nasapolynomial = "NASAPolynomial";

	public StoreSetOfNASAPolynomialData(String keyword, DatabaseObject object, TransactionInfo transaction,
			boolean storeObject) {
		super(keyword, object, transaction, storeObject);
	}

	protected void storeObject() {
		/*
		SetOfNASAPolynomialData data = (SetOfNASAPolynomialData) object;
		for( NASAPolynomialData nasa: data.getNasaSet()) {
			store(nasa);
		}
		flushStore();
		*/
		super.storeObject();
		super.flushStore();
	}
	
	protected void storeRDF() {
		SetOfNASAPolynomialData data = (SetOfNASAPolynomialData) object;
		for( NASAPolynomialData nasa: data.getNasaSet()) {
			String molname = CreateMechanismMoleculeData.createMoleculeKey(keyword, nasa.getMoleculeName());
			storeObjectRDF(molname, nasa);
			storeStringRDF(nasapolynomial, molname);
		}
	}
	
}
