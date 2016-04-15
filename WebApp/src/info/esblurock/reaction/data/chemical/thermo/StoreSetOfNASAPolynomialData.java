package info.esblurock.reaction.data.chemical.thermo;

import java.util.ArrayList;
import java.util.logging.Logger;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.chemical.molecule.CreateMechanismMoleculeData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.server.TextToDatabaseImpl;

public class StoreSetOfNASAPolynomialData extends StoreObject  {
	static public String nasapolynomial = "NASAPolynomial";
	private static Logger log = Logger.getLogger(StoreSetOfNASAPolynomialData.class.getName());

	public StoreSetOfNASAPolynomialData(String keyword, DatabaseObject object, TransactionInfo transaction,
			boolean storeObject) {
		super(keyword, object, transaction, storeObject);
	}

	protected void storeObject() {
		SetOfNASAPolynomialData data = (SetOfNASAPolynomialData) object;
		/*
		for( NASAPolynomialData nasa: data.getNasaSet()) {
			store(nasa);
		}
		*/
		super.storeObject();
	}
	
	protected void storeRDF() {
		SetOfNASAPolynomialData data = (SetOfNASAPolynomialData) object;
		for( NASAPolynomialData nasa: data.getNasaSet()) {
			String molname = CreateMechanismMoleculeData.createMoleculeKey(keyword, nasa.getMoleculeName());
			storeStringRDF(nasapolynomial, molname);
		}
	}
	public void finish() {
		flushStore();
		SetOfNASAPolynomialData data = (SetOfNASAPolynomialData) object;
		for( NASAPolynomialData nasa: data.getNasaSet()) {
			String molname = CreateMechanismMoleculeData.createMoleculeKey(keyword, nasa.getMoleculeName());
			storeObjectRDF(molname, nasa);
		}
		super.finish();
	}
}
