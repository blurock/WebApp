package info.esblurock.reaction.data.chemical.reaction;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreChemkinCoefficientsData extends StoreObject {

	static final public String forward = "Forward";
	static final public String reverse = "Reverse";
	static final public String troe = "Troe";
	static final public String low = "LowPressure";
	static final public String high = "HighPressure";
	static final public String plog = "PLOG";
	static final public String sri = "SRI";
	static final public String coefficientsS = "Coefficients";
	
	public StoreChemkinCoefficientsData(String keyword, DatabaseObject object, TransactionInfo transaction) {
		super(keyword, object, transaction);
	}

	protected void storeRDF() {
		super.storeRDF();
	}

	private String stringCoefficients(ChemkinCoefficientsData data) {
		DecimalFormat formatterExp  = new DecimalFormat("0.####E00");
		double A = Double.parseDouble(data.getA());
		double n = Double.parseDouble(data.getN());
		double Ea = Double.parseDouble(data.getEa());
		DecimalFormat formatterSimp  = new DecimalFormat("#");
		formatterSimp.setMaximumFractionDigits(3);
		String AS = formatterExp.format(A);
		String nS = formatterSimp.format(n);
		String EaS = formatterExp.format(Ea);
		String coeffS = 
				"A=" + AS +
				" n="+ nS +
				" Ea=" + EaS;
		return coeffS;
	}

	private String stringConstants(ChemkinCoefficientsData data) {
		DecimalFormat formatterExp  = new DecimalFormat("0.####E00");
		StringBuilder build = new StringBuilder();
		System.out.println("Coefficients: " + data.getCoeffs());
		for(String coef : data.getCoeffs()) {
			double c = Double.parseDouble(coef);
			String cS = formatterExp.format(c);
			build.append(cS);
			build.append(" ");
		}
		String constants = build.toString();
		return constants;
	}

	protected void storeObject() {
		ChemkinCoefficientsData data = (ChemkinCoefficientsData) object;
		
		if(data.forward) {
			String coeffS = stringCoefficients(data);
			String f = forward + coefficientsS;
			storeStringRDF(f,coeffS);
		} else if(data.reverse) {
			String coeffS = stringCoefficients(data);
			String r = reverse + coefficientsS;
			storeStringRDF(r,coeffS);
		} else if(data.low) {
			String coeffS = stringCoefficients(data);
			String l = low + coefficientsS;
			storeStringRDF(l,coeffS);
		} else if(data.high) {
			String coeffS = stringCoefficients(data);
			String h = high + coefficientsS;
			storeStringRDF(h,coeffS);
		} else if(data.sri) {
			String constants = stringConstants(data);
			String s = sri + coefficientsS;
			System.out.println("StoreChemkinCoefficientsData:storeObject() -->'" + s + "´");
			storeStringRDF(s,constants);
		} else if(data.troe) {
			String constants = stringConstants(data);
			String t = troe + coefficientsS;
			storeStringRDF(t,constants);
		} else if(data.plog) {
			String constants = stringConstants(data);
			String p = plog + coefficientsS;
			storeStringRDF(p,constants);
		} else {
			System.out.println("No RDF made for coefficient data: " + data.getKey());
		}
	}

}
