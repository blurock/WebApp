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

	ArrayList<NASAPolynomialData> nasapolynomialset;
	
	
	public StoreSetOfNASAPolynomialData(String keyword, DatabaseObject object, TransactionInfo transaction,
			boolean storeObject) {
		super(keyword, object, transaction, storeObject);
	}

	protected void storeObject() {
		SetOfNASAPolynomialData data = (SetOfNASAPolynomialData) object;
		super.storeObject();
	}
	
	protected void storeRDF() {
	}
	
	public void setListOfNASAPolynomials(ArrayList<NASAPolynomialData> set) {
		nasapolynomialset = set;
	}
	public void finish() {
		super.finish();
	}
}
