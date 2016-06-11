package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import info.esblurock.react.mechanisms.chemkin.ThirdBodyMolecules;
import info.esblurock.react.mechanisms.chemkin.ThirdBodyWeight;
import info.esblurock.reaction.data.CreateData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class CreateThirdBodyMoleculesData extends CreateData {
	
	/** The molecule keys table. */
	// moleculeName (label in ChemkinMolecule) -> full molecule name
	Map<String,String> moleculeNamesTable;

	String reactionKeyword;

	public CreateThirdBodyMoleculesData(String reactionKeyword, 
			Map<String,String> moleculeNamesTable) {
		this.reactionKeyword = reactionKeyword;
		this.moleculeNamesTable = moleculeNamesTable;
	}
	public ThirdBodyMoleculesData create(ThirdBodyMolecules thirdbodies) {
		ArrayList<ThirdBodyWeightsData> thirdBodyMoleculeKeys = new ArrayList<ThirdBodyWeightsData>();
		
		Set<String> keys = thirdbodies.keySet();
		for(String key : keys) {
			ThirdBodyWeight thirdbody = thirdbodies.get(key);
			double weight = thirdbody.getWeight();
			ThirdBodyWeightsData third = new ThirdBodyWeightsData(thirdbody.getMolecule().getLabel(), weight);
			thirdBodyMoleculeKeys.add(third);
		}
		ThirdBodyMoleculesData thirds = new ThirdBodyMoleculesData(thirdBodyMoleculeKeys);
		return thirds;
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
	public void create(ThirdBodyMoleculesData thirdbodies, TransactionInfo transaction) {
		for(ThirdBodyWeightsData third : thirdbodies.getThirdBodyMoleculeKeys()) {
			StoreThirdBodyWeightsData store = new StoreThirdBodyWeightsData(reactionKeyword, third, transaction);
			addStore(store);
		}
		StoreThirdBodyMoleculesData storeset = new StoreThirdBodyMoleculesData(reactionKeyword, thirdbodies, transaction);
		addStore(storeset);
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
		addStore(store);
		return data;
	}


}
