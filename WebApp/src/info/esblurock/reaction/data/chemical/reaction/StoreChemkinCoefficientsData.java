package info.esblurock.reaction.data.chemical.reaction;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreChemkinCoefficientsData  extends StoreObject  {
	
	static public String reactionCoefficients = "ReactionCoefficients";

	public StoreChemkinCoefficientsData(String keyword, DatabaseObject object, TransactionInfo transaction) {
		super(keyword, object, transaction);
	}
	protected void storeRDF() {
		super.storeRDF();
	}
	
	protected void storeObject() {
		ChemkinCoefficientsData data = (ChemkinCoefficientsData) object;
		
		String coeffS = 
				"A=" + data.getA() +
				" n="+ data.getN() +
				" Ea=" + data.getEa();
		storeStringRDF(reactionCoefficients,coeffS);
	}

}
