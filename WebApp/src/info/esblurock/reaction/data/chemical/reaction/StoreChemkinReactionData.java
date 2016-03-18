package info.esblurock.reaction.data.chemical.reaction;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreChemkinReactionData  extends StoreObject {
	
	static public String isAProduct  = "isAsProduct";
	static public String isAReactant = "isAsReactant";
	static public String reactionS = "isReaction";
	static public String hasCoefficientS = "hasCoefficients";
	
	GenerateReactionKeywords generateReactions;

	public StoreChemkinReactionData(String keyword, DatabaseObject object, TransactionInfo transaction, boolean storeObject) {
		super(keyword, object, transaction,storeObject);
		
	}
		protected void storeRDF() {
			generateReactions = new GenerateReactionKeywords(keyword);
			ChemkinReactionData data = (ChemkinReactionData) object;
			String rxnS = generateReactions.getReactionSimpleName(data);
			storeStringRDF(reactionS,rxnS);

			for(String name : data.getReactantKeys()) {
				storeStringRDF(isAReactant,name);
			}
			for(String name : data.getProductKeys()) {
				storeStringRDF(isAProduct,name);
			}
			if(data.getForwardCoefficients() != null) {
				String key = keyword + ":" + StoreChemkinCoefficientsData.forward;
				storeStringRDF(hasCoefficientS,key);
			}
			if(data.getReverseCoefficients() != null) {
				String key = keyword + ":" + StoreChemkinCoefficientsData.reverse;
				storeStringRDF(hasCoefficientS,key);
			}
			if(data.getLowCoefficients() != null) {
				String key = keyword + ":" + StoreChemkinCoefficientsData.low;
				storeStringRDF(hasCoefficientS,key);
			}
			if(data.getHighCoefficients() != null) {
				String key = keyword + ":" + StoreChemkinCoefficientsData.high;
				storeStringRDF(hasCoefficientS,key);
			}
			if(data.getTroeCoefficients() != null) {
				String key = keyword + ":" + StoreChemkinCoefficientsData.troe;
				storeStringRDF(hasCoefficientS,key);
			}
			if(data.getSriCoefficients() != null) {
				String key = keyword + ":" + StoreChemkinCoefficientsData.sri;
				System.out.println("StoreChemkinReactionData(" + hasCoefficientS + "): " + key);
				storeStringRDF(hasCoefficientS,key);
			}
			if(data.getPlogCoefficients() != null) {
				String key = keyword + ":" + StoreChemkinCoefficientsData.plog;
				storeStringRDF(hasCoefficientS,key);
			}
		}
		
		protected void storeObject() {
		}


}
