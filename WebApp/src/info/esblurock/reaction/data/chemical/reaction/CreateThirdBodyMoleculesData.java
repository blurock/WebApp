package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import info.esblurock.react.mechanisms.chemkin.ThirdBodyMolecules;
import info.esblurock.react.mechanisms.chemkin.ThirdBodyWeight;
import info.esblurock.reaction.data.chemical.molecule.CreateMechanismMoleculeData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class CreateThirdBodyMoleculesData {
	
	/** The molecule keys table. */
	// moleculeName (label in ChemkinMolecule) -> full molecule name
	HashMap<String,String> moleculeNamesTable;

	String reactionKeyword;

	public CreateThirdBodyMoleculesData(String reactionKeyword, 
			HashMap<String,String> moleculeNamesTable) {
		this.reactionKeyword = reactionKeyword;
		this.moleculeNamesTable = moleculeNamesTable;
	}
	
	/**
	 * Gets the third body molecule data  {@link ThirdBodyMolecules}
	 * 
	 * This routine stores each third body (using storeThirdBodyWeightsData):
	 * <ul>
	 * <li> for each {@link ThirdBodyMoleculesData} produce {@link ThirdBodyMoleculesData}
	 * <li> for each {@link ThirdBodyMoleculesData} store and produce key
	 * <li> accumulate keys into ArrayList
	 * <li> Produce {@link ThirdBodyMoleculesData}
	 * <li> return {@link ThirdBodyMoleculesData}
	 * <ul>.
	 *
	 * @param reactionKeyword the reaction keyword
	 * @param thirdbodies the third body data from {@link ThirdBodyMolecules}
	 * @param transaction the transaction
	 * @return the third body molecule data
	 */
	public ThirdBodyMoleculesData create(ThirdBodyMolecules thirdbodies, 
			TransactionInfo transaction) {
		ArrayList<ThirdBodyWeightsData> thirdBodyMoleculeKeys = new ArrayList<ThirdBodyWeightsData>();
		
		Set<String> keys = thirdbodies.keySet();
		for(String key : keys) {
			ThirdBodyWeight thirdbody = thirdbodies.get(key);
			ThirdBodyWeightsData third = storeThirdBodyWeightsData(thirdbody,transaction);
			thirdBodyMoleculeKeys.add(third);
		}
		ThirdBodyMoleculesData thirds = new ThirdBodyMoleculesData(thirdBodyMoleculeKeys);
		StoreThirdBodyMoleculesData store = new StoreThirdBodyMoleculesData(reactionKeyword, thirds, transaction);
		return thirds;
	}
	
	/**
	 * Store third body weights data from {@link ThirdBodyWeight} 
	 * The keyword that is used is that of the total reaction
	 * 
	 * This uses the {@link StoreThirdBodyWeightsData} class to store the information and return the key.
	 *
	 * @param reactionKeyword the reaction keyword
	 * @param thirdbody the {@link ThirdBodyWeight} as read in from CHEMKIN mechanism
	 * @param transaction the transaction
	 * @return the class {@link ThirdBodyWeightsData}
	 */
	private ThirdBodyWeightsData storeThirdBodyWeightsData(ThirdBodyWeight thirdbody, TransactionInfo transaction) {
		double weight = thirdbody.getWeight();
		//String moleculename = moleculeNamesTable.get(thirdbody.getMolecule().getLabel());
		ThirdBodyWeightsData data = new ThirdBodyWeightsData(thirdbody.getMolecule().getLabel(), weight);
		StoreThirdBodyWeightsData store = new StoreThirdBodyWeightsData(reactionKeyword, data, transaction);
		return data;
	}


}
