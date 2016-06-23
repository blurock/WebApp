package info.esblurock.reaction.data.chemical.thermo;

import java.io.IOException;

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
	
	TransactionInfo transaction = null;
	
	public ProcessNASAPolynomialUpload() {
	}
/*
	public String processUploadedNASAPolynomials(DescriptionDataData description, 
			String key, String filename, boolean process) throws IOException {
		ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(key,filename,commentString);
		String nasaname = GenerateKeywordFromDescription.createKeyword(description);
		SetOfThermodynamicInformation set = read(nasaname, chemkinstring);
		String ans = set.toString();
		if(process) {
			String keyword = GenerateKeywordFromDescription.createKeyword(description);
			String user = description.getInputkey();
			String idCode = ManageDataSourceIdentification.getDataSourceIdentification(user);

			
			CreateSetOfNASAPolynomialData create = new CreateSetOfNASAPolynomialData(keyword);
			
			System.out.println("processUploadedNASAPolynomials: SetOfNASAPolynomialData nasaset");
			SetOfNASAPolynomialData nasaset = create.create(set);

			transaction = new TransactionInfo(user,keyword,SetOfNASAPolynomialData.class.getName(),idCode);
			create.create(keyword,nasaset,transaction);			
		}
		return ans;

	}
*/
	private SetOfThermodynamicInformation read(String name, ChemkinStringFromStoredFile tok) throws IOException {
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
	public TransactionInfo getTransactionInfo() {
		return transaction;
	}
}
