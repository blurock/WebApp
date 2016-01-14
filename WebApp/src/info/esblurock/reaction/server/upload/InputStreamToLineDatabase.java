package info.esblurock.reaction.server.upload;

import info.esblurock.reaction.client.panel.transaction.SourceTransaction;
import info.esblurock.reaction.server.datastore.PMF;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;

import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.FileUploadLines;
import info.esblurock.reaction.data.upload.UploadFileTransaction;

import com.google.appengine.api.datastore.Key;

public class InputStreamToLineDatabase {	
	private int counter;
	private ArrayList<String> keys;
	private String transactionKey;

	PersistenceManager pm = PMF.get().getPersistenceManager();

	public UploadFileTransaction uploadFile(
			String user, String filename, String sourceType, BufferedReader buf)
			throws IOException {
		keys = new ArrayList<String>();
		String line;
		boolean notdone = true;
		counter = 0;
		while (notdone) {
			line = buf.readLine();
			if (line != null) {
				counter++;
				FileUploadLines uploadlines = new FileUploadLines(counter, line);
				pm.makePersistent(uploadlines);
				String key = uploadlines.getKey();
				keys.add(key);
			} else {
				notdone = false;
			}
		}
		UploadFileTransaction transaction = new UploadFileTransaction(user, filename, sourceType, keys);
		pm.makePersistent(transaction);
		UploadFileTransaction t = pm.detachCopy(transaction);
		return t;
	}

	public Set<UploadFileTransaction> getUploadedFiles() {
		HashSet<UploadFileTransaction> transset = new HashSet<UploadFileTransaction>();
		// Set set = pm.getManagedObjects();
		javax.jdo.Query q = pm.newQuery(UploadFileTransaction.class);
		try {
			List<UploadFileTransaction> results = (List<UploadFileTransaction>) q.execute();
			if (!results.isEmpty()) {
				for (UploadFileTransaction transaction : results) {
					UploadFileTransaction trans = pm.detachCopy(transaction);
					transset.add(trans);
				}
			} else {
				// Handle "no results" case
			}
		} finally {
			q.closeAll();
		}
		return transset;
	}

	public String removeUpload(String key) {
		String ans = "SUCCESS";
		try {
			UploadFileTransaction uploadinfo = pm.getObjectById(
					UploadFileTransaction.class, key);
			List<String> linekeys = uploadinfo.getSetOfLinesKeys();
			for (String linekey : linekeys) {
				FileUploadLines line = pm.getObjectById(FileUploadLines.class,
						linekey);
				pm.deletePersistent(line);
			}
			pm.deletePersistent(uploadinfo);
		} catch (Exception ex) {
			ans = ex.toString();
		}
		return ans;
	}

	public int getCounter() {
		return counter;
	}

	public ArrayList<String> getKeys() {
		return keys;
	}

	public String getTransactionKey() {
		return transactionKey;
	}

}
