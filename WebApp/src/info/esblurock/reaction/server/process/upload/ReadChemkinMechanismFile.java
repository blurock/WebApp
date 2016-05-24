package info.esblurock.reaction.server.process.upload;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.reaction.client.panel.inputs.DataInput;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload;
import info.esblurock.reaction.data.upload.types.CreateBufferedReaderForSourceFile;
import info.esblurock.reaction.server.StringToKeyConversion;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificaitonsBase;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;

public class ReadChemkinMechanismFile extends ProcessBase {
	static String deleteUploadedFile = "RemoveUploadedFile";
	static String keywordDelimitor = "#";
	InputStreamToLineDatabase input;
	StringToKeyConversion conversion;

	UploadFileTransaction upload;
	protected String textBody;
	protected String textName;
	protected String source = "";
	protected String sourceType;


	public ReadChemkinMechanismFile(ProcessInputSpecificaitonsBase specs) {
		super(specs);
		SourcefFileUploadInput specifications = (SourcefFileUploadInput) specs;
		input = new InputStreamToLineDatabase();
		conversion = new StringToKeyConversion();
		textBody = specifications.getTextBody();
		textName = specifications.getTextName();
		sourceType = specifications.getSourceType();
	}

	@Override
	protected String getProcessName() {
		return "ReadChemkinMechanismFile";
	}

	@Override
	protected String getProcessDescription() {
		return "Read in a CHEMKIN mechanism from client box and see if correct";
	}
	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		return new ArrayList<String>();
	}

	@Override
	protected ArrayList<String> getOutputTransactionObjectNames() {
		String o1 = "info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload";
		ArrayList<String> output = new ArrayList<String>();
		output.add(o1);
		return output;
	}
	@Override
	protected void initializeOutputObjects() {
		super.initializeOutputObjects();
		upload = new ChemkinMechanismFileUpload(user, textName, outputSourceCode, source, 0);
		objectOutputs.add(upload);
	}

	protected void createObjects() throws IOException {
		BufferedReader br = CreateBufferedReaderForSourceFile.getBufferedReader(sourceType, textName, textBody);
		String commentString = "!";
		log.info("User verified: to read text: " + textName);
		upload = input.uploadFile(upload, br);
		try {
			ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(upload, commentString);
			ChemkinMechanism mechanism = new ChemkinMechanism();
			mechanism.parse(chemkinstring, commentString);
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
}
