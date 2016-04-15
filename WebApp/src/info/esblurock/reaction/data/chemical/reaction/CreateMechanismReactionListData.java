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
	
	public ArrayList<String> getKeywords(ChemkinReactionList reactionList) {
		ArrayList<String> lst = new ArrayList<String>();
		for(ChemkinReaction reaction : reactionList) {
			String keyword = createReaction.getKeyword(reaction);
			lst.add(keyword);
		}
		return lst;
	}
	
	public MechanismReactionListData create(ChemkinReactionList reactionList) {
		ArrayList<ChemkinReactionData> lst = new ArrayList<ChemkinReactionData>();
		for(ChemkinReaction reaction : reactionList) {
			ChemkinReactionData rxndata = createReaction.create(reaction);
			lst.add(rxndata);
		}		
		MechanismReactionListData data = new MechanismReactionListData(lst);
		return data;
	}
	/**
	 * Creates the.
	 *
	 * @param reactionList the reaction list
	 * @param transaction the transaction
	 * @return the mechanism reaction list data
	 */
	public void create(ArrayList<String> keywords, MechanismReactionListData mechReactionList, TransactionInfo transaction) {
		Iterator<String> iter = keywords.iterator();
		System.out.println("CreateMechanismReactionListData create: " + keywords);
		System.out.println("CreateMechanismReactionListData create: " + mechReactionList);
		for(ChemkinReactionData rxndata : mechReactionList.getReactionSet()) {
			createReaction.create(iter.next(),rxndata,transaction);
		}
		this.merge(createReaction);
	}

}
