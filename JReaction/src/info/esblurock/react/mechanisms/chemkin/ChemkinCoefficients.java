package info.esblurock.react.mechanisms.chemkin;

import java.io.IOException;
import java.util.StringTokenizer;

public class ChemkinCoefficients {
	private boolean forward;
	private boolean low;
	private boolean troe;
	
	private String A,n,Ea;
	private String[] troeCoeffs;
	
	private String forwardS;
	private String reverseS;
	
	private String reverseCoeffsS;
	private String troeCoeffsS;
	private String lowCoeffsS;
	
	
	private void init() {
		forwardS = "Forward:";
		reverseS = "Reverse:";
		reverseCoeffsS = "REV";
		troeCoeffsS = "TROE";
		lowCoeffsS = "LOW";
	
	}
	public ChemkinCoefficients() {
		init();
		forward = true;
	}
	public ChemkinCoefficients(boolean forward) {
		init();
		this.forward = forward;
	}

	public boolean parseReverse(String trimmed) throws IOException {
		boolean reverse = false;
		if(trimmed.startsWith(reverseCoeffsS)) {
			forward = false;
			reverse = true;
			int pos1 = trimmed.indexOf("/");
			int pos2 = trimmed.indexOf("/",pos1+1);
			System.out.println(trimmed.substring(pos1+1,pos2));
			parseCoeffs(trimmed.substring(pos1+1,pos2));
		}
		return reverse;
	}

	public boolean parseLow(String trimmed) throws IOException {
		low = false;
		if(trimmed.startsWith(lowCoeffsS)) {
			low = true;
			int pos1 = trimmed.indexOf("/");
			int pos2 = trimmed.indexOf("/",pos1+1);
			System.out.println(trimmed.substring(pos1+1,pos2));
			parseCoeffs(trimmed.substring(pos1+1,pos2));
		}
		return low;
	}

	public boolean parseTroe(String trimmed)  throws IOException {
		troe = false;
		if(trimmed.startsWith(troeCoeffsS)) {
			troe = true;
			int pos1 = trimmed.indexOf("/");
			int pos2 = trimmed.indexOf("/",pos1+1);
			StringTokenizer tok = new StringTokenizer(trimmed.substring(pos1+1,pos2)," ");
			if(tok.countTokens() > 0) {
				troeCoeffs = new String[tok.countTokens()];
				int i = 0;
				while(tok.hasMoreTokens()) {
					troeCoeffs[i++] = tok.nextToken();
				}
			} else {
				throw new IOException("Illegal TROE: " + trimmed);
			}
		}
		return troe;
	}
    public void parseCoeffs(String line) throws IOException {
    	StringTokenizer tok = new StringTokenizer(line," ");
    	parseCoeffs(tok);
    }
	
    public void parseCoeffs(StringTokenizer tok) throws IOException {
        if(tok.countTokens() == 3) {
            A = tok.nextToken();
            n = tok.nextToken();
            Ea = tok.nextToken();
        } else {
            throw new IOException("Coefficient Parse Error:" + tok.countTokens() + "fields");
        }
    }
    
    public String toString() {
    	StringBuilder build = new StringBuilder();
    	if(troe) {
    		build.append("TROE: ");
    		for(int i=0;i<troeCoeffs.length;i++) {
    			build.append("\t");
    			build.append(troeCoeffs[i]);
    			build.append("\n");
    		}
    	} else {
    		if(low) {
    			build.append(lowCoeffsS);
    			build.append("\n");
    		} else if(forward) {
    			build.append(forwardS);
    			build.append("\n");
    		} else {
    			build.append(reverseS);
    			build.append("\n");
    		}
    	build.append("\tA=");
    	build.append(A);
    	build.append(",\tn=");
    	build.append(n);
    	build.append(",\tEa=");
    	build.append(Ea);
    	build.append("\n");
    	}
   	return build.toString();
    }
    
	public String getA() {
		return A;
	}
	public void setA(String a) {
		A = a;
	}
	public String getN() {
		return n;
	}
	public void setN(String n) {
		this.n = n;
	}
	public String getEa() {
		return Ea;
	}
	public void setEa(String ea) {
		Ea = ea;
	}
	public boolean isForward() {
		return forward;
	}
	public void setForward() {
		this.forward = true;
	}
	public void setReverse() {
		this.forward = false;
	}

}
