package info.esblurock.reaction.server.upload;

import info.esblurock.reaction.server.datastore.PMF;
import info.esblurock.reaction.server.utilities.ManageDataSourceIdentification;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import info.esblurock.reaction.data.upload.DeleteTextSetUploadData;
import info.esblurock.reaction.data.upload.FileUploadLines;
import info.esblurock.reaction.data.upload.UploadFileTransaction;


public class InputStreamToLineDatabase {
	private static final Logger log = Logger.getLogger(InputStreamToLineDatabase.class.getName());

	private int counter;
	private int totalcount;

	private String transactionKey;

	static public int maxLines = 2000;

	

	public UploadFileTransaction uploadFile(String user, String filename, String sourceType, BufferedReader buf)
			throws IOException {
		UploadFileTransaction transaction = null;
		try {
		String line;
		boolean notdone = true;
		int partcount = 0;
		counter = 0;
		totalcount = 0;
		String fileCode = ManageDataSourceIdentification.getDataSourceIdentification(user);
		transaction = new UploadFileTransaction(user, filename, fileCode, sourceType,totalcount);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(transaction);
		pm.flush();
		pm.close();
		String transactionkey = transaction.getKey();
		
		ArrayList<FileUploadLines> set = new ArrayList<FileUploadLines>();
		while (notdone) {
			line = buf.readLine();
			if (line != null) {
				counter++;
				totalcount++;
				FileUploadLines uploadlines = new FileUploadLines(totalcount, line, fileCode);
				set.add(uploadlines);
				if (counter >= maxLines) {
					uploadSet(set, fileCode, partcount);
					partcount++;
					counter = 0;
					set = new ArrayList<FileUploadLines>();
				}
			} else {
				notdone = false;
				uploadSet(set, fileCode, partcount);
			}
		}
		transaction = new UploadFileTransaction(user, filename, fileCode, sourceType,totalcount);
		pm = PMF.get().getPersistenceManager();
		transaction = pm.getObjectById(UploadFileTransaction.class,transactionkey);
		transaction.setLineCount(totalcount);
		pm.makePersistent(transaction);
		pm.flush();
		pm.close();
		log.info("Done Persist: UploadFilePartTransaction with " + totalcount + " lines");
		} catch (Exception ex) {
			if(transaction != null) {
				PersistenceManager pm = PMF.get().getPersistenceManager();
				sourceType = "ERROR";
				transaction.setLineCount(totalcount);
				pm.makePersistent(transaction);
				pm.flush();
				pm.close();
				log.log(Level.SEVERE,"ERROR write incomplete transaction: ");
			}
			log.log(Level.SEVERE,"ERROR in upload file: " + ex.toString());
			throw new IOException("ERROR in upload file: " + ex.toString());
		}
		
		
		return transaction;
	}

	private void uploadSet(ArrayList<FileUploadLines> set, String filename, int partcount) {
		log.info("Persist: UploadFilePartTransaction");
		log.info("uploadSet: " + partcount + "(" + set.size() + ")");
		System.out.println("uploadSet: " + partcount + "(" + set.size() + ")");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistentAll(set);
		pm.flush();
		pm.close();
		log.info("Done Persist Part: UploadFilePartTransaction with " + totalcount 
				+ " lines: part="  + partcount 
				+ "(" + set.size() + ")");
	}


	public Set<UploadFileTransaction> getUploadedFiles() {
		HashSet<UploadFileTransaction> transset = new HashSet<UploadFileTransaction>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
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
			pm.close();
		}
		return transset;
	}

	
	public String removeUpload(String key) {
		String ans = "SUCCESS";
		try {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			UploadFileTransaction uploadinfo = pm.getObjectById(UploadFileTransaction.class, key);
			DeleteTextSetUploadData delete = new DeleteTextSetUploadData();
			ans = delete.removeUpload(key,uploadinfo);
		} catch (IOException ex) {
			log.log(Level.SEVERE,ex.toString());
		} catch (Exception ex) {
			log.log(Level.SEVERE,ex.toString());
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
