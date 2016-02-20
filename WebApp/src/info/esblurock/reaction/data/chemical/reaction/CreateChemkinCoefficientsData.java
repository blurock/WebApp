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
		ArrayList<String> troecoeffs = null;
		if (coeffs.getTroeCoeffs() != null) {
			troecoeffs = new ArrayList<String>();
			String[] tcoeffs = coeffs.getTroeCoeffs();
			for (int i = 0; i < tcoeffs.length; i++) {
				troecoeffs.add(tcoeffs[i]);
			}
		}

		ChemkinCoefficientsData data = new ChemkinCoefficientsData(coeffs.isForward(), coeffs.isLow(), coeffs.isTroe(),
				coeffs.getA(), coeffs.getN(), coeffs.getEa(), troecoeffs);
		StoreChemkinCoefficientsData store = new StoreChemkinCoefficientsData(reactionKeyword, data, transaction);
		return data;
	}

	public String getReactionKeyword() {
		return reactionKeyword;
	}

	public void setReactionKeyword(String reactionKeyword) {
		this.reactionKeyword = reactionKeyword;
	}

}
