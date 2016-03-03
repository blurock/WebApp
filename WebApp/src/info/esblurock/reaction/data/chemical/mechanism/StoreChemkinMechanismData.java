package info.esblurock.reaction.data.chemical.mechanism;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;
import info.esblurock.reaction.data.chemical.reaction.GenerateReactionKeywords;
import info.esblurock.reaction.data.chemical.reaction.MechanismReactionListData;
import info.esblurock.reaction.data.chemical.reaction.ThirdBodyWeightsData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeData;

// TODO: Auto-generated Javadoc
/**
 * The Class StoreChemkinMechanismData.
 */
public class StoreChemkinMechanismData  extends StoreObject  {
	
	

	/**
	 * Instantiates a new store chemkin mechanism data.
	 *
	 * @param keyword the keyword to be used for all the generated RDF's
	 * @param object the object
	 * @param transaction Keeps track of all the transactions
	 * @param storeObject the store object
	 */
	public StoreChemkinMechanismData(String keyword, DatabaseObject object, TransactionInfo transaction,boolean storeObject) {
		super(keyword, object, transaction, storeObject);
	}
	
	/* (non-Javadoc)
	 * @see info.esblurock.reaction.data.StoreObject#storeRDF()
	 */
	protected void storeRDF() {
		super.storeRDF();		
	}
	
	/* (non-Javadoc)
	 * @see info.esblurock.reaction.data.StoreObject#storeObject()
	 */
	protected void storeObject() {
		super.storeObject();
	}
	
	/* (non-Javadoc)
	 * @see info.esblurock.reaction.data.StoreObject#finish()
	 * 
	 * This stores information about the dependent classes
	 */
	public void finish() {
		ChemicalMechanismData data = (ChemicalMechanismData) object;
		for(MechanismMoleculeData molecule : data.getMoleculeList().getMolecules()) {
			// Associate the object with simple name
			String molname = molecule.getMoleculeName().toLowerCase();
			this.storeObjectRDF(molname, molecule);
			// Associate molecule with mechanism name
			this.storeObjectRDF(molecule);
		}
		GenerateReactionKeywords generatekeyword = new GenerateReactionKeywords(keyword);
		for(ChemkinReactionData reaction: data.getReactionList().getReactionSet()) {
			this.storeObjectRDF(reaction);
			String name = generatekeyword.getReactionName(reaction.getReactantKeys(), reaction.getProductKeys());
			this.storeObjectRDF(name, reaction);
		}
		super.finish();
	}
	
}
