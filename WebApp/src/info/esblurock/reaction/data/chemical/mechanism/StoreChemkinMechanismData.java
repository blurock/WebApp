package info.esblurock.reaction.data.chemical.mechanism;

import org.hamcrest.core.IsAnything;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;
import info.esblurock.reaction.data.chemical.reaction.GenerateReactionKeywords;
import info.esblurock.reaction.data.chemical.reaction.MechanismReactionListData;
import info.esblurock.reaction.data.chemical.reaction.ThirdBodyWeightsData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.chemical.molecule.GenerateMoleculeKeywords;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeData;

// TODO: Auto-generated Javadoc
/**
 * The Class StoreChemkinMechanismData.
 */
public class StoreChemkinMechanismData  extends StoreObject  {
	
	final static String mechanismS = "Mechanism";
	final static String mechanismReaction = "MechanismReaction";

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
		isA(mechanismS);
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
		flushStore();
		ChemicalMechanismData data = (ChemicalMechanismData) object;
		GenerateMoleculeKeywords genMoleculeName = new GenerateMoleculeKeywords(keyword);
		for(MechanismMoleculeData molecule : data.getMoleculeList().getMolecules()) {
			String molname = genMoleculeName.getDataKeyword(molecule);
			storeObjectRDF(molname, molecule);
		}
		GenerateReactionKeywords generatekeyword = new GenerateReactionKeywords(keyword);
		for(ChemkinReactionData reaction: data.getReactionList().getReactionSet()) {
			String name = generatekeyword.getReactionFullName(reaction);
			storeStringRDF(mechanismReaction, name);
			storeObjectRDF(name, reaction);
		}
		super.finish();
	}
	
}
