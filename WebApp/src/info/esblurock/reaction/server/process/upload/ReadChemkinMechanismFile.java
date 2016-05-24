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
import info.esblurock.reaction.server.StringToKeyConversion;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;

public class ReadChemkinMechanismFile extends ProcessBase {
	public static String textAsSource = "Text";
	public static String httpAsSource = "Http";
	static String deleteUploadedFile = "RemoveUploadedFile";
	static String keywordDelimitor = "#";
	InputStreamToLineDatabase input;
	StringToKeyConversion conversion;

	UploadFileTransaction upload;
	protected String textBody;
	protected String textName;
	protected String source = "";
	protected String sourceType;


	public ReadChemkinMechanismFile(String user, String keyword, String sourceCode) {
		super(user, keyword, sourceCode);
		input = new InputStreamToLineDatabase();
		conversion = new StringToKeyConversion();
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
		BufferedReader br = getBufferedReader();
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
	//abstract protected BufferedReader getBufferedReader() throws IOException ;
	protected BufferedReader getBufferedReader() throws IOException {
		InputStream stream = null;
		if(sourceType.equals(textAsSource)) {
			stream = new ByteArrayInputStream(textBody.getBytes(StandardCharsets.UTF_8));
		} else if(sourceType.equals(httpAsSource)) {
			URL url = new URL(textName);
			stream = url.openStream();
		} else {
			throw new IOException("Source Type not found: " + sourceType);
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IOException(e.toString());
		}
		return br;
	}

	


	
	public String getTextBody() {
		return textBody;
	}

	public void setTextBody(String textBody) {
		this.textBody = textBody;
	}

	public String getTextName() {
		return textName;
	}

	public void setTextName(String textName) {
		this.textName = textName;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

}
