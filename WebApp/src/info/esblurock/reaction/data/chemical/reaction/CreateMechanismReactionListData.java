package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;
import java.util.HashMap;

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
		createReaction = new CreateChemkinReactionData(keywordBase, moleculeNamesTable,false);
	}
	
	/**
	 * Creates the.
	 *
	 * @param reactionList the reaction list
	 * @param transaction the transaction
	 * @return the mechanism reaction list data
	 */
	public MechanismReactionListData create(ChemkinReactionList reactionList,
			TransactionInfo transaction) {
		ArrayList<ChemkinReactionData> lst = new ArrayList<ChemkinReactionData>();
		for(ChemkinReaction reaction : reactionList) {
			ChemkinReactionData rxndata = createReaction.create(reaction, transaction);
			lst.add(rxndata);
		}
		this.merge(createReaction);
		MechanismReactionListData data = new MechanismReactionListData(lst);
		return data;
	}

}
