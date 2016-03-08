package info.esblurock.reaction.data.chemical.thermo;

import java.io.IOException;

import info.esblurock.reaction.data.chemical.mechanism.CreateChemicalMechanismData;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import thermo.data.benson.NASAPolynomial;
import thermo.data.benson.SetOfThermodynamicInformation;

public class ProcessNASAPolynomialUpload {
	
	String endS = "end";
	String commentString = "!";
	String thermoS = "thermo";
	

	NASAPolynomial nasa;
	String temperaturerange = null;
	String comments = "";
	
	public ProcessNASAPolynomialUpload() {
	}
	
	public String processUploadedNASAPolynomials(DescriptionDataData description, 
			String key, String filename, boolean process) throws IOException {
		ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(key,filename,commentString);
		String nasaname = description.getSourcekey() + "." + description.getKeyword();
		SetOfThermodynamicInformation set = read(nasaname, chemkinstring);
		String ans = set.toString();
		if(process) {
			String keyword = CreateChemicalMechanismData.createMechanismName(description.getSourcekey(),description.getKeyword());
			
			TransactionInfo transaction = new TransactionInfo(description.getInputkey(),keyword,SetOfNASAPolynomialData.class.getName());
			CreateSetOfNASAPolynomialData create = new CreateSetOfNASAPolynomialData();
			System.out.println("processUploadedNASAPolynomials: SetOfNASAPolynomialData nasaset");
			SetOfNASAPolynomialData nasaset = create.create(keyword, set, transaction);
			System.out.println("processUploadedNASAPolynomials: StoreSetOfNASAPolynomialData store");
			StoreSetOfNASAPolynomialData store = new StoreSetOfNASAPolynomialData(keyword, nasaset, transaction, true);
			System.out.println("processUploadedNASAPolynomials: finish");
			store.finish();
			System.out.println("processUploadedNASAPolynomials: done");
		}
		return ans;

	}
	
	private SetOfThermodynamicInformation read(String name, ChemkinStringFromStoredFile tok) throws IOException {
        SetOfThermodynamicInformation set = new SetOfThermodynamicInformation(name);
        findBeginning(tok);
        boolean notdone = true;
        while (notdone) {
            String line1 = tok.nextToken();
            
            if (!line1.toLowerCase().startsWith(endS)) {
                String line2 = tok.nextToken();
                String line3 = tok.nextToken();
                String line4 = tok.nextToken();
                NASAPolynomial nasa = new NASAPolynomial();
                nasa.parse(line1, line2, line3, line4);
                System.out.println(nasa.toString());
                System.out.println("Entalpy: " + nasa.getStandardEnthalpy298());
                set.add(nasa);
            } else {
                notdone = false;
            }
        }
		return set;

	}

	private void findBeginning(ChemkinStringFromStoredFile tok) {
		boolean notdone = true;
		while(notdone) {
			String temp = tok.nextToken();
			if(temp == null) {
				notdone = false;
			} else if(temp.trim().toLowerCase().startsWith(thermoS)) {
				notdone = false;
				temperaturerange = tok.nextToken();
			} else {
				comments += temp + "\n";
			}
		}
		
		
	}
}
