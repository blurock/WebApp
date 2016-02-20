package info.esblurock.reaction.server.chemkin;

import java.io.IOException;
import java.util.ArrayList;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import info.esblurock.react.mechanisms.chemkin.ChemkinString;
import info.esblurock.reaction.data.upload.FileUploadLines;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.server.datastore.PMF;

public class ChemkinStringFromStoredFile extends ChemkinString {
	private static final long serialVersionUID = 1L;
	PersistenceManager pm = PMF.get().getPersistenceManager();
	DatastoreService datastore;
	ArrayList<String> lines;
	private int count = 0;

	public ChemkinStringFromStoredFile(String key, String user, String commentString) {
		super(key, commentString);
		count = 0;
		datastore = DatastoreServiceFactory.getDatastoreService();
		System.out.println("Key: " + key);

		UploadFileTransaction transaction = pm.getObjectById(UploadFileTransaction.class, key);
		if (transaction != null) {
			lines = transaction.getSetOfLinesKeys();
		} else {
			System.out.println("UploadFileTransaction not found with key: " + key);
		}
	}
	public String getCurrent() {
		String key = lines.get(count);
		System.out.println("getCurrent(" + count + "): " + key);
		FileUploadLines lineE = pm.getObjectById(FileUploadLines.class, key);
		String line = lineE.getLine();
		System.out.println("getCurrent(): " + line);
		return line;		
	}
	public String nextToken() {
		String line;
		if(count < lines.size()) {
		String key = lines.get(++count);
		FileUploadLines lineE = pm.getObjectById(FileUploadLines.class, key);
		line = lineE.getLine();
		System.out.println("nextToken(" + count + "): " + line);
		} else {
			line = null;
		}
		return line;
	}

	public String nextNLines(int n) throws IOException {
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < n; i++) {
			String line = nextToken();
			build.append(line);
			build.append("\n");
		}
		return build.toString();
	}

}
