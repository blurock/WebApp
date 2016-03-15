package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;
import java.util.Set;

import info.esblurock.react.mechanisms.chemkin.ChemkinCoefficients;
import info.esblurock.react.mechanisms.chemkin.ThirdBodyMolecules;
import info.esblurock.react.mechanisms.chemkin.ThirdBodyWeight;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class CreateChemkinCoefficientsData {

	String reactionKeyword;

	CreateChemkinCoefficientsData(String reactionKeyword) {
		this.reactionKeyword = reactionKeyword;
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
	public ChemkinCoefficientsData create(ChemkinCoefficients coeffs, TransactionInfo transaction) {
		ArrayList<String> coeffvalues = transferConstants(coeffs.getCoeffs());

		System.out.println("CreateChemkinCoefficientsData: " + coeffs.toString());
		ChemkinCoefficientsData data = new ChemkinCoefficientsData(coeffs.isForward(), coeffs.isReverse(),
				coeffs.isLow(), coeffs.isTroe(),
				coeffs.isHigh(), coeffs.isPlog(),coeffs.isSri(),
				coeffs.getA(), coeffs.getN(), coeffs.getEa(), 
				coeffvalues);
		StoreChemkinCoefficientsData store = new StoreChemkinCoefficientsData(reactionKeyword, data, transaction);
		return data;
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
