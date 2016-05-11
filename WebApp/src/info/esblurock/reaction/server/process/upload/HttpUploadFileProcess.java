package info.esblurock.reaction.server.process.upload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.reaction.client.data.DatabaseObject;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.server.StringToKeyConversion;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;

public class HttpUploadFileProcess extends ProcessBase {

	static String uploadText = "UploadText";
	static String uploadHTTP = "UploadHTTP";
	static String deleteUploadedFile = "RemoveUploadedFile";
	static String keywordDelimitor = "#";
	static String source = "HTTP";
	InputStreamToLineDatabase input = new InputStreamToLineDatabase();
	StringToKeyConversion conversion = new StringToKeyConversion();

	UploadFileTransaction upload;
	private String http;

	public HttpUploadFileProcess(String processName, String user, String keyword, String sourceCode) {
		super(processName, user, keyword, sourceCode);
	}

	protected void createObjects() throws IOException {

		String commentString = "!";
		log.info("User verified: to read http address: " + http);
		URL url = new URL(http);
		InputStream inputstream = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"));
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

	protected void initializeOutputObjects() {
		super.initializeOutputObjects();
		objectOutputs = new ArrayList<DatabaseObject>();
		upload = new UploadFileTransaction(user, http, outputSourceCode, source, 0);
		objectOutputs.add(upload);
		log.info("initializeOutputObjects()" + objectOutputs);
	}

	public String getHttp() {
		return http;
	}

	public void setHttp(String http) {
		this.http = http;
	}

}
