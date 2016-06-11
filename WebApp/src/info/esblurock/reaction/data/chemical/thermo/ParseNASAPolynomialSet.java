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
        SetOfThermodynamicInformation set = new SetOfThermodynamicInformation(name);
        findBeginning(tok);
        boolean notdone = true;
        while (notdone) {
            String line1 = tok.nextToken();
            tok.skipOverComments();
            line1 = tok.getCurrent();
            if(line1 == null) {
            	notdone = false;
            } else if (!line1.toLowerCase().startsWith(endS)) {
                String line2 = tok.nextToken();
                String line3 = tok.nextToken();
                String line4 = tok.nextToken();
                NASAPolynomial nasa = new NASAPolynomial();
                nasa.parse(line1, line2, line3, line4);
                set.add(nasa);
            } else {
                notdone = false;
            }
        }
		return set;

	}

	private void findBeginning(ChemkinStringFromStoredFile tok) {
		boolean notdone = true;
		String temp = tok.getCurrent();
		while(notdone) {
			if(temp == null) {
				notdone = false;
			} else if(temp.trim().toLowerCase().startsWith(thermoS)) {
				notdone = false;
				temperaturerange = tok.nextToken();
			} else {
				comments += temp + "\n";
				temp = tok.nextToken();
			}
		}
	}

}
