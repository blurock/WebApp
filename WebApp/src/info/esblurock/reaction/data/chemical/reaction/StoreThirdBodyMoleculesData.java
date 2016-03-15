package info.esblurock.reaction.data.chemical.reaction;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreThirdBodyMoleculesData extends StoreObject {
	
	final String reactionThirdBodyMolelculeS = "ThirdBodyMolecule";

	public StoreThirdBodyMoleculesData(String keyword, DatabaseObject object, TransactionInfo transaction) {
		super(keyword, object, transaction);
	}
	
	protected void storeRDF() {
		super.storeRDF();
	}
	
	protected void storeObject() {
		ThirdBodyMoleculesData data = (ThirdBodyMoleculesData) object;
		
		StringBuilder build = new StringBuilder();
		build.append("[");
		for(ThirdBodyWeightsData weights : data.getThirdBodyMoleculeKeys()) {
			build.append(weights.getMolecule());
			build.append(" ");
		}
		build.append("]");
		storeStringRDF(reactionThirdBodyMolelculeS,build.toString());
	}

}
