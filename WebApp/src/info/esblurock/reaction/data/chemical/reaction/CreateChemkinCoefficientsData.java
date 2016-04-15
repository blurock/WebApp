package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;
import java.util.Set;

import info.esblurock.react.mechanisms.chemkin.ChemkinCoefficients;
import info.esblurock.react.mechanisms.chemkin.ThirdBodyMolecules;
import info.esblurock.react.mechanisms.chemkin.ThirdBodyWeight;
import info.esblurock.reaction.data.CreateData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class CreateChemkinCoefficientsData extends CreateData {

	String reactionKeyword;

	CreateChemkinCoefficientsData(String reactionKeyword) {
		this.reactionKeyword = reactionKeyword;
	}

	
	public ChemkinCoefficientsData create(ChemkinCoefficients coeffs) {
		ArrayList<String> coeffvalues = transferConstants(coeffs.getCoeffs());

		ChemkinCoefficientsData data = new ChemkinCoefficientsData(coeffs.isForward(), coeffs.isReverse(),
				coeffs.isLow(), coeffs.isTroe(),
				coeffs.isHigh(), coeffs.isPlog(),coeffs.isSri(),
				coeffs.getA(), coeffs.getN(), coeffs.getEa(), 
				coeffvalues);
		return data;
	}
	/**
	 * Store chemkin coefficients data.
	 *
	 * @param reactionKeyword
	 *            the reaction keyword
	 * @param coeffs
	 *            the coeffs
	 * @param transaction
	 *            the transaction
	 * @return the chemkin coefficients data
	 */
	public void create(ChemkinCoefficientsData data, TransactionInfo transaction) {
		StoreChemkinCoefficientsData store = new StoreChemkinCoefficientsData(reactionKeyword, data, transaction);
		this.addStore(store);
	}

	private ArrayList<String> transferConstants(String[] original) {
		ArrayList<String> coeffs = null;
		if (original != null) {
			coeffs = new ArrayList<String>();
			String[] tcoeffs = original;
			for (int i = 0; i < tcoeffs.length; i++) {
				coeffs.add(tcoeffs[i]);
			}
		}
		return coeffs;
	}
	
	public String getReactionKeyword() {
		return reactionKeyword;
	}

	public void setReactionKeyword(String reactionKeyword) {
		this.reactionKeyword = reactionKeyword;
	}

}
