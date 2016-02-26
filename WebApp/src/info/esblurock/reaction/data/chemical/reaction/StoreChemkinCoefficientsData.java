package info.esblurock.reaction.data.chemical.reaction;


import java.text.DecimalFormat;
import java.text.NumberFormat;

import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.StoreObject;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class StoreChemkinCoefficientsData  extends StoreObject  {
	
	static public String forward = "ForwardCoefficients";
	static public String troe = "TroeCoefficients";
	static public String low = "LowPressureCoefficients";

	public StoreChemkinCoefficientsData(String keyword, DatabaseObject object, TransactionInfo transaction) {
		super(keyword, object, transaction);
	}
	protected void storeRDF() {
		super.storeRDF();
	}
	
	protected void storeObject() {
		ChemkinCoefficientsData data = (ChemkinCoefficientsData) object;
		
		double A = Double.parseDouble(data.getA());
		double n = Double.parseDouble(data.getN());
		double Ea = Double.parseDouble(data.getEa());
		
		DecimalFormat formatterExp  = new DecimalFormat("0.####E00");
		DecimalFormat formatterSimp  = new DecimalFormat("#");
		formatterSimp.setMaximumFractionDigits(3);
		String AS = formatterExp.format(A);
		String nS = formatterSimp.format(n);
		String EaS = formatterExp.format(Ea);
		String coeffS = 
				"A=" + AS +
				" n="+ nS +
				" Ea=" + EaS;
		if(data.forward)
			storeStringRDF(forward,coeffS);
		else if(data.troe)
			storeStringRDF(troe,coeffS);
		else if(data.low)
			storeStringRDF(low,coeffS);
	}

}
