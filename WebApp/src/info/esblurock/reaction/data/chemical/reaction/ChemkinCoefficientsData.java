package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class ChemkinCoefficientsData extends DatabaseObject  {
	
	@Persistent
	public boolean forward;
	@Persistent
	public boolean low;
	@Persistent
	public boolean troe;
	
	@Persistent
	public String A;
	@Persistent
	public String n;
	@Persistent
	public String Ea;
	@Persistent
	public ArrayList<String> troeCoeffs;
	
	
	public ChemkinCoefficientsData(boolean forward, boolean low, boolean troe, String a, String n, String ea,
			ArrayList<String> troeCoeffs) {
		super();
		this.forward = forward;
		this.low = low;
		this.troe = troe;
		A = a;
		this.n = n;
		Ea = ea;
		this.troeCoeffs = troeCoeffs;
	}
	public boolean isForward() {
		return forward;
	}
	public boolean isLow() {
		return low;
	}
	public boolean isTroe() {
		return troe;
	}
	public String getA() {
		return A;
	}
	public String getN() {
		return n;
	}
	public String getEa() {
		return Ea;
	}
	public ArrayList<String> getTroeCoeffs() {
		return troeCoeffs;
	}

	
}
