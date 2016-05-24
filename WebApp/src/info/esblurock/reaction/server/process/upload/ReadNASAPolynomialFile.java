package info.esblurock.reaction.server.process.upload;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.upload.types.CreateBufferedReaderForSourceFile;
import info.esblurock.reaction.data.upload.types.NASAPolynomialFileUpload;
import info.esblurock.reaction.server.StringToKeyConversion;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificaitonsBase;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;
import thermo.data.benson.NASAPolynomial;
import thermo.data.benson.SetOfThermodynamicInformation;

public class ReadNASAPolynomialFile  extends ProcessBase  {

	UploadFileTransaction upload;
	InputStreamToLineDatabase input;
	
	protected String textBody;
	protected String textName;
	protected String source = "";
	protected String sourceType;

	String temperaturerange = null;
	String comments = "";
	String thermoS = "thermo";
	String endS = "end";

	
	public ReadNASAPolynomialFile() {
		super();
	}

	public ReadNASAPolynomialFile(ProcessInputSpecificaitonsBase specs) {
		super(specs);
		SourcefFileUploadInput specifications = (SourcefFileUploadInput) specs;
		input = new InputStreamToLineDatabase();
		textBody = specifications.getTextBody();
		textName = specifications.getTextName();
		sourceType = specifications.getSourceType();
	}

	@Override
	protected String getProcessName() {
		return "ReadNASAPolynomialFile";
	}

	@Override
	protected String getProcessDescription() {
		return "Read in a NASA polynomial from client and see if correct";
	}

	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		return new ArrayList<String>();
	}

	@Override
	protected ArrayList<String> getOutputTransactionObjectNames() {
		String o1 = "info.esblurock.reaction.data.upload.types.NASAPolynomialFileUpload";
		ArrayList<String> output = new ArrayList<String>();
		output.add(o1);
		return output;
	}

	@Override
	protected void initializeOutputObjects() {
		super.initializeOutputObjects();
		upload = new NASAPolynomialFileUpload(user, textName, outputSourceCode, source, 0);
		objectOutputs.add(upload);
	}

	@Override
	protected void createObjects() throws IOException {
		BufferedReader br = CreateBufferedReaderForSourceFile.getBufferedReader(sourceType, textName, textBody);
		String commentString = "!";
		log.info("User verified: to read text: " + textName);
		upload = input.uploadFile(upload, br);
		try {
			ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(upload, commentString);
			SetOfThermodynamicInformation set = read(keyword, chemkinstring);
			String ans = set.toString();
		} catch (IOException ex) {
			storeNewTransactions();
			throw ex;
		}
	}
	public String getTextBody() {
		return textBody;
	}
	public String getTextName() {
		return textName;
	}
	public String getSourceType() {
		return sourceType;
	}
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


}
