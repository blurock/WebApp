package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import info.esblurock.react.mechanisms.chemkin.ChemkinReaction;
import info.esblurock.react.mechanisms.chemkin.ChemkinReactionList;
import info.esblurock.reaction.data.CreateData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateMechanismReactionListData.
 */
public class CreateMechanismReactionListData extends CreateData {
	
	/** The create reaction. */
	CreateChemkinReactionData createReaction;

	String keywordBase;
	ArrayList<ChemkinReactionData> chemkinReactionList;
	
	/**
	 * Instantiates a new creates the mechanism reaction list data.
	 *
	 * In {@link CreateChemkinReactionData} storeObject is false because the list is stored later
	 *
	 * @param keywordBase the keyword base
	 * @param moleculeNamesTable the molecule names table
	 * @param storeObject the store object
	 */
	public CreateMechanismReactionListData(String keywordBase,
			HashMap<String,String> moleculeNamesTable,
			boolean storeObject) {
		createReaction = new CreateChemkinReactionData(keywordBase, moleculeNamesTable,true);
		this.keywordBase = keywordBase;
	}
	
	public MechanismReactionListData create(ChemkinReactionList reactionList) {
		
		chemkinReactionList = new ArrayList<ChemkinReactionData>();
		for(ChemkinReaction reaction : reactionList) {
			ChemkinReactionData rxndata = createReaction.create(reaction);
			chemkinReactionList.add(rxndata);
		}		
		MechanismReactionListData data = new MechanismReactionListData(keywordBase);
		data.setNumberOfReaction(chemkinReactionList.size());
		return data;
	}
	/**
	 * Creates the.
	 *
	 * @param reactionList the reaction list
	 * @param transaction the transaction
	 * @return the mechanism reaction list data
	 */
	public void create(MechanismReactionListData mechReactionList, TransactionInfo transaction) {
		MechanismReactionListData rxnlst = new MechanismReactionListData(keywordBase);
		StoreMechanismReactionListData store = 
				new StoreMechanismReactionListData(keywordBase,mechReactionList,transaction,true);
		store.finish();
		
		for(ChemkinReactionData rxndata : chemkinReactionList) {
			createReaction.create(rxndata.getReactionName(),rxndata,transaction);
		}
		this.merge(createReaction);
		flushCreate();
		for(ChemkinReactionData data : chemkinReactionList) {
			System.out.println("keyword='" + data.getReactionName() + "', Key='" + data.getKey() + "'");
			store.storeObjectRDF(data.getReactionName(), data);
		}
		addStore(store);
	}
}
