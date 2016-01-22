package info.esblurock.react.mechanisms.chemkin;

public class ReactionForwardReverseType {
	String EquivString = "=";
	boolean forward = false;
	boolean reverse = false;
	boolean reversible = true;
	
	ReactionForwardReverseType() {
	}

	public ReactionForwardReverseType(String equivString, boolean forward,
			boolean reverse, boolean reversible) {
		super();
		EquivString = equivString;
		this.forward = forward;
		this.reverse = reverse;
		this.reversible = reversible;
	}

	public String getEquivString() {
		return EquivString;
	}

	public boolean isForward() {
		return forward;
	}

	public boolean isReverse() {
		return reverse;
	}

	public boolean isReversible() {
		return reversible;
	}

}
