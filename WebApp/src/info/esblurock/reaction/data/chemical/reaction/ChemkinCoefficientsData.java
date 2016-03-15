package info.esblurock.reaction.data.chemical.reaction;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.client.data.DatabaseObject;

@PersistenceCapable
public class ChemkinCoefficientsData extends DatabaseObject  {
	
	private static final long serialVersionUID = 1L;
	@Persistent
	public boolean forward;
	@Persistent
	public boolean reverse;
	@Persistent
	public boolean low;
	@Persistent
	public boolean troe;
	@Persistent
	public boolean high;
	@Persistent
	public boolean plog;
	@Persistent
	public boolean sri;
	
	@Persistent
	public String A;
	@Persistent
	public String n;
	@Persistent
	public String Ea;
	@Persistent
	public ArrayList<String> coeffs;
	
	
	public ChemkinCoefficientsData() {
		super();
	}
	public ChemkinCoefficientsData(boolean forward, boolean reverse, boolean low, 
			boolean troe, boolean high, boolean plog, boolean sri,
			String a, String n, String ea,
			ArrayList<String> coeffs) {
		super();
		this.forward = forward;
		this.reverse = reverse;
		this.low = low;
		this.troe = troe;
		this.plog = plog;
		this.high = high;
		this.sri = sri;
		A = a;
		this.n = n;
		Ea = ea;
		this.coeffs = coeffs;
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
	public ArrayList<String> getCoeffs() {
		return coeffs;
	}
	public boolean isReverse() {
		return reverse;
	}
	public boolean isHigh() {
		return high;
	}
	public boolean isPlog() {
		return plog;
	}
	public boolean isSri() {
		return sri;
	}
}
