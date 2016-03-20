package info.esblurock.reaction.data.chemical.thermo;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.chemical.molecule.isomer.CreateIsomerData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreNASAPolynomialData extends StoreObject {
	
	static public String isomerPredicate = "Isomer";
	static public String standardEnthalpy = "StandardEnthalpy";
	static public String standardEntropy = "StandardEntropy";

	public StoreNASAPolynomialData(String keyword, DatabaseObject object, TransactionInfo transaction, boolean storeObject) {
		super(keyword, object, transaction,storeObject);
	}

	protected void storeObject() {
		super.storeObject();
	}
	
	protected void storeRDF() {
		NASAPolynomialData data = (NASAPolynomialData) object;
		String standard = CreateIsomerData.standardIsomerName(data.getMoleculeComposition());
		storeStringRDF(isomerPredicate, standard);
		if(data.getStandardEnthalpy() != null)
		storeStringRDF(standardEnthalpy, data.getStandardEnthalpy().toString());
		if(data.getStandardEntropy() != null)
		storeStringRDF(standardEntropy, data.getStandardEntropy().toString());
	}

	
}
