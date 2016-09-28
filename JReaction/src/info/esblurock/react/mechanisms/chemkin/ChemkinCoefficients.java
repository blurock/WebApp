package info.esblurock.react.mechanisms.chemkin;

import java.io.IOException;
import java.util.StringTokenizer;

public class ChemkinCoefficients {
	private boolean forward;
	private boolean reverse;
	private boolean low;
	private boolean troe;
	private boolean high;
	private boolean plog;
	private boolean sri;

	private String A, n, Ea;
	private String[] coeffs;

	private String forwardS;
	private String reverseS;

	private String reverseCoeffsS;
	private String troeCoeffsS;
	private String lowCoeffsS;
	private String highCoeffsS;
	private String plogCoeffsS;
	private String sriCoeffsS;
	
	private String comments = "";

	private void init() {
		forwardS = "Forward:";
		reverseS = "Reverse:";
		reverseCoeffsS = "REV";
		troeCoeffsS = "TROE";
		lowCoeffsS = "LOW";
		highCoeffsS = "HIGH";
		plogCoeffsS = "PLOG";
		sriCoeffsS = "SRI";
		coeffs = null;
		A = null;
		n = null;
		Ea = null;

		forward = false;
		reverse = false;
		low = false;
		troe = false;
		high = false;
		plog = false;
		sri = false;

	}

	public ChemkinCoefficients() {
		init();
	}

	public ChemkinCoefficients(boolean forward) {
		init();
		this.forward = forward;
	}

	public boolean parseReverse(String trimmed) throws IOException {
		reverse = false;
		if (trimmed.toUpperCase().startsWith(reverseCoeffsS)) {
			reverse = true;
			int pos1 = trimmed.indexOf("/");
			int pos2 = trimmed.indexOf("/", pos1 + 1);
			parseCoeffs(trimmed.substring(pos1 + 1, pos2));
		}
		return reverse;
	}

	public boolean parseLow(String trimmed) throws IOException {
		low = false;
		if (trimmed.toUpperCase().startsWith(lowCoeffsS)) {
			low = true;
			int pos1 = trimmed.indexOf("/");
			int pos2 = trimmed.indexOf("/", pos1 + 1);
			parseCoeffs(trimmed.substring(pos1 + 1, pos2));
		}
		return low;
	}

	public boolean parseHigh(String trimmed) throws IOException {
		high = false;
		if (trimmed.toUpperCase().startsWith(highCoeffsS)) {
			high = true;
			int pos1 = trimmed.indexOf("/");
			int pos2 = trimmed.indexOf("/", pos1 + 1);
			parseCoeffs(trimmed.substring(pos1 + 1, pos2));
		}
		return high;
	}

	public boolean parsePlog(String trimmed) throws IOException {
		plog = false;
		if (trimmed.toUpperCase().startsWith(plogCoeffsS)) {
			plog = true;
			coeffs = parseConstants(trimmed, plogCoeffsS);
		}
		return plog;
	}

	public boolean parseSRI(String trimmed) throws IOException {
		sri = false;
		if (trimmed.toUpperCase().startsWith(sriCoeffsS)) {
			sri = true;
			coeffs = parseConstants(trimmed, sriCoeffsS);
		}
		return sri;
	}

	public boolean parseTroe(String trimmed) throws IOException {
		troe = false;
		if (trimmed.toUpperCase().startsWith(troeCoeffsS)) {
			troe = true;
			coeffs = parseConstants(trimmed, troeCoeffsS);
		}
		return troe;
	}

	public String[] parseConstants(String trimmed, String type) throws IOException {

		int pos1 = trimmed.indexOf("/");
		int pos2 = trimmed.indexOf("/", pos1 + 1);
		StringTokenizer tok = new StringTokenizer(trimmed.substring(pos1 + 1, pos2), " ");
		String[] constants = new String[tok.countTokens()];
		if (tok.countTokens() > 0) {
			constants = new String[tok.countTokens()];
			int i = 0;
			while (tok.hasMoreTokens()) {
				constants[i++] = tok.nextToken();
			}
		} else {
			throw new IOException("Illegal " + type + ": " + trimmed);
		}
		return constants;
	}

	public void parseCoeffs(String line) throws IOException {
		StringTokenizer tok = new StringTokenizer(line, " ");
		parseCoeffs(tok);
	}

	public void parseCoeffs(StringTokenizer tok) throws IOException {
		if (tok.countTokens() == 3) {
			A = tok.nextToken();
			n = tok.nextToken();
			Ea = tok.nextToken();
		} else {
			throw new IOException("Coefficient Parse Error: " + tok.countTokens() + " fields");
		}
	}

	public void addCommentLine(String line) {
		comments += line + "\n";
	}
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("Forward: " + forward + ", Reverse: " + reverse + ", Low: " + low + ", High: " + high + ", Troe: "
				+ troe + ", SRI: " + sri + ", PLOG: " + plog + "\n");
		if (troe) {
			build.append(troeCoeffsS + ": ");
			build.append(constantsToString());
		} else if (low) {
			build.append(lowCoeffsS + ": ");
			build.append(coeffsToString());
		} else if (high) {
			build.append(highCoeffsS + ": ");
			build.append(coeffsToString());
		} else if (sri) {
			build.append(sriCoeffsS + ": ");
			build.append(coeffsToString());
		} else if (forward) {
			build.append(forwardS + ": ");
			build.append(coeffsToString());
		} else if(reverse) {
			build.append(reverseS + ": ");
			build.append(coeffsToString());
		} else if(plog) {
			build.append(plogCoeffsS + ": ");
			build.append(constantsToString());
		}
		return build.toString();
	}

	private String coeffsToString() {
		StringBuilder build = new StringBuilder();
		build.append("\tA=");
		build.append(A);
		build.append(",\tn=");
		build.append(n);
		build.append(",\tEa=");
		build.append(Ea);
		build.append("\n");
		return build.toString();
	}

	private String constantsToString() {
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < coeffs.length; i++) {
			build.append("\t");
			build.append(coeffs[i]);
			build.append(", ");
		}
		return build.toString();
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

	public boolean isForward() {
		return forward;
	}

	public boolean isReverse() {
		return reverse;
	}

	public boolean isLow() {
		return low;
	}

	public boolean isTroe() {
		return troe;
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

	public String[] getCoeffs() {
		return coeffs;
	}
	public String getComments() {
		return comments;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public void setLow(boolean low) {
		this.low = low;
	}

	public void setTroe(boolean troe) {
		this.troe = troe;
	}

	public void setHigh(boolean high) {
		this.high = high;
	}

	public void setSri(boolean sri) {
		this.sri = sri;
	}
	public void setPlog(boolean plog) {
		this.plog = plog;
	}
}
