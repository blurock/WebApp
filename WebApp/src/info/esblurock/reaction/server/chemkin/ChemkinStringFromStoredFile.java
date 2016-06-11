package info.esblurock.reaction.server.chemkin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import info.esblurock.react.mechanisms.chemkin.ChemkinString;
import info.esblurock.reaction.data.transaction.ActionsUsingIdentificationCode;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.server.datastore.PMF;

public class ChemkinStringFromStoredFile extends ChemkinString {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(ChemkinStringFromStoredFile.class.getName());

	DatastoreService datastore;
	ArrayList<String> lines;
	ArrayList<String> parts;
	Iterator<String> partiterator;
	private int count = 0;
	private int totalcount = 0;
	private int lineCount;
	private String fileCode;
	private int maxPart = 1000;
	boolean nextpart = true;

	public ChemkinStringFromStoredFile(UploadFileTransaction transaction, String commentString) {
		super(transaction.getKey(), commentString);
		count = 0;
		totalcount = 0;
		nextpart = true;
		lineCount = transaction.getLineCount();
		fileCode = transaction.getFileCode();
		setUpNextPart();

	}

	public ChemkinStringFromStoredFile(String key, String user, String commentString) {
		super(key, commentString);
		count = 0;
		totalcount = 0;
		nextpart = true;
		datastore = DatastoreServiceFactory.getDatastoreService();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		UploadFileTransaction transaction = pm.getObjectById(UploadFileTransaction.class, key);
		if (transaction != null) {
			lineCount = transaction.getLineCount();
			fileCode = transaction.getFileCode();
			setUpNextPart();
		} else {
			log.log(Level.SEVERE, "UploadFileTransaction not found with key: " + key);
		}
	}

	private boolean setUpNextPart() {
		count = 0;
		if (nextpart) {
			lines = ActionsUsingIdentificationCode.getNextLines(totalcount, maxPart, fileCode);
		}
		if (lines.size() == 0)
			nextpart = false;
		return nextpart;
	}

	public String getCurrent() {
		String line = null;
		if (count < lines.size()) {
			line = lines.get(count);
		} else {
			if (setUpNextPart()) {
				line = lines.get(count);
			}
		}
		return line;
	}

	public String nextToken() {
		String line = null;
		count++;
		totalcount++;
		if (count < lines.size()) {
			line = lines.get(count);
		} else {
			if (setUpNextPart()) {
				line = lines.get(count);
			}
		}
		return line;
	}

}
