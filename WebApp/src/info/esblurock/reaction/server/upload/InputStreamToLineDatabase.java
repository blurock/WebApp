package info.esblurock.reaction.server.upload;

import info.esblurock.reaction.client.panel.transaction.SourceTransaction;
import info.esblurock.reaction.server.datastore.PMF;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.FileUploadLines;
import info.esblurock.reaction.data.upload.UploadFileCount;
import info.esblurock.reaction.data.upload.UploadFilePartTransaction;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.user.UserAccount;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class InputStreamToLineDatabase {	
	private int counter;
	private int totalcount;
	
	private ArrayList<String> partkeys;
	private String transactionKey;
	
	static public int maxLines = 500;

	PersistenceManager pm = PMF.get().getPersistenceManager();

	public UploadFileTransaction uploadFile(
			String user, String filename, String sourceType, BufferedReader buf)
			throws IOException {
		String line;
		boolean notdone = true;
		int partcount = 0;
		counter = 0;
		totalcount = 0;
		String fileCode = getFileCode(user);
		System.out.println("UploadFileTransaction: User: " + user + "  FileCode: " + fileCode + "  File: " + filename);
		partkeys = new ArrayList<String>();
		ArrayList<FileUploadLines> set = new ArrayList<FileUploadLines>();
		while (notdone) {
			line = buf.readLine();
			if (line != null) {
				counter++;
				totalcount++;
				FileUploadLines uploadlines = new FileUploadLines(totalcount, line,fileCode);
				set.add(uploadlines);
				if(counter >= maxLines) {
					uploadSet(set, fileCode, partcount);
					partcount++;
					counter = 0;
				}
			} else {
				notdone = false;
				uploadSet(set,fileCode,partcount);
			}
		}
		System.out.println("Persist: UploadFilePartTransaction");
		UploadFileTransaction transaction = new UploadFileTransaction(user, filename, fileCode, sourceType, partkeys);
		pm.makePersistent(transaction);
		UploadFileTransaction t = pm.detachCopy(transaction);
		System.out.println("UploadFileTransaction: " +  transaction);
		System.out.println("UploadFileTransaction: " +  t);
		System.out.println("UploadFileTransaction: " +  transaction.getKey());

		return t;
	}

	private void uploadSet(ArrayList<FileUploadLines> set, String filename, int partcount) {
		System.out.println("uploadSet: " + partcount + "(" + set.size() + ")");
		//FileUploadLines[] arr = new FileUploadLines[set.size()];
		//set.toArray(arr);
		pm.makePersistentAll(set);
		pm.flush();
		ArrayList<String> keys = new ArrayList<String>();
		for(FileUploadLines line : set) {
			String key = line.getKey();
			keys.add(key);			
		}
		Integer partI = new Integer(partcount);
		UploadFilePartTransaction part = new UploadFilePartTransaction(filename, partI, keys);
		pm.makePersistent(part);
		pm.flush();
		System.out.println("Persist: UploadFilePartTransaction Key= " + part.getKey());
		partkeys.add(part.getKey());
		System.out.println("uploadSet: Done");
	}

	String getFileCode(String username) {
		System.out.println("getFileCode: " + username);
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			UploadFileCount count = null;
			try {
			count = pm.getObjectById(UploadFileCount.class, username);
			} catch(JDOObjectNotFoundException ex) {
				System.out.println("new UploadFileCount");
				count = new UploadFileCount(username);
				pm.makePersistent(count);
			}
			
			count.increment();
			System.out.println("Final UploadFileCount  count=" + count.getCountAsString());
			pm.makePersistent(count);
			return count.getCountAsString();

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

	public String getTransactionKey() {
		return transactionKey;
	}

}
