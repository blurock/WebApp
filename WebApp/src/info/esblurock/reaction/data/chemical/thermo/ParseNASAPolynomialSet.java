package info.esblurock.reaction.data.chemical.thermo;

import java.io.IOException;

import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import thermo.data.benson.NASAPolynomial;
import thermo.data.benson.SetOfThermodynamicInformation;

public class ParseNASAPolynomialSet {
	String thermoS = "thermo";
	String endS = "end";

	String temperaturerange = null;
	String comments = "";

	
	public  SetOfThermodynamicInformation parse(String name, ChemkinStringFromStoredFile tok) throws IOException {
       	StringBuilder errbuild = new StringBuilder();
        SetOfThermodynamicInformation set = new SetOfThermodynamicInformation(name);
        findBeginning(tok);
        boolean notdone = true;
        while (notdone) {
            String line1 = nextNonblankToken(tok);
            tok.skipOverComments();
            line1 = tok.getCurrent();
            if(line1 == null) {
            	notdone = false;
            } else if (!line1.trim().toLowerCase().startsWith(endS)) {
                String line2 = nextNonblankToken(tok);
                String line3 = nextNonblankToken(tok);
                String line4 = nextNonblankToken(tok);
                /*
                System.out.println("---------------------------------------------------------------------------------");
                System.out.println("1 :" + line1);
                System.out.println("2 :" + line2);
                System.out.println("3 :" + line3);
                System.out.println("4 :" + line4);
                System.out.println("---------------------------------------------------------------------------------");
                */
                try {
                	NASAPolynomial nasa = new NASAPolynomial();
                	nasa.parse(line1, line2, line3, line4);
                	set.add(nasa);
                } catch(IOException ex) {
                	errbuild.append("Error in Thermodynamics\n");
                	errbuild.append("(1): " + line1 + "\n");
                	errbuild.append(ex.toString() + "\n");
                }
            } else {
                notdone = false;
            }
        }
        System.err.println(errbuild.toString());
        return set;

	}

	private String nextNonblankToken(ChemkinStringFromStoredFile tok) {
		String line = tok.nextToken();
		while(line != null && line.trim().length() == 0) {
			line = tok.nextToken();
		}
		return line;
	}
	private void findBeginning(ChemkinStringFromStoredFile tok) {
		boolean notdone = true;
		String temp = tok.getCurrent();
		while(notdone) {
			if(temp == null) {
				notdone = false;
			} else if(temp.trim().toLowerCase().startsWith(thermoS)) {
				notdone = false;
				temperaturerange = nextNonblankToken(tok);
			} else {
				comments += temp + "\n";
				temp = tok.nextToken();
			}
		}
	}

}
