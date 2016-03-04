package info.esblurock.reaction.data.chemical.reaction;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreChemkinReactionData  extends StoreObject {
	
	static public String isAProduct  = "isAsProduct";
	static public String isAReactant = "isAsReactant";
	static public String reactionS = "isReaction";
	
	GenerateReactionKeywords generateReactions;

	public StoreChemkinReactionData(String keyword, DatabaseObject object, TransactionInfo transaction, boolean storeObject) {
		super(keyword, object, transaction,storeObject);
		
	}
		protected void storeRDF() {
			generateReactions = new GenerateReactionKeywords(keyword);
			ChemkinReactionData data = (ChemkinReactionData) object;
			String rxnS = generateReactions.getReactionName(data.getReactantKeys(),data.getProductKeys());
			storeStringRDF(reactionS,rxnS);
			for(String name : data.getReactantKeys()) {
				storeStringRDF(isAReactant,name);
			}
			for(String name : data.getProductKeys()) {
				storeStringRDF(isAProduct,name);
			}
			
		}
		
		protected void storeObject() {
		}


}
